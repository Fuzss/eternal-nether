package fuzs.eternalnether.init;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.world.entity.monster.WarpedEnderMan;
import fuzs.eternalnether.world.level.levelgen.structure.CatacombStructure;
import fuzs.eternalnether.world.level.levelgen.structure.CitadelStructure;
import fuzs.eternalnether.world.level.levelgen.structure.PiglinManorStructure;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.item.equipment.trim.TrimMaterial;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.storage.loot.LootTable;

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
    public static final Holder.Reference<EntityDataSerializer<WarpedEnderMan.Variant>> WARPED_ENDER_MAN_VARIANT_ENTITY_DATA_SERIALIZER = REGISTRIES.registerEntityDataSerializer(
            "warped_ender_man_variant",
            () -> EntityDataSerializer.forValueType(WarpedEnderMan.Variant.STREAM_CODEC));
    public static final Holder.Reference<CreativeModeTab> CREATIVE_MODE_TAB = REGISTRIES.registerCreativeModeTab(
            ModItems.WITHERED_DEBRIS);
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

    static final TagFactory TAGS = TagFactory.make(EternalNether.MOD_ID);
    public static final TagKey<Block> WITHERED_BLOCK_TAG_KEY = TAGS.registerBlockTag("withered");
    public static final TagKey<Item> PIGLIN_BRUTE_SAFE_ARMOR_ITEM_TAG_KEY = TAGS.registerItemTag(
            "piglin_brute_safe_armor");
    public static final TagKey<TrimMaterial> PIGLIN_SAFE_TRIM_MATERIAL_TAG_KEY = TAGS.registerTagKey(Registries.TRIM_MATERIAL,
            "piglin_safe");
    public static final TagKey<DamageType> BYPASSES_CUTLASS_DAMAGE_TYPE_TAG_KEY = TAGS.registerTagKey(Registries.DAMAGE_TYPE,
            "bypasses_cutlass");
    public static final TagKey<Biome> HAS_CATACOMB_BIOME_TAG_KEY = TAGS.registerBiomeTag("has_structure/catacomb");
    public static final TagKey<Biome> HAS_CITADEL_BIOME_TAG_KEY = TAGS.registerBiomeTag("has_structure/citadel");
    public static final TagKey<Biome> HAS_PIGLIN_MANOR_BIOME_TAG_KEY = TAGS.registerBiomeTag(
            "has_structure/piglin_manor");
    public static final TagKey<Item> SHEAR_TOOLS_ITEM_TAG_KEY = TagFactory.COMMON.registerItemTag("tools/shear");
    public static final TagKey<Item> SHIELD_TOOLS_ITEM_TAG_KEY = TagFactory.COMMON.registerItemTag("tools/shield");

    public static void boostrap() {
        ModBlocks.boostrap();
        ModEntityTypes.boostrap();
        ModItems.boostrap();
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
