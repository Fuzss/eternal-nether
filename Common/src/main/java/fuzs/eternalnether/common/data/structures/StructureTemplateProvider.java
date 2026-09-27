package fuzs.eternalnether.common.data.structures;

import com.google.common.hash.Hashing;
import com.google.common.hash.HashingOutputStream;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.structures.NbtToSnbt;
import net.minecraft.data.structures.StructureUpdater;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtAccounter;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.util.Util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Converts NBT structure templates from the data generator input directories into upgraded {@code nbt} and {@code snbt}
 * files in the data generator output.
 * <p>
 * Every structure is upgraded by {@link StructureUpdater}, which applies the vanilla data fixer and resolves the block
 * palette against the registered blocks (including all modded ones while data generation is running). The generated
 * {@code snbt} file is a human-readable representation of the upgraded {@code nbt} file and is intended for reviewing
 * changes in version control.
 *
 * @see net.minecraft.data.structures.SnbtToNbt
 */
public class StructureTemplateProvider implements DataProvider {
    private final PackOutput output;
    private final Collection<Path> inputs;

    public StructureTemplateProvider(DataProviderContext context) {
        // TODO replace with the data generator input directories once they are provided by the data provider context
        this(context.getPackOutput(), List.of(Path.of("../Common/src/main/nbt")));
    }

    public StructureTemplateProvider(PackOutput output, Collection<Path> inputs) {
        this.output = output;
        this.inputs = inputs;
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
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
                try (Stream<Path> files = Files.walk(inputDirectory)) {
                    return CompletableFuture.allOf(files.filter((Path path) -> path.toString().endsWith(".nbt"))
                            .map((Path path) -> CompletableFuture.runAsync(() -> {
                                String name = this.getName(inputDirectory, path);
                                try {
                                    this.convertStructure(cache, outputDirectory, path, name);
                                } catch (Exception exception) {
                                    failures.add(new RuntimeException("Failed to convert structure " + path, exception));
                                }
                            }, Util.backgroundExecutor().forName(this.getName())))
                            .toArray(CompletableFuture[]::new));
                } catch (Exception exception) {
                    failures.add(new RuntimeException("Failed to read structure input directory " + inputDirectory, exception));
                    return CompletableFuture.<Void>completedFuture(null);
                }
            }, Util.backgroundExecutor().forName(this.getName())).thenCompose(Function.identity()));
        }

        return Util.sequenceFailFast(tasks).thenRun(() -> {
            if (!failures.isEmpty()) {
                RuntimeException exception = new RuntimeException("Failed to convert " + failures.size()
                        + " structure template file(s)");
                failures.forEach(exception::addSuppressed);
                throw exception;
            }
        });
    }

    protected String getName(Path inputDirectory, Path structureFile) {
        String name = inputDirectory.relativize(structureFile).toString().replace('\\', '/');
        return name.substring(0, name.length() - ".nbt".length());
    }

    protected void convertStructure(CachedOutput cache, Path outputDirectory, Path structureFile, String name) {
        try {
            CompoundTag tag = NbtIo.readCompressed(structureFile, NbtAccounter.unlimitedHeap());
            CompoundTag upgradedTag = StructureUpdater.update(name, tag);
            this.writeNbt(cache, outputDirectory, name, upgradedTag);
            this.writeSnbt(cache, outputDirectory, name, upgradedTag);
        } catch (IOException exception) {
            throw new RuntimeException("Failed to convert structure " + structureFile, exception);
        }
    }

    @SuppressWarnings("UnstableApiUsage")
    protected void writeNbt(CachedOutput cache, Path outputDirectory, String name, CompoundTag tag) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);
        NbtIo.writeCompressed(tag, hashingOutputStream);
        Path path = outputDirectory.resolve(name + ".nbt");
        cache.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }

    protected void writeSnbt(CachedOutput cache, Path outputDirectory, String name, CompoundTag tag) throws IOException {
        Path path = outputDirectory.resolve(name + ".snbt");
        NbtToSnbt.writeSnbt(cache, path, NbtUtils.structureToSnbt(tag));
    }

    @Override
    public String getName() {
        return "Structure Templates";
    }
}
