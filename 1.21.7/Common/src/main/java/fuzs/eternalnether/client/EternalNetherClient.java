package fuzs.eternalnether.client;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.WexModel;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.eternalnether.client.renderer.entity.*;
import fuzs.eternalnether.client.renderer.special.GildedNetheriteShieldSpecialRenderer;
import fuzs.eternalnether.init.ModBlocks;
import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelsContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshTransformer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;

public class EternalNetherClient implements ClientModConstructor {

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModEntityTypes.PIGLIN_PRISONER.value(), PiglinPrisonerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.PIGLIN_HUNTER.value(), PiglinHunterRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WEX.value(), WexRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WARPED_ENDERMAN.value(), WarpedEndermanRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WRAITHER.value(), WraitherRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WITHER_SKELETON_KNIGHT.value(),
                WitherSkeletonKnightRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.CORPOR.value(), CorporRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WITHER_SKELETON_HORSE.value(), WitherSkeletonHorseRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WARPED_ENDER_PEARL.value(), ThrownItemRenderer::new);
    }

    @Override
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModBlocks.NETHERITE_BELL_BLOCK_ENTITY_TYPE.value(),
                NetheriteBellRenderer::new);
    }

    @Override
    public void onRegisterItemModels(ItemModelsContext context) {
        context.registerSpecialModelRenderer(EternalNether.id("gilded_netherite_shield"),
                GildedNetheriteShieldSpecialRenderer.Unbaked.MAP_CODEC);
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        LayerDefinition innerArmorLayerDefinition = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                LayerDefinitions.INNER_ARMOR_DEFORMATION), 64, 32);
        LayerDefinition outerArmorLayerDefinition = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(
                LayerDefinitions.OUTER_ARMOR_DEFORMATION), 64, 32);
        LayerDefinition largeOuterArmorLayerDefinition = LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(
                1.02F)), 64, 32);
        LayerDefinition piglinLayerDefinition = LayerDefinition.create(PiglinModel.createMesh(CubeDeformation.NONE),
                64,
                64);
        context.registerLayerDefinition(ModModelLayers.NETHERITE_BELL, BellModel::createBodyLayer);
        context.registerLayerDefinition(ModModelLayers.GILDED_NETHERITE_SHIELD, ShieldModel::createLayer);
        context.registerLayerDefinition(ModModelLayers.WEX,
                () -> WexModel.createBodyLayer().apply(MeshTransformer.scaling(0.4F)));
        context.registerLayerDefinition(ModModelLayers.WARPED_ENDERMAN, WarpedEndermanRenderer::createBodyLayer);
        context.registerLayerDefinition(ModModelLayers.CORPOR,
                () -> CorporRenderer.createBodyLayer().apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.CORPOR_INNER_ARMOR,
                () -> innerArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.CORPOR_OUTER_ARMOR,
                () -> largeOuterArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WRAITHER,
                () -> SkeletonModel.createBodyLayer().apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WRAITHER_INNER_ARMOR,
                () -> innerArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WRAITHER_OUTER_ARMOR,
                () -> outerArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_KNIGHT,
                () -> SkeletonModel.createBodyLayer().apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_KNIGHT_OUTER_LAYER,
                () -> LayerDefinition.create(HumanoidModel.createMesh(new CubeDeformation(0.25F), 0.0F), 64, 32)
                        .apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_KNIGHT_INNER_ARMOR,
                () -> innerArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_KNIGHT_OUTER_ARMOR,
                () -> outerArmorLayerDefinition.apply(MeshTransformer.scaling(1.2F)));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER, () -> piglinLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER_INNER_ARMOR, () -> innerArmorLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER_OUTER_ARMOR,
                () -> largeOuterArmorLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER_BABY,
                () -> piglinLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER_BABY_INNER_ARMOR,
                () -> innerArmorLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_PRISONER_BABY_OUTER_ARMOR,
                () -> largeOuterArmorLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER, () -> piglinLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER_INNER_ARMOR, () -> innerArmorLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER_OUTER_ARMOR, () -> largeOuterArmorLayerDefinition);
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER_BABY,
                () -> piglinLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER_BABY_INNER_ARMOR,
                () -> innerArmorLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.PIGLIN_HUNTER_BABY_OUTER_ARMOR,
                () -> largeOuterArmorLayerDefinition.apply(HumanoidModel.BABY_TRANSFORMER));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_HORSE,
                () -> LayerDefinition.create(AbstractEquineModel.createBodyMesh(CubeDeformation.NONE), 64, 64));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_HORSE_BABY,
                () -> LayerDefinition.create(AbstractEquineModel.createBabyMesh(CubeDeformation.NONE), 64, 64));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_HORSE_SADDLE,
                () -> EquineSaddleModel.createSaddleLayer(false));
        context.registerLayerDefinition(ModModelLayers.WITHER_SKELETON_HORSE_BABY_SADDLE,
                () -> EquineSaddleModel.createSaddleLayer(true));
    }
}
