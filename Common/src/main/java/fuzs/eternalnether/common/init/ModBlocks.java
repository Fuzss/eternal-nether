package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.world.level.block.NetheriteBellBlock;
import fuzs.eternalnether.common.world.level.block.entity.NetheriteBellBlockEntity;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;

public final class ModBlocks {
    public static final Holder.Reference<Block> COBBLED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlock(
            "cobbled_blackstone",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE).strength(2.0F, 6.0F));

    public static final Holder.Reference<Block> WITHERED_BLACKSTONE = ModRegistry.REGISTRIES.registerBlock(
            "withered_blackstone",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BLACKSTONE)
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.DEEPSLATE));
    public static final Holder.Reference<Block> WARPED_NETHER_BRICKS = ModRegistry.REGISTRIES.registerBlock(
            "warped_nether_bricks",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.NETHER_BRICKS).mapColor(MapColor.WARPED_STEM));

    public static final Holder.Reference<Block> WITHERED_BASALT = ModRegistry.REGISTRIES.registerBlock("withered_basalt",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BASALT).strength(50.0F, 1200.0F));
    public static final Holder.Reference<Block> WITHERED_COAL_BLOCK = ModRegistry.REGISTRIES.registerBlock(
            "withered_coal_block",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.COAL_BLOCK).strength(50.0F, 1200.0F));
    public static final Holder.Reference<Block> WITHERED_QUARTZ_BLOCK = ModRegistry.REGISTRIES.registerBlock(
            "withered_quartz_block",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.QUARTZ_BLOCK).strength(50.0F, 1200.0F));
    public static final Holder.Reference<Block> WITHERED_DEBRIS = ModRegistry.REGISTRIES.registerBlock("withered_debris",
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.ANCIENT_DEBRIS).strength(50.0F, 1200.0F));

    public static final Holder.Reference<Block> SOUL_STONE = ModRegistry.REGISTRIES.registerBlock("soul_stone",
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.COLOR_BROWN)
                    .requiresCorrectToolForDrops()
                    .strength(1.5F, 6.0F));
    public static final Holder.Reference<Block> WITHERED_BONE_BLOCK = ModRegistry.REGISTRIES.registerBlock(
            "withered_bone_block",
            RotatedPillarBlock::new,
            () -> BlockBehaviour.Properties.ofFullCopy(Blocks.BONE_BLOCK).mapColor(MapColor.COLOR_BLACK));

    public static final Holder.Reference<Block> NETHERITE_BELL = ModRegistry.REGISTRIES.registerBlock("netherite_bell",
            NetheriteBellBlock::new,
            () -> BlockBehaviour.Properties.of()
                    .mapColor(MapColor.METAL)
                    .strength(50.0F, 1200.0F)
                    .sound(SoundType.ANVIL)
                    .forceSolidOn()
                    .pushReaction(PushReaction.DESTROY));

    public static final Holder.Reference<BlockEntityType<NetheriteBellBlockEntity>> NETHERITE_BELL_BLOCK_ENTITY_TYPE = ModRegistry.REGISTRIES.registerBlockEntityType(
            "netherite_bell",
            NetheriteBellBlockEntity::new,
            NETHERITE_BELL);

    public static void boostrap() {
        // NO-OP
    }
}
