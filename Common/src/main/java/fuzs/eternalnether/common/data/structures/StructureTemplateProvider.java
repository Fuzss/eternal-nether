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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
        for (Path inputDirectory : this.inputs) {
            if (!Files.isDirectory(inputDirectory)) {
                continue;
            }

            List<Path> structureFiles = new ArrayList<>();
            try (Stream<Path> files = Files.walk(inputDirectory)) {
                files.filter((Path path) -> path.toString().endsWith(".nbt")).forEach(structureFiles::add);
            } catch (IOException exception) {
                throw new RuntimeException("Failed to read structure input directory " + inputDirectory, exception);
            }

            for (Path structureFile : structureFiles) {
                this.convertStructure(cache, outputDirectory, inputDirectory, structureFile);
            }
        }

        return CompletableFuture.completedFuture(null);
    }

    private void convertStructure(CachedOutput cache, Path outputDirectory, Path inputDirectory, Path structureFile) {
        String name = inputDirectory.relativize(structureFile).toString().replace('\\', '/');
        name = name.substring(0, name.length() - ".nbt".length());
        try {
            CompoundTag tag = NbtIo.readCompressed(structureFile, NbtAccounter.unlimitedHeap());
            CompoundTag upgradedTag = StructureUpdater.update(name, tag);
            this.writeNbt(cache, outputDirectory.resolve(name + ".nbt"), upgradedTag);
            NbtToSnbt.writeSnbt(cache, outputDirectory.resolve(name + ".snbt"), NbtUtils.structureToSnbt(upgradedTag));
        } catch (IOException exception) {
            throw new RuntimeException("Failed to convert structure " + structureFile, exception);
        }
    }

    private void writeNbt(CachedOutput cache, Path path, CompoundTag tag) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        HashingOutputStream hashingOutputStream = new HashingOutputStream(Hashing.sha1(), byteArrayOutputStream);
        NbtIo.writeCompressed(tag, hashingOutputStream);
        cache.writeIfNeeded(path, byteArrayOutputStream.toByteArray(), hashingOutputStream.hash());
    }

    @Override
    public String getName() {
        return "Structure Templates";
    }
}
