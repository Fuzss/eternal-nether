package fuzs.eternalnether.client.renderer.entity;

import fuzs.eternalnether.client.model.CorporModel;
import fuzs.eternalnether.world.entity.monster.Corpor;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class CorporRenderer extends HumanoidMobRenderer<Corpor, CorporModel> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id("textures/entity/wither/corpor.png");

    public CorporRenderer(EntityRendererProvider.Context context) {
        super(context, new CorporModel(CorporModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(Corpor corpor) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected void scale(Corpor corpor, PoseStack poseStack, float partialTicks) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
