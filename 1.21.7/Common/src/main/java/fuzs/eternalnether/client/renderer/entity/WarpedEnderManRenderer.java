package fuzs.eternalnether.client.renderer.entity;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.WarpedEndermanModel;
import fuzs.eternalnether.client.renderer.entity.layers.ModCarriedBlockLayer;
import fuzs.eternalnether.client.renderer.entity.layers.ModEnderEyesLayer;
import fuzs.eternalnether.world.entity.monster.WarpedEnderMan;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class WarpedEnderManRenderer extends MobRenderer<WarpedEnderMan, WarpedEndermanModel> {
    private static final Map<WarpedEnderMan.WarpedEnderManVariant, ResourceLocation> TEXTURE_LOCATIONS = Maps.immutableEnumMap(
            ImmutableMap.of(WarpedEnderMan.WarpedEnderManVariant.FRESH,
                    EternalNether.id("textures/entity/enderman/warped_enderman_fresh.png"),
                    WarpedEnderMan.WarpedEnderManVariant.SHORT_VINE,
                    EternalNether.id("textures/entity/enderman/warped_enderman_short_vine.png"),
                    WarpedEnderMan.WarpedEnderManVariant.LONG_VINE,
                    EternalNether.id("textures/entity/enderman/warped_enderman_long_vine.png")));

    private final RandomSource random = RandomSource.create();

    public WarpedEnderManRenderer(Context context) {
        super(context, new WarpedEndermanModel(WarpedEndermanModel.createBodyLayer().bakeRoot()), 0.5F);
        this.addLayer(new ModEnderEyesLayer<>(this));
        this.addLayer(new ModCarriedBlockLayer<>(this, context.getBlockRenderDispatcher()));
    }

    @Override
    public void render(WarpedEnderMan warpedEnderMan, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        BlockState blockstate = warpedEnderMan.getCarriedBlock();
        WarpedEndermanModel enderManModel = this.getModel();
        enderManModel.carrying = blockstate != null;
        enderManModel.creepy = warpedEnderMan.isCreepy();
        super.render(warpedEnderMan, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public Vec3 getRenderOffset(WarpedEnderMan warpedEnderMan, float offset) {
        return warpedEnderMan.isCreepy() ?
                new Vec3(this.random.nextGaussian() * 0.02D, 0.0D, this.random.nextGaussian() * 0.02D) :
                super.getRenderOffset(warpedEnderMan, offset);
    }

    @Override
    public ResourceLocation getTextureLocation(WarpedEnderMan warpedEnderMan) {
        return TEXTURE_LOCATIONS.get(warpedEnderMan.getVariant());
    }
}
