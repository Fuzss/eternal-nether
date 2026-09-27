package fuzs.eternalnether.common.data.loot;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.puzzleslib.common.api.data.v3.loot.AbstractBlockLootSubProvider;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetFamily;
import net.minecraft.data.loot.LootTableSubProvider;

public class ModBlockLootProvider extends AbstractBlockLootSubProvider {

    public ModBlockLootProvider(LootTableSubProvider.Context output) {
        super(output);
    }

    @Override
    public void generate() {
        ModBlockFamilies.getAllBlockSetFamilies().forEach((BlockSetFamily blockSetFamily) -> {
            this.generateFor(blockSetFamily, VARIANT_PROVIDERS);
        });
        this.dropSelf(ModBlocks.COBBLED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.WITHERED_BLACKSTONE.value());
        this.dropSelf(ModBlocks.WITHERED_BASALT.value());
        this.dropSelf(ModBlocks.WITHERED_COAL_BLOCK.value());
        this.dropSelf(ModBlocks.WITHERED_QUARTZ_BLOCK.value());
        this.dropSelf(ModBlocks.WITHERED_DEBRIS.value());
        this.dropSelf(ModBlocks.SOUL_STONE.value());
        this.dropSelf(ModBlocks.WITHERED_BONE_BLOCK.value());
        this.dropSelf(ModBlocks.WARPED_NETHER_BRICKS.value());
        this.dropSelf(ModBlocks.NETHERITE_BELL.value());
    }
}
