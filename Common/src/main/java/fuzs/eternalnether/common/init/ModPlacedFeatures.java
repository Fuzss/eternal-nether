package fuzs.eternalnether.common.init;

import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.placement.*;

import java.util.List;

public final class ModPlacedFeatures {
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_PIGLIN_PRISONER = register("mob_feature_piglin_prisoner");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_PIGLIN_MANOR_INSIDE = register(
            "mob_feature_piglin_manor_inside");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_PIGLIN_MANOR_OUTSIDE = register(
            "mob_feature_piglin_manor_outside");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_STRIDER = register("mob_feature_strider");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_WITHER_SKELETON = register("mob_feature_wither_skeleton");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_CATACOMB = register("mob_feature_catacomb");
    public static final ResourceKey<PlacedFeature> MOB_FEATURE_WARPED_ENDERMAN = register("mob_feature_warped_enderman");
    public static final ResourceKey<PlacedFeature> SOUL_STONE_BLOBS = ModRegistry.REGISTRIES.makeResourceKey(Registries.PLACED_FEATURE,
            "soul_stone_blobs");

    private static ResourceKey<PlacedFeature> register(String path) {
        return ModRegistry.REGISTRIES.makeResourceKey(Registries.PLACED_FEATURE, path);
    }

    public static void bootstrap(BootstrapContext<PlacedFeature> context) {
        HolderGetter<Feature> features = context.lookup(Registries.FEATURE);
        context.register(MOB_FEATURE_PIGLIN_PRISONER,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_PIGLIN_PRISONER), List.of()));
        context.register(MOB_FEATURE_PIGLIN_MANOR_INSIDE,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_PIGLIN_MANOR_INSIDE), List.of()));
        context.register(MOB_FEATURE_PIGLIN_MANOR_OUTSIDE,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_PIGLIN_MANOR_OUTSIDE), List.of()));
        context.register(MOB_FEATURE_STRIDER,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_STRIDER), List.of()));
        context.register(MOB_FEATURE_WITHER_SKELETON,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_WITHER_SKELETON), List.of()));
        context.register(MOB_FEATURE_CATACOMB,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_CATACOMB), List.of()));
        context.register(MOB_FEATURE_WARPED_ENDERMAN,
                new PlacedFeature(features.getOrThrow(ModFeatures.MOB_FEATURE_WARPED_ENDERMAN), List.of()));
        context.register(SOUL_STONE_BLOBS,
                new PlacedFeature(features.getOrThrow(ModFeatures.SOUL_STONE_BLOBS),
                        List.of(CountPlacement.of(1),
                                InSquarePlacement.spread(),
                                HeightRangePlacement.uniform(VerticalAnchor.aboveBottom(0), VerticalAnchor.belowTop(0)),
                                BiomeFilter.biome())));
    }
}
