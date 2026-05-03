package fuzs.eternalnether.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.WexModel;
import fuzs.eternalnether.world.entity.monster.Wex;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;

public class WexRenderer extends MobRenderer<Wex, WexModel> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id("textures/entity/skeleton/wex.png");
    private static final ResourceLocation CHARGING_TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/skeleton/wex_charging.png");

    public WexRenderer(Context context) {
        super(context, new WexModel(WexModel.createBodyLayer().bakeRoot()), 0.3F);
    }

    @Override
    protected int getBlockLightLevel(Wex wex, BlockPos blockPos) {
        return 15;
    }

    @Override
    public ResourceLocation getTextureLocation(Wex wex) {
        return wex.isCharging() ? CHARGING_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }

    @Override
    protected void scale(Wex wex, PoseStack poseStack, float partialTicks) {
        poseStack.scale(0.4F, 0.4F, 0.4F);
    }
}
