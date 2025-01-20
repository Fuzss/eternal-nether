package com.izofar.bygonenether.util;

import com.google.common.collect.ImmutableList;
import com.izofar.bygonenether.init.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.BasaltColumnsFeature;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
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

    public static void addBasaltRestrictions() {
        BasaltColumnsFeature.CANNOT_PLACE_ON = ImmutableList.of(
                // Default
                Blocks.LAVA,
                Blocks.BEDROCK,
                Blocks.MAGMA_BLOCK,
                Blocks.SOUL_SAND,
                Blocks.NETHER_BRICKS,
                Blocks.NETHER_BRICK_FENCE,
                Blocks.NETHER_BRICK_STAIRS,
                Blocks.NETHER_WART,
                Blocks.CHEST,
                Blocks.SPAWNER,
                // New Fortresses:
                Blocks.NETHER_BRICK_SLAB,
                Blocks.CRACKED_NETHER_BRICKS,
                Blocks.CHISELED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICK_STAIRS,
                Blocks.RED_NETHER_BRICK_SLAB,
                Blocks.CRIMSON_TRAPDOOR,
                // Wither Forts:
                ModBlocks.COBBLED_BLACKSTONE.get(),
                ModBlocks.WITHERED_BLACKSTONE.get(),
                ModBlocks.CHISELED_WITHERED_BLACKSTONE.get(),
                ModBlocks.CRACKED_WITHERED_BLACKSTONE.get(),
                ModBlocks.WITHERED_DEBRIS.get(),
                Blocks.IRON_BARS,
                Blocks.COAL_BLOCK);
        DeltaFeature.CANNOT_REPLACE = ImmutableList.of(
                // Default
                Blocks.BEDROCK,
                Blocks.NETHER_BRICKS,
                Blocks.NETHER_BRICK_FENCE,
                Blocks.NETHER_BRICK_STAIRS,
                Blocks.NETHER_WART,
                Blocks.CHEST,
                Blocks.SPAWNER,
                // New Fortresses:
                Blocks.NETHER_BRICK_SLAB,
                Blocks.CRACKED_NETHER_BRICKS,
                Blocks.CHISELED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICK_STAIRS,
                Blocks.RED_NETHER_BRICK_SLAB,
                Blocks.CRIMSON_TRAPDOOR,
                // Wither Forts:
                ModBlocks.COBBLED_BLACKSTONE.get(),
                ModBlocks.WITHERED_BLACKSTONE.get(),
                ModBlocks.CHISELED_WITHERED_BLACKSTONE.get(),
                ModBlocks.CRACKED_WITHERED_BLACKSTONE.get(),
                ModBlocks.WITHERED_DEBRIS.get(),
                Blocks.IRON_BARS,
                Blocks.COAL_BLOCK);
    }
}
