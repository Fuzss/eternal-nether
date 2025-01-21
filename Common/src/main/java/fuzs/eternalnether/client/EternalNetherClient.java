package fuzs.eternalnether.client;

import fuzs.eternalnether.client.renderer.ShieldItemRenderer;
import fuzs.eternalnether.client.renderer.blockentity.NetheriteBellRenderer;
import fuzs.eternalnether.client.renderer.entity.*;
import fuzs.eternalnether.init.ModBlocks;
import fuzs.eternalnether.init.ModEntityTypes;
import fuzs.eternalnether.init.ModItems;
import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.*;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

public class EternalNetherClient implements ClientModConstructor {
    public static final ResourceLocation BLOCKING_ITEM_MODEL_PROPERTY = EternalNether.id("blocking");

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
    public void onRegisterBlockColorProviders(ColorProvidersContext<Block, BlockColor> context) {
        ClientModConstructor.super.onRegisterBlockColorProviders(context);
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
        context.registerItemProperty(BLOCKING_ITEM_MODEL_PROPERTY,
                (ItemStack itemStack, ClientLevel clientLevel, LivingEntity livingEntity, int seed) -> {
                    return livingEntity != null && livingEntity.isUsingItem() &&
                            livingEntity.getUseItem() == itemStack ? 1.0F : 0.0F;
                },
                ModItems.GILDED_NETHERITE_SHIELD.value());
    }

    @Override
    public ContentRegistrationFlags[] getContentRegistrationFlags() {
        return new ContentRegistrationFlags[]{ContentRegistrationFlags.DYNAMIC_RENDERERS};
    }
}
