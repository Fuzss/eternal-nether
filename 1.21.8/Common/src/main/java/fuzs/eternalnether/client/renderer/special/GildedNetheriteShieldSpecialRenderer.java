package fuzs.eternalnether.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.serialization.MapCodec;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import net.minecraft.client.model.ShieldModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class GildedNetheriteShieldSpecialRenderer extends ShieldSpecialRenderer {
    public static final Material SHIELD_BASE = new Material(Sheets.SHIELD_SHEET,
            EternalNether.id("entity/gilded_netherite_shield_base"));
    public static final Material NO_PATTERN_SHIELD = new Material(Sheets.SHIELD_SHEET,
            EternalNether.id("entity/gilded_netherite_shield_base_nopattern"));

    private final ShieldModel model;

    public GildedNetheriteShieldSpecialRenderer(ShieldModel model) {
        super(model);
        this.model = model;
    }

    @Override
    public void render(@Nullable DataComponentMap patterns, ItemDisplayContext displayContext, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay, boolean hasFoilType) {
        BannerPatternLayers bannerPatternLayers =
                patterns != null ? patterns.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) :
                        BannerPatternLayers.EMPTY;
        DyeColor dyeColor = patterns != null ? patterns.get(DataComponents.BASE_COLOR) : null;
        boolean bl = !bannerPatternLayers.layers().isEmpty() || dyeColor != null;
        poseStack.pushPose();
        poseStack.scale(1.0F, -1.0F, -1.0F);
        // copied from super method with different materials
        Material material = bl ? SHIELD_BASE : NO_PATTERN_SHIELD;
        VertexConsumer vertexConsumer = material.sprite()
                .wrap(ItemRenderer.getFoilBuffer(bufferSource,
                        this.model.renderType(material.atlasLocation()),
                        displayContext == ItemDisplayContext.GUI,
                        hasFoilType));
        this.model.handle().render(poseStack, vertexConsumer, packedLight, packedOverlay);
        if (bl) {
            BannerRenderer.renderPatterns(poseStack,
                    bufferSource,
                    packedLight,
                    packedOverlay,
                    this.model.plate(),
                    material,
                    false,
                    (DyeColor) Objects.requireNonNullElse(dyeColor, DyeColor.WHITE),
                    bannerPatternLayers,
                    hasFoilType,
                    false);
        } else {
            this.model.plate().render(poseStack, vertexConsumer, packedLight, packedOverlay);
        }

        poseStack.popPose();
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked {
        public static final GildedNetheriteShieldSpecialRenderer.Unbaked INSTANCE = new GildedNetheriteShieldSpecialRenderer.Unbaked();
        public static final MapCodec<GildedNetheriteShieldSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<GildedNetheriteShieldSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public SpecialModelRenderer<?> bake(EntityModelSet modelSet) {
            return new GildedNetheriteShieldSpecialRenderer(new ShieldModel(modelSet.bakeLayer(ModModelLayers.GILDED_NETHERITE_SHIELD)));
        }
    }
}
