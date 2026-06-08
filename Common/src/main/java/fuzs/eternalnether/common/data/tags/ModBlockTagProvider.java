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

public class ModBlockTagProvider extends AbstractTagProvider<Block> {

    public ModBlockTagProvider(DataProviderContext context) {
        super(Registries.BLOCK, context);
    }

    @Override
    public void addTags(HolderLookup.Provider provider) {
        ModBlockFamilies.getAllBlockSetFamilies().forEach((BlockSetFamily blockSetFamily) -> {
            this.generateFor(blockSetFamily.getBlockVariants(), VARIANT_STONE_BLOCK_TAGS);
        });
        this.tag(ModTags.WITHERED_BLOCK_TAG_KEY)
                .add(ModBlocks.WITHERED_BLACKSTONE.value(),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.STAIRS).value(),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.SLAB).value(),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.WALL).value(),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED).value(),
                        ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED).value(),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.STAIRS).value(),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.SLAB).value(),
                        ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.WALL).value(),
                        ModBlocks.WITHERED_BASALT.value(),
                        ModBlocks.WITHERED_COAL_BLOCK.value(),
                        ModBlocks.WITHERED_QUARTZ_BLOCK.value(),
                        ModBlocks.WITHERED_DEBRIS.value());
        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.COBBLED_BLACKSTONE.value(),
                        ModBlocks.SOUL_STONE.value(),
                        ModBlocks.WITHERED_BONE_BLOCK.value(),
                        ModBlocks.WARPED_NETHER_BRICKS.value(),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.STAIRS).value(),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.SLAB).value(),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.WALL).value(),
                        ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getBlock(BlockSetVariant.CHISELED).value())
                .addTag(ModTags.WITHERED_BLOCK_TAG_KEY);
        this.tag(BlockTags.NEEDS_DIAMOND_TOOL).addTag(ModTags.WITHERED_BLOCK_TAG_KEY);
        this.tag(BlockTags.WITHER_SUMMON_BASE_BLOCKS).add(ModBlocks.SOUL_STONE);
    }
}
