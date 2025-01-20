package com.izofar.bygonenether.client.renderer;

import com.izofar.bygonenether.init.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import fuzs.eternalnether.EternalNether;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.entity.BannerBlockEntity;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.List;

public class ModShieldRenderer extends BlockEntityWithoutLevelRenderer {
    private static final Material GILDED_NETHERITE_SHIELD_BASE_MATERIAL = new Material(InventoryMenu.BLOCK_ATLAS,
            EternalNether.id("entity/shield/gilded_netherite_shield_base"));
    private static final Material GILDED_NETHERITE_SHIELD_BASE_NOPATTERN_MATERIAL = new Material(InventoryMenu.BLOCK_ATLAS,
            EternalNether.id("entity/shield/gilded_netherite_shield_base_nopattern"));

    public ModShieldRenderer(BlockEntityRenderDispatcher dispatcher, EntityModelSet modelSet) {
        super(dispatcher, modelSet);
    }

    @SubscribeEvent
    public static void onRegisterReloadListener(RegisterClientReloadListenersEvent event) {
        instance = new ModShieldRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(),
                Minecraft.getInstance().getEntityModels());
        event.registerReloadListener(instance);
    }

    @Override
    public void renderByItem(ItemStack stack, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        boolean flag = stack.getTagElement("BlockEntityTag") != null;
        Material rendermaterial = flag ? ModelBakery.SHIELD_BASE : ModelBakery.NO_PATTERN_SHIELD;

        Item shield = stack.getItem();
        if (shield == ModItems.GILDED_NETHERITE_SHIELD.get()) {
            rendermaterial =
                    flag ? GILDED_NETHERITE_SHIELD_BASE_MATERIAL : GILDED_NETHERITE_SHIELD_BASE_NOPATTERN_MATERIAL;
        }

        VertexConsumer vertexConsumer = rendermaterial.sprite()
                .wrap(ItemRenderer.getFoilBufferDirect(buffer,
                        shieldModel.renderType(rendermaterial.atlasLocation()),
                        true,
                        stack.hasFoil()));
        this.shieldModel.handle().render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        if (flag) {
            List<Pair<Holder<BannerPattern>, DyeColor>> list = BannerBlockEntity.createPatterns(ShieldItem.getColor(
                    stack), BannerBlockEntity.getItemPatterns(stack));
            BannerRenderer.renderPatterns(poseStack,
                    buffer,
                    packedLight,
                    packedOverlay,
                    this.shieldModel.plate(),
                    rendermaterial,
                    false,
                    list,
                    stack.hasFoil());
        } else {
            this.shieldModel.plate()
                    .render(poseStack, vertexConsumer, packedLight, packedOverlay, 1.0F, 1.0F, 1.0F, 1.0F);
        }
        poseStack.popPose();
    }
}
