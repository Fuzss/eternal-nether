package fuzs.eternalnether.init;

import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;

public final class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(EternalNether.MOD_ID);
    public static final ResourceKey<PlacedFeature> SOUL_STONE_BLOBS_PLACED_FEATURE = REGISTRIES.makeResourceKey(
            Registries.PLACED_FEATURE,
            "soul_stone_blobs");
    static final TagFactory TAGS = TagFactory.make(EternalNether.MOD_ID);
    public static final TagKey<Block> WITHERED_BLOCK_TAG_KEY = TAGS.registerBlockTag("withered");
    public static final TagKey<Biome> HAS_CATACOMB_BIOME_TAG_KEY = TAGS.registerBiomeTag("has_structure/catacomb");
    public static final TagKey<Biome> HAS_CITADEL_BIOME_TAG_KEY = TAGS.registerBiomeTag("has_structure/citadel");
    public static final TagKey<Biome> HAS_PIGLIN_MANOR_BIOME_TAG_KEY = TAGS.registerBiomeTag(
            "has_structure/piglin_manor");

    public static void boostrap() {
        ModBlocks.boostrap();
        ModEntityTypes.boostrap();
        ModItems.boostrap();
        ModFeatures.boostrap();
        ModSensorTypes.boostrap();
        ModSoundEvents.boostrap();
        ModStructures.boostrap();
    }
}
