package fuzs.eternalnether.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.WitherSkeletonKnightModel;
import fuzs.eternalnether.world.entity.monster.WitherSkeletonKnight;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WitherSkeletonKnightRenderer extends HumanoidMobRenderer<WitherSkeletonKnight, WitherSkeletonKnightModel> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/skeleton/wither_skeleton_knight.png");
    private static final ResourceLocation DISARMORED_TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/skeleton/wither_skeleton_knight_disarmored.png");

    public WitherSkeletonKnightRenderer(EntityRendererProvider.Context context) {
        super(context, new WitherSkeletonKnightModel(WitherSkeletonKnightModel.createBodyLayer().bakeRoot()), 0.5F);
    }

    @Override
    public ResourceLocation getTextureLocation(WitherSkeletonKnight witherSkeletonKnight) {
        return witherSkeletonKnight.isDisarmored() ? DISARMORED_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }

    @Override
    protected void scale(WitherSkeletonKnight witherSkeletonKnight, PoseStack poseStack, float partialTicks) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
