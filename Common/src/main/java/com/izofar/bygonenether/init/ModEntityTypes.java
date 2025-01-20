package com.izofar.bygonenether.init;

import com.google.common.collect.ImmutableList;
import com.izofar.bygonenether.world.level.block.entity.NetheriteBellBlockEntity;
import com.izofar.bygonenether.world.entity.animal.horse.WitherSkeletonHorse;
import com.izofar.bygonenether.world.entity.monster.*;
import com.izofar.bygonenether.world.entity.projectile.ThrownWarpedEnderpearl;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntityType;

public abstract class ModEntityTypes {
    public static final Holder.Reference<EntityType<Wex>> WEX = ModRegistry.REGISTRIES.registerEntityType("wex",
            () -> EntityType.Builder.of(Wex::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.4F, 0.8F)
                    .clientTrackingRange(8));
    public static final Holder.Reference<EntityType<WarpedEnderMan>> WARPED_ENDERMAN = ModRegistry.REGISTRIES.registerEntityType(
            "warped_enderman",
            () -> EntityType.Builder.of(WarpedEnderMan::new, MobCategory.MONSTER)
                    .fireImmune()
                    .sized(0.6F, 2.9F)
                    .clientTrackingRange(8));

    public static final Holder.Reference<EntityType<PiglinPrisoner>> PIGLIN_PRISONER = ModRegistry.REGISTRIES.registerEntityType(
            "piglin_prisoner",
            () -> EntityType.Builder.of(PiglinPrisoner::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8));
    public static final Holder.Reference<EntityType<PiglinHunter>> PIGLIN_HUNTER = ModRegistry.REGISTRIES.registerEntityType(
            "piglin_hunter",
            () -> EntityType.Builder.of(PiglinHunter::new, MobCategory.MONSTER)
                    .sized(0.6F, 1.95F)
                    .clientTrackingRange(8));

    public static final Holder.Reference<EntityType<Wraither>> WRAITHER = ModRegistry.REGISTRIES.registerEntityType(
            "wraither",
            () -> EntityType.Builder.of(Wraither::new, MobCategory.MONSTER)
                    .fireImmune()
                    .immuneTo(Blocks.WITHER_ROSE)
                    .sized(0.7F, 2.4F)
                    .clientTrackingRange(8));
    public static final Holder.Reference<EntityType<WitherSkeletonKnight>> WITHER_SKELETON_KNIGHT = ModRegistry.REGISTRIES.registerEntityType(
            "wither_skeleton_knight",
            () -> EntityType.Builder.of(WitherSkeletonKnight::new, MobCategory.MONSTER)
                    .fireImmune()
                    .immuneTo(Blocks.WITHER_ROSE)
                    .sized(0.7F, 2.4F)
                    .clientTrackingRange(8));
    public static final Holder.Reference<EntityType<Corpor>> CORPOR = ModRegistry.REGISTRIES.registerEntityType("corpor",
            () -> EntityType.Builder.of(Corpor::new, MobCategory.MONSTER)
                    .fireImmune()
                    .immuneTo(Blocks.WITHER_ROSE)
                    .sized(0.7F, 2.4F)
                    .clientTrackingRange(8));

    public static final Holder.Reference<EntityType<WitherSkeletonHorse>> WITHER_SKELETON_HORSE = ModRegistry.REGISTRIES.registerEntityType(
            "wither_skeleton_horse",
            () -> EntityType.Builder.of(WitherSkeletonHorse::new, MobCategory.CREATURE)
                    .fireImmune()
                    .sized(1.3964844F, 1.6F)
                    .clientTrackingRange(10));

    public static final Holder.Reference<EntityType<ThrownWarpedEnderpearl>> WARPED_ENDER_PEARL = ModRegistry.REGISTRIES.registerEntityType(
            "warped_ender_pearl",
            () -> EntityType.Builder.<ThrownWarpedEnderpearl>of(ThrownWarpedEnderpearl::new, MobCategory.MISC)
                    .sized(0.25F, 0.25F)
                    .clientTrackingRange(4)
                    .updateInterval(10));

    public static final Holder.Reference<BlockEntityType<NetheriteBellBlockEntity>> NETHERITE_BELL_BLOCK_ENTITY_TYPE = ModRegistry.REGISTRIES.registerBlockEntityType(
            "netherite_bell",
            () -> BlockEntityType.Builder.of(NetheriteBellBlockEntity::new, ModBlocks.NETHERITE_BELL.value()));

    public static void boostrap() {
        // NO-OP
    }

    public static void modifyPiglinMemoryAndSensors() {
        PiglinBrute.SENSOR_TYPES = ImmutableList.of(SensorType.NEAREST_LIVING_ENTITIES,
                SensorType.NEAREST_PLAYERS,
                SensorType.NEAREST_ITEMS,
                SensorType.HURT_BY,
                ModSensorTypes.PIGLIN_BRUTE_SPECIFIC_SENSOR.value());

        PiglinBrute.MEMORY_TYPES = ImmutableList.of(MemoryModuleType.LOOK_TARGET,
                MemoryModuleType.DOORS_TO_CLOSE,
                MemoryModuleType.NEAREST_LIVING_ENTITIES,
                MemoryModuleType.NEAREST_VISIBLE_LIVING_ENTITIES,
                MemoryModuleType.NEAREST_VISIBLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER,
                MemoryModuleType.NEAREST_VISIBLE_ADULT_PIGLINS,
                MemoryModuleType.NEARBY_ADULT_PIGLINS,
                MemoryModuleType.HURT_BY,
                MemoryModuleType.HURT_BY_ENTITY,
                MemoryModuleType.WALK_TARGET,
                MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE,
                MemoryModuleType.ATTACK_TARGET,
                MemoryModuleType.ATTACK_COOLING_DOWN,
                MemoryModuleType.INTERACTION_TARGET,
                MemoryModuleType.PATH,
                MemoryModuleType.ANGRY_AT,
                MemoryModuleType.NEAREST_VISIBLE_NEMESIS,
                MemoryModuleType.HOME,
                MemoryModuleType.UNIVERSAL_ANGER,
                ModMemoryModuleTypes.NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GILD_MEMORY_MODULE_TYPE.value());
    }
}
