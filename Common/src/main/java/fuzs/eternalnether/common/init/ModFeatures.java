package fuzs.eternalnether.common.init;

import com.google.common.collect.ImmutableList;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobFeature;
import fuzs.eternalnether.common.world.level.levelgen.feature.MobPassengerFeature;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.feature.DeltaFeature;
import net.minecraft.world.level.levelgen.feature.Feature;

@SuppressWarnings("unchecked")
public final class ModFeatures {
    public static final WeightedList<Holder<EntityType<?>>> PIGLIN_MANOR_MOBS;
    public static final WeightedList<Holder<EntityType<?>>> CATACOMB_MOBS;
    public static final WeightedList<Holder<EntityType<?>>> PIGLIN_PRISONER_CONVERSIONS;

    static {
        PIGLIN_MANOR_MOBS = WeightedList.of(weighted(ModEntityTypes.PIGLIN_HUNTER, 1),
                weighted(EntityTypes.PIGLIN.builtInRegistryHolder(), 3));
        CATACOMB_MOBS = WeightedList.of(weighted(ModEntityTypes.CORPOR, 1),
                weighted(ModEntityTypes.WITHER_SKELETON_KNIGHT, 2),
                weighted(ModEntityTypes.WRAITHER, 3),
                weighted(EntityTypes.WITHER_SKELETON.builtInRegistryHolder(), 1));
        PIGLIN_PRISONER_CONVERSIONS = WeightedList.of(weighted(EntityTypes.PIGLIN.builtInRegistryHolder(), 4),
                weighted(ModEntityTypes.PIGLIN_HUNTER, 3),
                weighted(EntityTypes.PIGLIN_BRUTE.builtInRegistryHolder(), 1));
    }

    private static Weighted<Holder<EntityType<?>>> weighted(Holder<? extends EntityType<?>> entityType, int weight) {
        return new Weighted<>((Holder<EntityType<?>>) (Holder<?>) entityType, weight);
    }

    public static final Holder.Reference<Feature> MOB_FEATURE_PIGLIN_PRISONER = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_prisoner",
            () -> new MobFeature(ModEntityTypes.PIGLIN_PRISONER));
    public static final Holder.Reference<Feature> MOB_FEATURE_PIGLIN_MANOR_INSIDE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_manor_inside",
            () -> new MobFeature(PIGLIN_MANOR_MOBS));
    public static final Holder.Reference<Feature> MOB_FEATURE_PIGLIN_MANOR_OUTSIDE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_manor_outside",
            () -> new MobPassengerFeature(ModEntityTypes.PIGLIN_HUNTER, ModEntityTypes.WITHER_SKELETON_HORSE));
    public static final Holder.Reference<Feature> MOB_FEATURE_STRIDER = ModRegistry.REGISTRIES.register(Registries.FEATURE,
            "mob_feature_strider",
            () -> new MobFeature(EntityTypes.STRIDER.builtInRegistryHolder()));
    public static final Holder.Reference<Feature> MOB_FEATURE_WITHER_SKELETON = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_wither_skeleton",
            () -> new MobFeature(EntityTypes.WITHER_SKELETON.builtInRegistryHolder()));
    public static final Holder.Reference<Feature> MOB_FEATURE_CATACOMB = ModRegistry.REGISTRIES.register(Registries.FEATURE,
            "mob_feature_catacomb",
            () -> new MobFeature(CATACOMB_MOBS));
    public static final Holder.Reference<Feature> MOB_FEATURE_WARPED_ENDERMAN = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_warped_enderman",
            () -> new MobFeature(ModEntityTypes.WARPED_ENDERMAN));

    public static void boostrap() {
        // NO-OP
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
