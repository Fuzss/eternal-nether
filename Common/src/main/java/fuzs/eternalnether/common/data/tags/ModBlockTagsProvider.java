package fuzs.eternalnether.common.data.tags;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.eternalnether.common.init.ModTags;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.data.v2.tags.AbstractTagProvider;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;

public class ModBlockTagsProvider extends AbstractTagProvider<Block> {

    public ModBlockTagsProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        ModBlockFamilies.getAllBlockSetFamilies().forEach((BlockSetFamily blockSetFamily) -> {
            this.generateFor(blockSetFamily.getBlockVariants(), VARIANT_STONE_BLOCK_TAGS);
        });
        this.tag(ModTags.WITHERED_BLOCK_TAG_KEY)
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
                .addTag(ModTags.WITHERED_BLOCK_TAG_KEY);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).addTag(ModTags.WITHERED_BLOCK_TAG_KEY);
        this.tag(BlockTags.WITHER_SUMMON_BASE_BLOCKS).add(ModBlocks.SOUL_STONE);
    }
}
