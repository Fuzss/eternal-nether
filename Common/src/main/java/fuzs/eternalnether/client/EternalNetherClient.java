package fuzs.eternalnether.client;

import com.izofar.bygonenether.client.renderer.blockentity.NetheriteBellRenderer;
import com.izofar.bygonenether.client.renderer.entity.*;
import com.izofar.bygonenether.init.ModEntityTypes;
import com.izofar.bygonenether.init.ModItems;
import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.BlockEntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.BuiltinModelItemRendererContext;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelPropertiesContext;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

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
    public void onRegisterBlockEntityRenderers(BlockEntityRenderersContext context) {
        context.registerBlockEntityRenderer(ModEntityTypes.NETHERITE_BELL_BLOCK_ENTITY_TYPE.value(),
                NetheriteBellRenderer::new);
    }

    @Override
    public void onRegisterBuiltinModelItemRenderers(BuiltinModelItemRendererContext context) {
        context.registerItemRenderer();
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
}
