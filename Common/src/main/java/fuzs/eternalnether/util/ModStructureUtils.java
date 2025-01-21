package fuzs.eternalnether.util;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Random;

public final class ModStructureUtils {

    private ModStructureUtils() {
        // NO-OP
    }

    public static boolean isLavaLake(NoiseColumn blockReader) {
        boolean isLake = true;
        if (!blockReader.getBlock(31).is(Blocks.LAVA)) {
            isLake = false;
        } else {
            for (int i = 32; i < 70; i++) {
                isLake &= blockReader.getBlock(i).is(BlockTags.AIR);
            }
        }
        return isLake;
    }

    public static boolean isBuried(NoiseColumn blockReader, int minHeight, int maxHeight) {
        for (int i = minHeight; i < maxHeight; i++) {
            if (blockReader.getBlock(i + 1).is(BlockTags.AIR) && !blockReader.getBlock(i).is(BlockTags.AIR)) {
                return false;
            }
        }
        return true;
    }

    public static boolean verticalSpace(NoiseColumn blockReader, int minHeight, int maxHeight, int structureHeight) {
        int newHeight = 0;
        for (int i = maxHeight; i >= minHeight && newHeight < structureHeight; i--) {
            if (blockReader.getBlock(i).is(BlockTags.AIR)) {
                newHeight++;
            } else {
                newHeight = 0;
            }
        }
        return newHeight == structureHeight;
    }

    public static BlockPos getElevation(Structure.GenerationContext context, int minHeight, int maxHeight) {
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        NoiseColumn blockReader = context.chunkGenerator()
                .getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor(), context.randomState());

        boolean found = false;
        for (int i = minHeight; i < maxHeight; i++) {
            if (blockReader.getBlock(i + 1).is(BlockTags.AIR) && !blockReader.getBlock(i).is(BlockTags.AIR)) {
                blockpos = new BlockPos(blockpos.getX(), i, blockpos.getZ());
                found = true;
            }
        }
        if (!found) {
            blockpos = new BlockPos(blockpos.getX(),
                    new Random(context.seed()).nextInt(maxHeight - minHeight) + minHeight,
                    blockpos.getZ());
        }
        return blockpos;
    }

    public static int getScaledNetherHeight(Structure.GenerationContext context, int maxHeight) {
        return (int) (maxHeight / 128.0F * context.chunkGenerator().getGenDepth());
    }
}
