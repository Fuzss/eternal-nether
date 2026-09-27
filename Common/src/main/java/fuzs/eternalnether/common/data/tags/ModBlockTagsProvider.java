package fuzs.eternalnether.common.data.tags;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.eternalnether.common.init.ModTags;
import fuzs.puzzleslib.common.api.data.v3.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v3.tags.AbstractTagsProvider;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.references.BlockItemIds;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;

public class ModBlockTagsProvider extends AbstractTagsProvider<Block> {

    public ModBlockTagsProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        ModBlockFamilies.getAllBlockSetFamilies().forEach((BlockSetFamily blockSetFamily) -> {
            this.generateFor(blockSetFamily.getBlockVariants(), VARIANT_STONE_BLOCK_TAGS);
        });
        this.tag(ModTags.Blocks.WITHERED)
                .add(ModBlocks.WITHERED_BLACKSTONE,
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.STAIRS),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.SLAB),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.WALL),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.STAIRS),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.SLAB),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.WALL),
                        ModBlocks.WITHERED_BASALT,
                        ModBlocks.WITHERED_COAL_BLOCK,
                        ModBlocks.WITHERED_QUARTZ_BLOCK,
                        ModBlocks.WITHERED_DEBRIS);
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COBBLED_BLACKSTONE,
                        ModBlocks.SOUL_STONE,
                        ModBlocks.WITHERED_BONE_BLOCK,
                        ModBlocks.WARPED_NETHER_BRICKS,
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.STAIRS),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.SLAB),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.WALL),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.CHISELED))
                .addTag(ModTags.Blocks.WITHERED);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).addTag(ModTags.Blocks.WITHERED);
        this.tag(BlockTags.WITHER_SUMMON_BASE_BLOCKS).add(ModBlocks.SOUL_STONE);
        this.tag(BlockTags.CANNOT_PLACE_BASALT_PILLAR_ON)
                // New Fortresses
                .add(BlockItemIds.NETHER_BRICK_SLAB.block(),
                        BlockItemIds.CRACKED_NETHER_BRICKS.block(),
                        BlockItemIds.CHISELED_NETHER_BRICKS.block(),
                        BlockItemIds.RED_NETHER_BRICKS.block(),
                        BlockItemIds.RED_NETHER_BRICK_STAIRS.block(),
                        BlockItemIds.RED_NETHER_BRICK_SLAB.block(),
                        BlockItemIds.CRIMSON_TRAPDOOR.block(),
                        BlockItemIds.IRON_BARS.block(),
                        BlockItemIds.COAL_BLOCK.block())
                // Wither Forts
                .add(ModBlocks.COBBLED_BLACKSTONE,
                        ModBlocks.WITHERED_BLACKSTONE,
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
                        ModBlocks.WITHERED_DEBRIS);
        this.tag(ModTags.Blocks.BASALT_DELTA_CANNOT_REPLACE)
                .addAll(DeltaFeature.CANNOT_REPLACE.stream()
                        .map(Block::builtInRegistryHolder)
                        .map(Holder.Reference::key))
                // New Fortresses
                .add(BlockItemIds.NETHER_BRICK_SLAB.block(),
                        BlockItemIds.CRACKED_NETHER_BRICKS.block(),
                        BlockItemIds.CHISELED_NETHER_BRICKS.block(),
                        BlockItemIds.RED_NETHER_BRICKS.block(),
                        BlockItemIds.RED_NETHER_BRICK_STAIRS.block(),
                        BlockItemIds.RED_NETHER_BRICK_SLAB.block(),
                        BlockItemIds.CRIMSON_TRAPDOOR.block())
                // Wither Forts
                .add(BlockItemIds.IRON_BARS.block(), BlockItemIds.COAL_BLOCK.block())
                .add(ModBlocks.COBBLED_BLACKSTONE,
                        ModBlocks.WITHERED_BLACKSTONE,
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED),
                        ModBlocks.WITHERED_DEBRIS);
    }
}
