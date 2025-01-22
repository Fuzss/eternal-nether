package fuzs.eternalnether;

import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.eternalnether.init.ModFeatures;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.init.ModRegistry;
import fuzs.eternalnether.util.CreativeModeTabHelper;
import fuzs.eternalnether.world.entity.animal.horse.WitherSkeletonHorse;
import fuzs.eternalnether.world.entity.monster.*;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisoner;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinPrisonerAi;
import fuzs.eternalnether.world.entity.projectile.EnderPearlTeleportCallback;
import fuzs.eternalnether.world.entity.projectile.ThrownWarpedEnderpearl;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingContext;
import fuzs.puzzleslib.api.biome.v1.BiomeLoadingPhase;
import fuzs.puzzleslib.api.biome.v1.BiomeModificationContext;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.core.v1.context.BiomeModificationsContext;
import fuzs.puzzleslib.api.core.v1.context.CreativeModeTabContext;
import fuzs.puzzleslib.api.core.v1.context.EntityAttributesCreateContext;
import fuzs.puzzleslib.api.core.v1.context.SpawnPlacementsContext;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.level.BlockEvents;
import fuzs.puzzleslib.api.item.v2.CreativeModeTabConfigurator;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.horse.SkeletonHorse;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.piglin.PiglinBrute;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
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
        BlockEvents.BREAK.register((ServerLevel serverLevel, BlockPos blockPos, BlockState blockState, Player player, ItemStack itemInHand) -> {
            if (blockState.is(Blocks.IRON_BARS)) {
                PiglinPrisonerAi.exciteNearbyPiglins(player, false);
            }
            return EventResult.PASS;
        });
        EnderPearlTeleportCallback.EVENT.register(ThrownWarpedEnderpearl::onEnderPearlTeleport);
    }

    @Override
    public void onCommonSetup() {
        ModFeatures.setBasaltFeatureRestrictions();
        ModEntityTypes.setPiglinBruteSensorsAndMemories();
    }

    @Override
    public void onRegisterBiomeModifications(BiomeModificationsContext context) {
        context.register(BiomeLoadingPhase.ADDITIONS,
                (BiomeLoadingContext biomeLoadingContext) -> biomeLoadingContext.is(Biomes.SOUL_SAND_VALLEY),
                (BiomeModificationContext biomeModificationContext) -> {
                    biomeModificationContext.generationSettings()
                            .addFeature(GenerationStep.Decoration.UNDERGROUND_DECORATION,
                                    ModRegistry.SOUL_STONE_BLOBS_PLACED_FEATURE);
                });
    }

    @Override
    public void onEntityAttributeCreation(EntityAttributesCreateContext context) {
        context.registerEntityAttributes(ModEntityTypes.PIGLIN_PRISONER.value(), PiglinPrisoner.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.PIGLIN_HUNTER.value(), PiglinBrute.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WEX.value(), Wex.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WARPED_ENDERMAN.value(), WarpedEnderMan.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WRAITHER.value(), Wraither.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                WitherSkeletonKnight.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.CORPOR.value(), Corpor.createAttributes());
        context.registerEntityAttributes(ModEntityTypes.WITHER_SKELETON_HORSE.value(),
                WitherSkeletonHorse.createAttributes());
    }

    @Override
    public void onRegisterSpawnPlacements(SpawnPlacementsContext context) {
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_PRISONER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PiglinPrisoner::checkPiglinSpawnRules);
        context.registerSpawnPlacement(ModEntityTypes.PIGLIN_HUNTER.value(),
                SpawnPlacementTypes.ON_GROUND,
                Heightmap.Types.MOTION_BLOCKING_NO_LEAVES,
                PiglinPrisoner::checkPiglinSpawnRules);
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

    @Override
    public void onRegisterCreativeModeTabs(CreativeModeTabContext context) {
        context.registerCreativeModeTab(CreativeModeTabConfigurator.from(MOD_ID, ModItems.WITHERED_DEBRIS)
                .displayItems(CreativeModeTabHelper.getDisplayItems(MOD_ID)));
    }

    @Override
    public ContentRegistrationFlags[] getContentRegistrationFlags() {
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.BIOME_MODIFICATIONS};
    }

    public static ResourceLocation id(String path) {
        return ResourceLocationHelper.fromNamespaceAndPath(MOD_ID, path);
    }
}
