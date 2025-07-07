package fuzs.eternalnether.data.loot;

import fuzs.eternalnether.init.ModBlocks;
import fuzs.puzzleslib.api.data.v2.AbstractLootProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

import java.util.stream.Stream;

public class ModBlockLootProvider extends AbstractLootProvider.Blocks {

    public ModBlockLootProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addLootTables() {
        // TODO replace this with an injection instead of replacing the table in the future
        this.add(Blocks.BLACKSTONE, (Block block) -> {
            return this.createSingleItemTableWithSilkTouch(block, ModBlocks.COBBLED_BLACKSTONE.value());
        });
        this.dropSelf(ModBlocks.COBBLED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.WITHERED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.WITHERED_BLACKSTONE_STAIRS.value());
        this.add(ModBlocks.WITHERED_BLACKSTONE_SLAB.value(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.WITHERED_BLACKSTONE_WALL.value());
        this.dropSelf(ModBlocks.CRACKED_WITHERED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.CRACKED_WITHERED_BLACKSTONE_STAIRS.value());
        this.add(ModBlocks.CRACKED_WITHERED_BLACKSTONE_SLAB.value(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.CRACKED_WITHERED_BLACKSTONE_WALL.value());
        this.dropSelf(ModBlocks.CHISELED_WITHERED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.WITHERED_BASALT.value());
        this.dropSelf(ModBlocks.WITHERED_COAL_BLOCK.value());
        this.dropSelf(ModBlocks.WITHERED_QUARTZ_BLOCK.value());
        this.dropSelf(ModBlocks.WITHERED_DEBRIS.value());
        this.dropSelf(ModBlocks.SOUL_STONE.value());
        this.dropSelf(ModBlocks.WITHERED_BONE_BLOCK.value());
        this.dropSelf(ModBlocks.WARPED_NETHER_BRICKS.value());
        this.dropSelf(ModBlocks.WARPED_NETHER_BRICK_STAIRS.value());
        this.add(ModBlocks.WARPED_NETHER_BRICK_SLAB.value(), this::createSlabItemTable);
        this.dropSelf(ModBlocks.WARPED_NETHER_BRICK_WALL.value());
        this.dropSelf(ModBlocks.CHISELED_WARPED_NETHER_BRICKS.value());
        this.dropSelf(ModBlocks.NETHERITE_BELL.value());
    }

    @Override
    protected Stream<Holder.Reference<Block>> getRegistryEntries() {
        return Stream.concat(super.getRegistryEntries(), Stream.of(Blocks.BLACKSTONE.builtInRegistryHolder()));
    }
}
