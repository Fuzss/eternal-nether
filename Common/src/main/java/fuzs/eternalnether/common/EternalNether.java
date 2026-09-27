package fuzs.eternalnether.common;

import fuzs.eternalnether.common.init.*;
import fuzs.eternalnether.common.world.entity.animal.horse.WitherSkeletonHorse;
import fuzs.eternalnether.common.world.entity.monster.WarpedEnderman;
import fuzs.eternalnether.common.world.entity.monster.Wex;
import fuzs.eternalnether.common.world.entity.monster.piglin.AgeablePiglin;
import fuzs.eternalnether.common.world.entity.monster.piglin.PiglinPrisoner;
import fuzs.eternalnether.common.world.entity.monster.skeleton.Corpor;
import fuzs.eternalnether.common.world.entity.monster.skeleton.WitherSkeletonKnight;
import fuzs.eternalnether.common.world.entity.monster.skeleton.Wraither;
import fuzs.eternalnether.common.world.entity.projectile.ThrownWarpedEnderpearl;
import fuzs.eternalnether.common.world.item.WitheredBoneMealItem;
import fuzs.puzzleslib.common.api.biome.v2.BiomeLoadingPhase;
import fuzs.puzzleslib.common.api.biome.v2.BiomeTransformer;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import fuzs.puzzleslib.common.api.core.v1.context.BiomeTransformationsContext;
import fuzs.puzzleslib.common.api.core.v1.context.EntityAttributesContext;
import fuzs.puzzleslib.common.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.common.api.event.v1.entity.EnderPearlTeleportCallback;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.dispenser.BlockSource;
import net.minecraft.core.dispenser.OptionalDispenseItemBehavior;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.equine.SkeletonHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.LevelEvent;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.Heightmap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EternalNether implements ModConstructor {
    public static final String MOD_ID = "eternalnether";
    public static final String MOD_NAME = "Eternal Nether";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_NAME);

    @Override
    public void onConstructMod() {
        ModRegistry.boostrap();
        registerEventHandlers();
    }

    private static void registerEventHandlers() {
        EnderPearlTeleportCallback.EVENT.register(ThrownWarpedEnderpearl::onEnderPearlTeleport);
    }

    @Override
    public void onCommonSetup() {
        ModFeatures.setBasaltFeatureRestrictions();
        ModEntityTypes.setPiglinBruteSensorsAndMemories();
        DispenserBlock.registerBehavior(ModItems.WITHERED_BONE_MEAL.value(), new OptionalDispenseItemBehavior() {
            @Override
            protected ItemStack execute(BlockSource source, ItemStack dispensed) {
                this.setSuccess(true);
                Level level = source.level();
                BlockPos target = source.pos().relative(source.state().getValue(DispenserBlock.FACING));
                if (!WitheredBoneMealItem.growCrop(dispensed, level, target) && !WitheredBoneMealItem.growWaterPlant(
                        dispensed,
                        level,
                        target,
                        null)) {
                    this.setSuccess(false);
                } else if (!level.isClientSide()) {
                    level.levelEvent(LevelEvent.PARTICLES_AND_SOUND_PLANT_GROWTH, target, 15);
                }

                return dispensed;
            }
        });
    }

    @Override
    public void onRegisterBiomeTransformations(BiomeTransformationsContext context) {
        context.registerBiomeTransformation(BiomeLoadingPhase.ADD,
                (HolderGetter.Provider lookupProvider, Holder<Biome> biome) -> {
                    return biome.is(Biomes.SOUL_SAND_VALLEY);
                },
                (HolderGetter.Provider lookupProvider, Holder<Biome> biome, BiomeTransformer.Context transformation) -> {
                    transformation.generation()
                            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                    lookupProvider.getOrThrow(ModPlacedFeatures.SOUL_STONE_BLOBS));
                });
    }

    @Override
    public void onRegisterEntityAttributes(EntityAttributesContext context) {
        context.registerAttributes(ModEntityTypes.PIGLIN_PRISONER.value(), PiglinPrisoner.createAttributes());
        context.registerAttributes(ModEntityTypes.PIGLIN_HUNTER.value(), PiglinBrute.createAttributes());
        context.registerAttributes(ModEntityTypes.WEX.value(), Wex.createAttributes());
        context.registerAttributes(ModEntityTypes.WARPED_ENDERMAN.value(), WarpedEnderman.createAttributes());
        context.registerAttributes(ModEntityTypes.WRAITHER.value(), Wraither.createAttributes());
        context.registerAttributes(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                WitherSkeletonKnight.createAttributes());
        context.registerAttributes(ModEntityTypes.CORPOR.value(), Corpor.createAttributes());
        context.registerAttributes(ModEntityTypes.WITHER_SKELETON_HORSE.value(),
                WitherSkeletonHorse.createAttributes());
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_PRISONER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AgeablePiglin::checkPiglinSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_HUNTER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                AgeablePiglin::checkPiglinSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WEX.value(),
                SpawnPlacementTypes.NO_RESTRICTIONS,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WARPED_ENDERMAN.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WRAITHER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.CORPOR.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                Monster::checkMonsterSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.WITHER_SKELETON_HORSE.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                SkeletonHorse::checkSkeletonHorseSpawnRules);
    }

    public static Identifier id(String path) {
        return Identifier.fromNamespaceAndPath(MOD_ID, path);
    }
}
