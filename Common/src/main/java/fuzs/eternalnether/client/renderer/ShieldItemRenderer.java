package fuzs.eternalnether.client.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.client.init.v1.ReloadingBuiltInItemRenderer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BannerPatternLayers;

import java.util.Objects;

public class ShieldItemRenderer implements ReloadingBuiltInItemRenderer {
    public static final Material SHIELD_BASE_MATERIAL = new Material(Sheets.SHIELD_SHEET,
            EternalNether.id("entity/gilded_netherite_shield_base"));
    public static final Material NO_PATTERN_SHIELD_MATERIAL = new Material(Sheets.SHIELD_SHEET,
            EternalNether.id("entity/gilded_netherite_shield_base_nopattern"));

    private ShieldModel shieldModel;

    /**
     * Copied from
     * {@link net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer#renderByItem(ItemStack, ItemDisplayContext,
     * PoseStack, MultiBufferSource, int, int)}, simply exchanging render materials.
     */
    @Override
    public void renderByItem(ItemStack itemStack, ItemDisplayContext itemDisplayContext, PoseStack poseStack, MultiBufferSource multiBufferSource, int packedLight, int packedOverlay) {
        BannerPatternLayers bannerPatternLayers = itemStack.getOrDefault(DataComponents.BANNER_PATTERNS,
                BannerPatternLayers.EMPTY);
        DyeColor dyeColor2 = itemStack.get(DataComponents.BASE_COLOR);
        boolean bl = !bannerPatternLayers.layers().isEmpty() || dyeColor2 != null;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        Material material = bl ? SHIELD_BASE_MATERIAL : NO_PATTERN_SHIELD_MATERIAL;
        VertexConsumer vertexConsumer = material.sprite()
                .wrap(ItemRenderer.getFoilBufferDirect(multiBufferSource,
                        this.shieldModel.renderType(material.atlasLocation()),
                        true,
                        itemStack.hasFoil()));
        this.shieldModel.handle().render(poseStack, vertexConsumer, packedLight, packedOverlay);
        if (bl) {
            BannerRenderer.renderPatterns(poseStack,
                    multiBufferSource,
                    packedLight,
                    packedOverlay,
                    this.shieldModel.plate(),
                    material,
                    false,
                    Objects.requireNonNullElse(dyeColor2, DyeColor.WHITE),
                    bannerPatternLayers,
                    itemStack.hasFoil());
        } else {
            this.shieldModel.plate().render(poseStack, vertexConsumer, packedLight, packedOverlay);
        }

        poseStack.popPose();
    }

    @Override
    public void onResourceManagerReload(ResourceManager resourceManager) {
        EntityModelSet entityModels = Minecraft.getInstance().getEntityModels();
        this.shieldModel = new ShieldModel(entityModels.bakeLayer(ModelLayers.SHIELD));
    }
}
