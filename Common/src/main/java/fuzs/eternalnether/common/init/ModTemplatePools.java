package fuzs.eternalnether.common.init;

import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;
import java.util.function.Function;

public final class ModTemplatePools {
    public static final ResourceKey<StructureTemplatePool> CATACOMB_START_POOL = register("catacomb/start_pool");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_SHOULDER = register("catacomb/shoulder");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_SPINE = register("catacomb/spine");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_TAIL = register("catacomb/tail");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_LEFT_HEAD = register("catacomb/left_head");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_RIGHT_HEAD = register("catacomb/right_head");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_LEFT_RIB = register("catacomb/left_rib");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_LEFT_RIB_BENT = register("catacomb/left_rib_bent");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_RIGHT_RIB = register("catacomb/right_rib");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_RIGHT_RIB_BENT = register("catacomb/right_rib_bent");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_MOBS = register("catacomb/catacomb_mobs");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_PIGLIN_PRISONER = register("catacomb/piglin_prisoner");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_WITHER_SKELETON = register("catacomb/wither_skeleton");
    public static final ResourceKey<StructureTemplatePool> CITADEL_START_POOL = register("citadel/start_pool");
    public static final ResourceKey<StructureTemplatePool> CITADEL_BRIDGE = register("citadel/bridge");
    public static final ResourceKey<StructureTemplatePool> CITADEL_BRIDGE_TOP = register("citadel/bridge_top");
    public static final ResourceKey<StructureTemplatePool> CITADEL_TOP = register("citadel/top");
    public static final ResourceKey<StructureTemplatePool> CITADEL_WARPED_ENDERMAN = register("citadel/warped_enderman");
    public static final ResourceKey<StructureTemplatePool> PIGLIN_MANOR_START_POOL = register("piglin_manor/start_pool");
    public static final ResourceKey<StructureTemplatePool> PIGLIN_MANOR_PIGLIN_HORSE = register(
            "piglin_manor/piglin_horse");
    public static final ResourceKey<StructureTemplatePool> PIGLIN_MANOR_PIGLIN_MOBS = register("piglin_manor/piglin_mobs");
    public static final ResourceKey<StructureTemplatePool> PIGLIN_MANOR_STRIDER = register("piglin_manor/strider");

    private static ResourceKey<StructureTemplatePool> register(String path) {
        return ModRegistry.REGISTRIES.makeResourceKey(Registries.TEMPLATE_POOL, path);
    }

    private static StructureTemplatePool pool(Holder<StructureTemplatePool> fallback,
            List<Pair<Function<StructureTemplatePool.Projection, ? extends StructurePoolElement>, Integer>> elements) {
        return new StructureTemplatePool(fallback, elements, StructureTemplatePool.Projection.RIGID);
    }

    public static void bootstrap(BootstrapContext<StructureTemplatePool> context) {
        HolderGetter<StructureTemplatePool> pools = context.lookup(Registries.TEMPLATE_POOL);
        Holder<StructureTemplatePool> empty = pools.getOrThrow(Pools.EMPTY);
        HolderGetter<StructureProcessorList> processors = context.lookup(Registries.PROCESSOR_LIST);
        Holder<StructureProcessorList> skull = processors.getOrThrow(ModProcessorLists.CATACOMB_SKULL);
        Holder<StructureProcessorList> withered = processors.getOrThrow(ModProcessorLists.WITHERED_BLACKSTONE);
        Holder<StructureProcessorList> citadel = processors.getOrThrow(ModProcessorLists.CITADEL);
        Holder<StructureProcessorList> manor = processors.getOrThrow(ModProcessorLists.PIGLIN_MANOR);
        HolderGetter<PlacedFeature> features = context.lookup(Registries.PLACED_FEATURE);

        context.register(CATACOMB_START_POOL, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/large_skull", skull), 1))));
        context.register(CATACOMB_SHOULDER, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/shoulder", withered), 1))));
        context.register(CATACOMB_SPINE, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/spine", withered), 1))));
        context.register(CATACOMB_TAIL, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/tail", withered), 1))));
        context.register(CATACOMB_LEFT_HEAD, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/left_small_skull", skull), 1))));
        context.register(CATACOMB_RIGHT_HEAD, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:catacomb/right_small_skull", skull), 1))));
        context.register(CATACOMB_LEFT_RIB, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/straight_treasure", withered), 2),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/straight_prison", withered), 1),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/straight_portal", withered), 1))));
        context.register(CATACOMB_LEFT_RIB_BENT, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/bent_treasure", withered), 3),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/bent_spawner", withered), 3),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/bent_prison", withered), 2),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/left_rib/bent_wither", withered), 2))));
        context.register(CATACOMB_RIGHT_RIB, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/straight_treasure", withered), 2),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/straight_prison", withered), 1),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/straight_portal", withered), 1))));
        context.register(CATACOMB_RIGHT_RIB_BENT, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/bent_treasure", withered), 2),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/bent_prison", withered), 1),
                Pair.of(StructurePoolElement.legacy("eternalnether:catacomb/right_rib/bent_portal", withered), 1))));
        context.register(CATACOMB_MOBS, pool(pools.getOrThrow(CATACOMB_MOBS), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_CATACOMB)), 1))));
        context.register(CATACOMB_PIGLIN_PRISONER, pool(pools.getOrThrow(CATACOMB_PIGLIN_PRISONER), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_PIGLIN_PRISONER)), 1))));
        context.register(CATACOMB_WITHER_SKELETON, pool(pools.getOrThrow(CATACOMB_WITHER_SKELETON), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_WITHER_SKELETON)), 1))));

        context.register(CITADEL_START_POOL, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:citadel/citadel_base", citadel), 1))));
        context.register(CITADEL_BRIDGE, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:citadel/citadel_bridge", citadel), 1))));
        context.register(CITADEL_BRIDGE_TOP, pool(empty, List.of(Pair.of(StructurePoolElement.legacy(
                "eternalnether:citadel/citadel_bridge_top", citadel), 1))));
        context.register(CITADEL_TOP, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:citadel/citadel_top", citadel), 4),
                Pair.of(StructurePoolElement.legacy("eternalnether:citadel/citadel_branch_top", citadel), 3))));
        context.register(CITADEL_WARPED_ENDERMAN, pool(pools.getOrThrow(CITADEL_WARPED_ENDERMAN), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_WARPED_ENDERMAN)), 1))));

        context.register(PIGLIN_MANOR_START_POOL, pool(empty, List.of(
                Pair.of(StructurePoolElement.legacy("eternalnether:piglin_manor/piglin_manor_1", manor), 4),
                Pair.of(StructurePoolElement.legacy("eternalnether:piglin_manor/piglin_manor_2", manor), 3))));
        context.register(PIGLIN_MANOR_PIGLIN_HORSE, pool(pools.getOrThrow(PIGLIN_MANOR_PIGLIN_HORSE), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_PIGLIN_MANOR_OUTSIDE)),
                1))));
        context.register(PIGLIN_MANOR_PIGLIN_MOBS, pool(pools.getOrThrow(PIGLIN_MANOR_PIGLIN_MOBS), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_PIGLIN_MANOR_INSIDE)),
                1))));
        context.register(PIGLIN_MANOR_STRIDER, pool(pools.getOrThrow(PIGLIN_MANOR_STRIDER), List.of(Pair.of(
                StructurePoolElement.feature(features.getOrThrow(ModPlacedFeatures.MOB_FEATURE_STRIDER)), 1))));
    }
}
