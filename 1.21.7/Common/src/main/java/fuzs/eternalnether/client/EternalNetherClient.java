package fuzs.eternalnether.client;

import fuzs.eternalnether.client.model.geom.ModModelLayers;
import fuzs.eternalnether.client.renderer.ShieldItemRenderer;
import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.eternalnether.client.renderer.entity.*;
import fuzs.eternalnether.init.ModBlocks;
import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.eternalnether.init.ModItems;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import net.minecraft.client.model.BellModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class EternalNetherClient implements ClientModConstructor {

    @Override
    public void onRegisterEntityRenderers(EntityRenderersContext context) {
        context.registerEntityRenderer(ModEntityTypes.PIGLIN_PRISONER.value(), PiglinPrisonerRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.PIGLIN_HUNTER.value(), PiglinHunterRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WEX.value(), WexRenderer::new);
        context.registerEntityRenderer(ModEntityTypes.WARPED_ENDERMAN.value(), WarpedEnderManRenderer::new);
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
    public void onRegisterBuiltinModelItemRenderers(BuiltinModelItemRendererContext context) {
        context.registerItemRenderer(new ShieldItemRenderer(), ModItems.GILDED_NETHERITE_SHIELD.value());
    }

    @Override
    public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
        context.registerItemProperty(ShieldItemRenderer.BLOCKING_ITEM_MODEL_PROPERTY,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int seed) -> {
                    return livingEntity != null && livingEntity.isUsingItem()
                            && livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
                },
                ModItems.GILDED_NETHERITE_SHIELD.value());
    }

    @Override
    public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
        context.registerLayerDefinition(ModModelLayers.NETHERITE_BELL, BellModel::createBodyLayer);
    }
}
