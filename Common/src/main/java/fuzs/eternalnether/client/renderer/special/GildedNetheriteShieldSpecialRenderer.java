package fuzs.eternalnether.client.renderer.special;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.serialization.MapCodec;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import net.minecraft.client.model.object.equipment.ShieldModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BannerRenderer;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.util.Unit;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.entity.BannerPatternLayers;
import org.jspecify.annotations.Nullable;

import java.util.Objects;

public class GildedNetheriteShieldSpecialRenderer extends ShieldSpecialRenderer {
    /**
     * @see Sheets#SHIELD_BASE
     */
    public static final SpriteId SHIELD_BASE = Sheets.SHIELD_MAPPER.apply(EternalNether.id(
            "gilded_netherite_shield_base"));
    /**
     * @see Sheets#SHIELD_BASE_NO_PATTERN
     */
    public static final SpriteId SHIELD_BASE_NO_PATTERN = Sheets.SHIELD_MAPPER.apply(EternalNether.id(
            "gilded_netherite_shield_base_nopattern"));

    private final SpriteGetter sprites;
    private final ShieldModel model;

    public GildedNetheriteShieldSpecialRenderer(SpriteGetter sprites, ShieldModel model) {
        super(sprites, model);
        this.sprites = sprites;
        this.model = model;
    }

    /**
     * @see ShieldSpecialRenderer#submit(DataComponentMap, PoseStack, SubmitNodeCollector, int, int, boolean, int)
     */
    @Override
    public void submit(@Nullable DataComponentMap components, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, boolean hasFoil, int outlineColor) {
        BannerPatternLayers patterns = components != null ?
                components.getOrDefault(DataComponents.BANNER_PATTERNS, BannerPatternLayers.EMPTY) :
                BannerPatternLayers.EMPTY;
        DyeColor baseColor = components != null ? components.get(DataComponents.BASE_COLOR) : null;
        boolean hasPatterns = !patterns.layers().isEmpty() || baseColor != null;
        SpriteId base = hasPatterns ? SHIELD_BASE : SHIELD_BASE_NO_PATTERN;
        submitNodeCollector.submitModel(this.model,
                Unit.INSTANCE,
                poseStack,
                lightCoords,
                overlayCoords,
                -1,
                base,
                this.sprites,
                outlineColor,
                null);
        if (hasPatterns) {
            BannerRenderer.submitPatterns(this.sprites,
                    poseStack,
                    submitNodeCollector,
                    lightCoords,
                    overlayCoords,
                    this.model,
                    Unit.INSTANCE,
                    false,
                    Objects.requireNonNullElse(baseColor, DyeColor.WHITE),
                    patterns,
                    null);
        }

        if (hasFoil) {
            submitNodeCollector.submitModel(this.model,
                    Unit.INSTANCE,
                    poseStack,
                    RenderTypes.entityGlint(),
                    lightCoords,
                    overlayCoords,
                    -1,
                    this.sprites.get(base),
                    0,
                    null);
        }
    }

    public record Unbaked() implements SpecialModelRenderer.Unbaked<DataComponentMap> {
        public static final GildedNetheriteShieldSpecialRenderer.Unbaked INSTANCE = new GildedNetheriteShieldSpecialRenderer.Unbaked();
        public static final MapCodec<GildedNetheriteShieldSpecialRenderer.Unbaked> MAP_CODEC = MapCodec.unit(INSTANCE);

        @Override
        public MapCodec<GildedNetheriteShieldSpecialRenderer.Unbaked> type() {
            return MAP_CODEC;
        }

        @Override
        public GildedNetheriteShieldSpecialRenderer bake(BakingContext context) {
            return new GildedNetheriteShieldSpecialRenderer(context.sprites(),
                    new ShieldModel(context.entityModelSet().bakeLayer(ModModelLayers.GILDED_NETHERITE_SHIELD)));
        }
    }
}
