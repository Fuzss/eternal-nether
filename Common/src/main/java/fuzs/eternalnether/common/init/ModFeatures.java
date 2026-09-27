package fuzs.eternalnether.common.init;

import com.google.common.collect.ImmutableList;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobFeature;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobPassengerFeature;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.ReplaceBlobsFeature;

public final class ModFeatures {
    public static final ResourceKey<Feature> MOB_FEATURE_PIGLIN_PRISONER = register("mob_feature_piglin_prisoner");
    public static final ResourceKey<Feature> MOB_FEATURE_PIGLIN_MANOR_INSIDE = register(
            "mob_feature_piglin_manor_inside");
    public static final ResourceKey<Feature> MOB_FEATURE_PIGLIN_MANOR_OUTSIDE = register(
            "mob_feature_piglin_manor_outside");
    public static final ResourceKey<Feature> MOB_FEATURE_STRIDER = register("mob_feature_strider");
    public static final ResourceKey<Feature> MOB_FEATURE_WITHER_SKELETON = register("mob_feature_wither_skeleton");
    public static final ResourceKey<Feature> MOB_FEATURE_CATACOMB = register("mob_feature_catacomb");
    public static final ResourceKey<Feature> MOB_FEATURE_WARPED_ENDERMAN = register("mob_feature_warped_enderman");
    public static final ResourceKey<Feature> SOUL_STONE_BLOBS = register("soul_stone_blobs");
    public static final WeightedList<Holder<EntityType<?>>> PIGLIN_MANOR_MOBS = WeightedList.of(new Weighted<>(cast(
            ModEntityTypes.PIGLIN_HUNTER), 1), new Weighted<>(cast(EntityTypes.PIGLIN.builtInRegistryHolder()), 3));
    public static final WeightedList<Holder<EntityType<?>>> CATACOMB_MOBS = WeightedList.of(new Weighted<>(cast(
                    ModEntityTypes.CORPOR), 1),
            new Weighted<>(cast(ModEntityTypes.WITHER_SKELETON_KNIGHT), 2),
            new Weighted<>(cast(ModEntityTypes.WRAITHER), 3),
            new Weighted<>(cast(EntityTypes.WITHER_SKELETON.builtInRegistryHolder()), 1));
    public static final WeightedList<Holder<EntityType<?>>> PIGLIN_PRISONER_CONVERSIONS = WeightedList.of(new Weighted<>(
                    cast(EntityTypes.PIGLIN.builtInRegistryHolder()),
                    4),
            new Weighted<>(cast(ModEntityTypes.PIGLIN_HUNTER), 3),
            new Weighted<>(cast(EntityTypes.PIGLIN_BRUTE.builtInRegistryHolder()), 1));

    private static ResourceKey<Feature> register(String path) {
        return ModRegistry.REGISTRIES.makeResourceKey(Registries.FEATURE, path);
    }

    @SuppressWarnings("unchecked")
    private static Holder<EntityType<?>> cast(Holder<? extends EntityType<?>> entityType) {
        return (Holder<EntityType<?>>) (Holder<?>) entityType;
    }

    public static void bootstrap(BootstrapContext<Feature> context) {
        context.register(MOB_FEATURE_PIGLIN_PRISONER, new MobFeature(cast(ModEntityTypes.PIGLIN_PRISONER)));
        context.register(MOB_FEATURE_PIGLIN_MANOR_INSIDE, new MobFeature(PIGLIN_MANOR_MOBS));
        context.register(MOB_FEATURE_PIGLIN_MANOR_OUTSIDE,
                new MobPassengerFeature(cast(ModEntityTypes.PIGLIN_HUNTER),
                        cast(ModEntityTypes.WITHER_SKELETON_HORSE)));
        context.register(MOB_FEATURE_STRIDER, new MobFeature(EntityTypes.STRIDER.builtInRegistryHolder()));
        context.register(MOB_FEATURE_WITHER_SKELETON,
                new MobFeature(EntityTypes.WITHER_SKELETON.builtInRegistryHolder()));
        context.register(MOB_FEATURE_CATACOMB, new MobFeature(CATACOMB_MOBS));
        context.register(MOB_FEATURE_WARPED_ENDERMAN, new MobFeature(cast(ModEntityTypes.WARPED_ENDERMAN)));
        context.register(SOUL_STONE_BLOBS,
                new ReplaceBlobsFeature(Blocks.NETHERRACK.defaultBlockState(),
                        ModBlocks.SOUL_STONE.value().defaultBlockState(),
                        UniformInt.of(3, 7)));
    }

    public static void setBasaltFeatureRestrictions() {
        DeltaFeature.CANNOT_REPLACE = ImmutableList.<Block>builder().addAll(DeltaFeature.CANNOT_REPLACE).add(
                // New Fortresses
                Blocks.NETHER_BRICK_SLAB,
                Blocks.CRACKED_NETHER_BRICKS,
                Blocks.CHISELED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICKS,
                Blocks.RED_NETHER_BRICK_STAIRS,
                Blocks.RED_NETHER_BRICK_SLAB,
                Blocks.CRIMSON_TRAPDOOR,
                // Wither Forts
                ModBlocks.COBBLED_BLACKSTONE.value(),
                ModBlocks.WITHERED_BLACKSTONE.value(),
                ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED).value(),
                ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED).value(),
                ModBlocks.WITHERED_DEBRIS.value(),
                Blocks.IRON_BARS,
                Blocks.COAL_BLOCK).build();
    }
}
