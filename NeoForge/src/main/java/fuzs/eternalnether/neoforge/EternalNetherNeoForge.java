package fuzs.eternalnether.neoforge;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.data.ModAdvancementProvider;
import fuzs.eternalnether.common.data.ModRecipeProvider;
import fuzs.eternalnether.common.data.loot.ModBlockLootProvider;
import fuzs.eternalnether.common.data.loot.ModChestLootProvider;
import fuzs.eternalnether.common.data.loot.ModEntityTypeLootProvider;
import fuzs.eternalnether.common.data.loot.ModShearingLootProvider;
import fuzs.eternalnether.common.data.tags.*;
import fuzs.eternalnether.common.init.ModRegistry;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(EternalNether.MOD_ID)
public class EternalNetherNeoForge {

    public EternalNetherNeoForge() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
        DataProviderHelper.registerDataProviders(EternalNether.MOD_ID,
                ModRegistry.REGISTRY_SET_BUILDER,
                ModBlockTagsProvider::new,
                ModItemTagsProvider::new,
                ModEntityTypeTagsProvider::new,
                ModBiomeTagsProvider::new,
                ModTrimMaterialTagsProvider::new,
                ModDamageTypeTagsProvider::new,
                ModBlockLootProvider::new,
                ModEntityTypeLootProvider::new,
                ModChestLootProvider::new,
                ModShearingLootProvider::new,
                ModRecipeProvider::new,
                ModAdvancementProvider::new);
    }
}
