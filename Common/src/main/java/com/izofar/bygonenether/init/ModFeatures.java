package com.izofar.bygonenether.init;

import com.izofar.bygonenether.world.level.levelgen.feature.MobFeature;
import com.izofar.bygonenether.world.level.levelgen.feature.MobPassengerFeature;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.util.random.WeightedEntry;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

@SuppressWarnings("unchecked")
public abstract class ModFeatures {
    public static final WeightedRandomList<WeightedEntry.Wrapper<Holder<? extends EntityType<? extends Mob>>>> PIGLIN_MANOR_MOBS = WeightedRandomList.create(
            WeightedEntry.wrap(ModEntityTypes.PIGLIN_HUNTER, 1),
            WeightedEntry.wrap((Holder<? extends EntityType<? extends Mob>>) EntityType.PIGLIN.builtInRegistryHolder(),
                    3));
    public static final WeightedRandomList<WeightedEntry.Wrapper<Holder<? extends EntityType<? extends Mob>>>> CATACOMB_MOBS = WeightedRandomList.create(
            WeightedEntry.wrap(ModEntityTypes.CORPOR, 1),
            WeightedEntry.wrap(ModEntityTypes.WITHER_SKELETON_KNIGHT, 2),
            WeightedEntry.wrap(ModEntityTypes.WRAITHER, 3),
            WeightedEntry.wrap((Holder<? extends EntityType<? extends Mob>>) EntityType.WITHER_SKELETON, 1));
    public static final WeightedRandomList<WeightedEntry.Wrapper<Holder<? extends EntityType<? extends Mob>>>> PIGLIN_PRISONER_CONVERSIONS = WeightedRandomList.create(
            WeightedEntry.wrap((Holder<? extends EntityType<? extends Mob>>) EntityType.PIGLIN, 4),
            WeightedEntry.wrap(ModEntityTypes.PIGLIN_HUNTER, 3),
            WeightedEntry.wrap((Holder<? extends EntityType<? extends Mob>>) EntityType.PIGLIN_BRUTE, 1));

    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_PIGLIN_PRISONER = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_prisoner",
            () -> new MobFeature(ModEntityTypes.PIGLIN_PRISONER));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_PIGLIN_MANOR_INSIDE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_manor_inside",
            () -> new MobFeature(PIGLIN_MANOR_MOBS));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_PIGLIN_MANOR_OUTSIDE = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_piglin_manor_outside",
            () -> new MobPassengerFeature(ModEntityTypes.PIGLIN_HUNTER, ModEntityTypes.WITHER_SKELETON_HORSE));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_STRIDER = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_strider",
            () -> new MobFeature((Holder<? extends EntityType<? extends Mob>>) EntityType.STRIDER.builtInRegistryHolder()));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_WITHER_SKELETON = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_wither_skeleton",
            () -> new MobFeature((Holder<? extends EntityType<? extends Mob>>) EntityType.WITHER_SKELETON.builtInRegistryHolder()));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_CATACOMB = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_catacomb",
            () -> new MobFeature(CATACOMB_MOBS));
    public static final Holder.Reference<Feature<NoneFeatureConfiguration>> MOB_FEATURE_WARPED_ENDERMAN = ModRegistry.REGISTRIES.register(
            Registries.FEATURE,
            "mob_feature_warped_enderman",
            () -> new MobFeature(ModEntityTypes.WARPED_ENDERMAN));

    public static void boostrap() {
        // NO-OP
    }
}
