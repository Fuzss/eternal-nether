package fuzs.eternalnether.neoforge;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.data.advancements.ModAdvancementProvider;
import fuzs.eternalnether.common.data.recipes.ModRecipeProvider;
import fuzs.eternalnether.common.data.loot.ModBlockLootProvider;
import fuzs.eternalnether.common.data.loot.ModChestLootProvider;
import fuzs.eternalnether.common.data.loot.ModEntityLootProvider;
import fuzs.eternalnether.common.data.loot.ModShearingLootProvider;
import fuzs.eternalnether.common.data.tags.*;
import fuzs.eternalnether.common.init.ModRegistry;
import fuzs.eternalnether.common.init.ModStructureSets;
import fuzs.eternalnether.common.init.ModStructures;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v3.core.DataProviderBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.fml.common.Mod;

@Mod(EternalNether.MOD_ID)
public class EternalNetherNeoForge {

    public EternalNetherNeoForge() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
        DataProviderBuilder.of(EternalNether.MOD_ID)
                .addWorldBootstrap(Registries.JUKEBOX_SONG, ModRegistry::bootstrapJukeboxSongs)
                .addWorldBootstrap(Registries.STRUCTURE, ModStructures::bootstrapStructures)
                .addWorldBootstrap(Registries.STRUCTURE_SET, ModStructureSets::bootstrapStructureSets)
                .addProvider(ModBlockTagsProvider::new,
                        ModItemTagsProvider::new,
                        ModEntityTypeTagsProvider::new,
                        ModBiomeTagsProvider::new,
                        ModTrimMaterialTagsProvider::new,
                        ModDamageTypeTagsProvider::new)
                .addLootProvider(ModBlockLootProvider::new, LootContextParamSets.BLOCK)
                .addLootProvider(ModEntityLootProvider::new, LootContextParamSets.ENTITY)
                .addLootProvider(ModChestLootProvider::new, LootContextParamSets.CHEST)
                .addLootProvider(ModShearingLootProvider::new, LootContextParamSets.SHEARING)
                .addRecipeProvider(ModRecipeProvider::new)
                .addAdvancementProvider(ModAdvancementProvider::new);
    }
}
