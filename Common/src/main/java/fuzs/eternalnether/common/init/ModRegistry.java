package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.world.entity.monster.WarpedEnderman;
import fuzs.eternalnether.common.world.level.levelgen.structure.CatacombStructure;
import fuzs.eternalnether.common.world.level.levelgen.structure.CitadelStructure;
import fuzs.eternalnether.common.world.level.levelgen.structure.PiglinManorStructure;
import fuzs.puzzleslib.common.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.common.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.common.impl.item.CreativeModeTabHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collection;

public final class ModRegistry {
    public static final RegistrySetBuilder REGISTRY_SET_BUILDER = new RegistrySetBuilder().add(Registries.JUKEBOX_SONG,
                    ModRegistry::bootstrapJukeboxSongs)
            .add(Registries.STRUCTURE, ModStructures::bootstrapStructures)
            .add(Registries.STRUCTURE_SET, ModStructureSets::bootstrapStructureSets);
    static final RegistryManager REGISTRIES = RegistryManager.from(EternalNether.MOD_ID);
    public static final Holder.Reference<StructureType<PiglinManorStructure>> PIGLIN_MANOR_STRUCTURE_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "piglin_manor",
            () -> () -> PiglinManorStructure.CODEC);
    public static final Holder.Reference<StructureType<CitadelStructure>> CITADEL_STRUCTURE_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "citadel",
            () -> () -> CitadelStructure.CODEC);
    public static final Holder.Reference<StructureType<CatacombStructure>> CATACOMB_STRUCTURE_TYPE = REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "catacomb",
            () -> () -> CatacombStructure.CODEC);
    public static final Holder.Reference<EntityDataSerializer<WarpedEnderman.Variant>> WARPED_ENDER_MAN_VARIANT_ENTITY_DATA_SERIALIZER = REGISTRIES.registerEntityDataSerializer(
            "warped_ender_man_variant",
            () -> EntityDataSerializer.forValueType(WarpedEnderman.Variant.STREAM_CODEC));
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(() -> new ItemStack(
                    ModItems.WITHERED_DEBRIS),
            (CreativeModeTab.ItemDisplayParameters parameters, CreativeModeTab.Output output) -> {
                // TODO use proper Puzzles Lib method
                Collection<ItemStack> tabContents = ItemStackLinkedSet.createTypeAndComponentsSet();
                CreativeModeTab.Output filteredOutput = (ItemStack itemStack, CreativeModeTab.TabVisibility tabVisibility) -> {
                    if (!tabContents.contains(itemStack)
                            || tabVisibility == CreativeModeTab.TabVisibility.SEARCH_TAB_ONLY) {
                        tabContents.add(itemStack);
                        output.accept(itemStack, tabVisibility);
                    }
                };
                filteredOutput.accept(ModItems.COBBLED_BLACKSTONE.value());
                filteredOutput.accept(ModItems.WITHERED_BLACKSTONE.value());
                ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getItemVariants()
                        .values()
                        .forEach((Holder.Reference<Item> holder) -> {
                            filteredOutput.accept(holder.value());
                        });
                ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY.getItemVariants()
                        .values()
                        .forEach((Holder.Reference<Item> holder) -> {
                            filteredOutput.accept(holder.value());
                        });
                filteredOutput.accept(ModItems.WARPED_NETHER_BRICKS.value());
                ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY.getItemVariants()
                        .values()
                        .forEach((Holder.Reference<Item> holder) -> {
                            filteredOutput.accept(holder.value());
                        });
                CreativeModeTabHelper.getDisplayItems(EternalNether.MOD_ID).accept(parameters, filteredOutput);
            });
    public static final ResourceKey<PlacedFeature> SOUL_STONE_BLOBS_PLACED_FEATURE = REGISTRIES.makeResourceKey(
            Registries.PLACED_FEATURE,
            "soul_stone_blobs");
    public static final ResourceKey<LootTable> CITADEL_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/citadel");
    public static final ResourceKey<LootTable> CATACOMB_TREASURE_RIB_LOOT_TABLE = REGISTRIES.makeResourceKey(Registries.LOOT_TABLE,
            "chests/catacomb/treasure_rib");
    public static final ResourceKey<LootTable> SHEARING_WARPED_ENDER_MAN_LOOT_TABLE = REGISTRIES.makeResourceKey(
            Registries.LOOT_TABLE,
            "shearing/warped_ender_man");

    public static void boostrap() {
        ModBlocks.boostrap();
        ModEntityTypes.boostrap();
        ModItems.boostrap();
        ModBlockFamilies.bootstrap();
        ModFeatures.boostrap();
        ModSensorTypes.boostrap();
        ModSoundEvents.boostrap();
    }

    public static void bootstrapJukeboxSongs(BootstrapContext<JukeboxSong> context) {
        AbstractDatapackRegistriesProvider.registerJukeboxSong(context,
                ModItems.WITHER_WALTZ_JUKEBOX_SONG,
                ModSoundEvents.WITHER_WALTZ,
                5040.0F,
                4);
    }
}
