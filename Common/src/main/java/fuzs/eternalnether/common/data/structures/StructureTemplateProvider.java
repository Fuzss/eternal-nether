package fuzs.eternalnether.common.data.structures;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.logging.LogUtils;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.*;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.Util;
import net.minecraft.util.datafix.DataFixTypes;
import net.minecraft.util.datafix.DataFixers;
import net.minecraft.world.RandomizableContainer;
import net.minecraft.world.level.BaseSpawner;
import net.minecraft.world.level.SpawnData;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.storage.loot.LootTable;
import org.slf4j.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Converts structure templates from the data generator input directories into upgraded {@code nbt} and {@code snbt}
 * files in the data generator output.
 * <p>
 * Input files may be {@code nbt} or {@code snbt} and can be mixed within the same directory, but only a single file per
 * structure is used to generate both outputs. When a structure is provided in both formats, the {@code snbt} file is
 * preferred and the other one is ignored.
 * <p>
 * Every structure is upgraded by {@link StructureUpdater}, which applies the vanilla data fixer and resolves the block
 * palette against the registered blocks (including all modded ones while data generation is running). The generated
 * {@code snbt} file is a human-readable representation of the upgraded {@code nbt} file and is intended for reviewing
 * changes in version control.
 * <p>
 * Structures are validated before they are upgraded, as {@link StructureUpdater} silently replaces unknown blocks with
 * air and drops unknown block state properties. Every structure that references an unknown block, an invalid block
 * state property, or an unknown block entity type, loot table, template pool, or spawner entity type is skipped and
 * reported once all structures have been processed.
 *
 * @see net.minecraft.data.structures.SnbtToNbt
 */
public class StructureTemplateProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();

    private final PackOutput output;
    private final Collection<Path> inputs;
    private final CompletableFuture<HolderLookup.Provider> lookupProvider;

    public StructureTemplateProvider(DataProviderContext context) {
        // TODO replace with the data generator input directories once they are provided by the data provider context
        this(context.getPackOutput(), List.of(Path.of("../Common/src/main/nbt")), context.getRegistries());
    }

    public StructureTemplateProvider(PackOutput output, Collection<Path> inputs, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        this.output = output;
        this.inputs = inputs;
        this.lookupProvider = lookupProvider;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return this.lookupProvider.thenCompose((HolderLookup.Provider lookupProvider) -> {
            Path outputDirectory = this.output.getOutputFolder();
            List<Throwable> failures = Collections.synchronizedList(new ArrayList<>());
            List<CompletableFuture<?>> tasks = new ArrayList<>();

            for (Path inputDirectory : this.inputs) {
                // Skip absent input directories, the data generator might not have been provided with any.
                if (!Files.isDirectory(inputDirectory)) {
                    continue;
                }

                // Flatten the nested future, so this task only completes once all structure files of the directory are converted.
                tasks.add(CompletableFuture.supplyAsync(() -> {
                    Map<String, Path> structures = new LinkedHashMap<>();
                    try (Stream<Path> files = Files.walk(inputDirectory)) {
                        files.filter(this::isStructureFile).forEach((Path path) -> {
                            String name = this.getName(inputDirectory, path);
                            Path previous = structures.putIfAbsent(name, path);
                            if (previous != null) {
                                // Structures provided as both NBT and SNBT use the SNBT file, the other one is ignored.
                                Path ignored;
                                if (this.isSnbtFile(path)) {
                                    ignored = previous;
                                    structures.put(name, path);
                                } else {
                                    ignored = path;
                                }

                                LOGGER.warn("Structure '{}' is provided as both NBT and SNBT, ignoring '{}'",
                                        name,
                                        ignored);
                            }
                        });
                    } catch (Exception exception) {
                        failures.add(new StructureConversionException(inputDirectory, exception));
                        return CompletableFuture.<Void>completedFuture(null);
                    }

                    return CompletableFuture.allOf(structures.entrySet()
                            .stream()
                            .map((Map.Entry<String, Path> entry) -> CompletableFuture.runAsync(() -> {
                                try {
                                    this.convertStructure(cache,
                                            outputDirectory,
                                            entry.getValue(),
                                            entry.getKey(),
                                            lookupProvider,
                                            failures);
                                } catch (Exception exception) {
                                    failures.add(new StructureConversionException(entry.getValue(), exception));
                                }
                            }, Util.backgroundExecutor().forName(this.getName())))
                            .toArray(CompletableFuture[]::new));
                }, Util.backgroundExecutor().forName(this.getName())).thenCompose(Function.identity()));
            }

            return Util.sequenceFailFast(tasks).thenRun(() -> {
                if (!failures.isEmpty()) {
                    RuntimeException exception = new StructureConversionException(
                            "Failed to process structure templates (" + failures.size() + " problem(s))");
                    failures.forEach(exception::addSuppressed);
                    throw exception;
                }
            });
        });
    }

    public boolean isStructureFile(Path path) {
        return this.isNbtFile(path) || this.isSnbtFile(path);
    }

    public final boolean isNbtFile(Path path) {
        return path.toString().endsWith(".nbt");
    }

    public final boolean isSnbtFile(Path path) {
        return path.toString().endsWith(".snbt");
    }

    private String getName(Path inputDirectory, Path structureFile) {
        String name = inputDirectory.relativize(structureFile).toString().replace('\\', '/');
        return name.substring(0, name.lastIndexOf('.'));
    }

    public void convertStructure(CachedOutput cache, Path outputDirectory, Path structureFile, String name, HolderLookup.Provider lookupProvider, List<Throwable> failures) throws IOException, CommandSyntaxException {
        CompoundTag tag = this.readStructure(structureFile);
        CompoundTag fixedTag = DataFixTypes.STRUCTURE.updateToCurrentVersion(DataFixers.getDataFixer(),
                tag,
                NbtUtils.getDataVersion(tag, 500));
        // Invalid structures are skipped entirely, mirroring vanilla data generators which fail instead of emitting
        // broken data.
        if (!this.validateStructure(name, fixedTag, lookupProvider, failures)) {
            return;
        }

        CompoundTag upgradedTag = StructureUpdater.update(name, tag);
        this.writeNbt(cache, outputDirectory, name, upgradedTag);
        this.writeSnbt(cache, outputDirectory, name, upgradedTag);
    }

    public CompoundTag readStructure(Path structureFile) throws IOException, CommandSyntaxException {
        // SNBT input uses the packed structure format produced by NbtUtils.structureToSnbt.
        if (this.isSnbtFile(structureFile)) {
            return NbtUtils.snbtToStructure(Files.readString(structureFile));
        }

        return NbtIo.readCompressed(structureFile, NbtAccounter.unlimitedHeap());
    }

    private boolean validateStructure(String name, CompoundTag tag, HolderLookup.Provider lookupProvider, List<Throwable> failures) {
        // Collect every issue independently, so all problems of a single structure are reported at once.
        List<Throwable> issues = new ArrayList<>();
        this.validatePalette(name, tag, issues::add);
        this.validateBlockEntities(name, tag, lookupProvider, issues::add);
        failures.addAll(issues);
        return issues.isEmpty();
    }

    public void validatePalette(String name, CompoundTag tag, Consumer<Throwable> issues) {
        // Structures either use a single palette for all blocks...
        tag.getList(StructureTemplate.PALETTE_TAG).ifPresent((ListTag palette) -> {
            this.validatePalette(name, palette, issues);
        });
        // ...or multiple palettes, one of which is picked per block.
        tag.getList(StructureTemplate.PALETTE_LIST_TAG).ifPresent((ListTag palettes) -> {
            for (int i = 0; i < palettes.size(); i++) {
                this.validatePalette(name, palettes.getListOrEmpty(i), issues);
            }
        });
    }

    public void validatePalette(String name, ListTag palette, Consumer<Throwable> issues) {
        for (int i = 0; i < palette.size(); i++) {
            // Each palette entry is a block state stored as an "id" with an optional "properties" compound.
            CompoundTag entry = palette.getCompoundOrEmpty(i);
            Optional<String> blockId = entry.getString("id");
            if (blockId.isEmpty()) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' has a palette entry without a block id"));
                continue;
            }

            Identifier identifier = Identifier.tryParse(blockId.get());
            if (identifier == null) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' has an invalid block id '" + blockId.get() + "'"));
                continue;
            }

            Optional<Block> block = BuiltInRegistries.BLOCK.getOptional(identifier);
            if (block.isEmpty()) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' references unknown block '" + blockId.get() + "'"));
                continue;
            }

            entry.getCompound("properties").ifPresent((CompoundTag properties) -> {
                StateDefinition<Block, BlockState> definition = block.get().getStateDefinition();
                for (String key : properties.keySet()) {
                    Property<?> property = definition.getProperty(key);
                    if (property == null) {
                        issues.accept(new StructureConversionException(
                                "Structure '" + name + "' has unknown property '" + key + "' for block '"
                                        + blockId.get() + "'"));
                    } else {
                        Optional<String> value = properties.getString(key);
                        if (value.isEmpty() || property.getValue(value.get()).isEmpty()) {
                            issues.accept(new StructureConversionException(
                                    "Structure '" + name + "' has invalid property value '" + value.orElse("")
                                            + "' for property '" + key + "' of block '" + blockId.get() + "'"));
                        }
                    }
                }
            });
        }
    }

    public void validateBlockEntities(String name, CompoundTag tag, HolderLookup.Provider lookupProvider, Consumer<Throwable> issues) {
        HolderLookup.RegistryLookup<StructureTemplatePool> templatePools = lookupProvider.lookupOrThrow(Registries.TEMPLATE_POOL);
        HolderLookup.RegistryLookup<LootTable> lootTables = lookupProvider.lookupOrThrow(Registries.LOOT_TABLE);
        ListTag blocks = tag.getListOrEmpty(StructureTemplate.BLOCKS_TAG);
        for (int i = 0; i < blocks.size(); i++) {
            // Block entities are stored as a raw "nbt" compound on their entry in the "blocks" list.
            blocks.getCompoundOrEmpty(i).getCompound("nbt").ifPresent((CompoundTag blockEntity) -> {
                this.validateBlockEntity(name, blockEntity, templatePools, lootTables, issues);
            });
        }
    }

    public void validateBlockEntity(String name, CompoundTag blockEntity, HolderLookup.RegistryLookup<StructureTemplatePool> templatePools, HolderLookup.RegistryLookup<LootTable> lootTables, Consumer<Throwable> issues) {
        // "id" is the block entity type.
        this.validateStaticReference(name,
                blockEntity,
                "id",
                "block entity type",
                BuiltInRegistries.BLOCK_ENTITY_TYPE,
                issues);
        // Chest-like block entities reference their loot table.
        blockEntity.getString(RandomizableContainer.LOOT_TABLE_TAG).ifPresent((String lootTable) -> {
            Identifier identifier = Identifier.tryParse(lootTable);
            if (identifier == null || lootTables.get(ResourceKey.create(Registries.LOOT_TABLE, identifier)).isEmpty()) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' references unknown loot table '" + lootTable + "'"));
            }
        });
        // Jigsaw blocks reference the template pool they connect to.
        blockEntity.getString("pool").ifPresent((String templatePool) -> {
            Identifier identifier = Identifier.tryParse(templatePool);
            if (identifier == null || templatePools.get(ResourceKey.create(Registries.TEMPLATE_POOL, identifier))
                    .isEmpty()) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' references unknown template pool '" + templatePool + "'"));
            }
        });
        // Spawners store their mobs in "SpawnData" and "SpawnPotentials".
        blockEntity.getCompound(BaseSpawner.SPAWN_DATA_TAG).ifPresent((CompoundTag spawnData) -> {
            this.validateSpawnData(name, spawnData, issues);
        });
        blockEntity.getList("SpawnPotentials").ifPresent((ListTag spawnPotentials) -> {
            for (int i = 0; i < spawnPotentials.size(); i++) {
                spawnPotentials.getCompoundOrEmpty(i).getCompound("data").ifPresent((CompoundTag spawnData) -> {
                    this.validateSpawnData(name, spawnData, issues);
                });
            }
        });
    }

    public void validateSpawnData(String name, CompoundTag spawnData, Consumer<Throwable> issues) {
        // "entity" holds the mob nbt, its "id" is the entity type.
        spawnData.getCompound(SpawnData.ENTITY_TAG).ifPresent((CompoundTag entity) -> {
            this.validateStaticReference(name, entity, "id", "entity type", BuiltInRegistries.ENTITY_TYPE, issues);
        });
    }

    private void validateStaticReference(String name, CompoundTag tag, String key, String description, Registry<?> registry, Consumer<Throwable> issues) {
        tag.getString(key).ifPresent((String id) -> {
            Identifier identifier = Identifier.tryParse(id);
            if (identifier == null || !registry.containsKey(identifier)) {
                issues.accept(new StructureConversionException(
                        "Structure '" + name + "' references unknown " + description + " '" + id + "'"));
            }
        });
    }

    @SuppressWarnings("UnstableApiUsage")
    public void writeNbt(CachedOutput cache, Path outputDirectory, String name, CompoundTag tag) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);
        NbtIo.writeCompressed(tag, hashingOutputStream);
        Path path = outputDirectory.resolve(name + ".nbt");
        cache.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }

    public void writeSnbt(CachedOutput cache, Path outputDirectory, String name, CompoundTag tag) throws IOException {
        Path path = outputDirectory.resolve(name + ".snbt");
        NbtToSnbt.writeSnbt(cache, path, NbtUtils.structureToSnbt(tag));
    }

    @Override
    public String getName() {
        return "Structure Templates";
    }

    private static class StructureConversionException extends RuntimeException {

        StructureConversionException(Path path, Throwable cause) {
            super(path.toAbsolutePath().toString(), cause);
        }

        StructureConversionException(String message) {
            super(message);
        }
    }
}
