package com.izofar.bygonenether.client.renderer.entity;

import com.izofar.bygonenether.client.renderer.entity.layers.WitherGlowLayer;
import com.izofar.bygonenether.world.entity.monster.Wraither;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;

public class WraitherRenderer extends MobRenderer<Wraither, SkeletonModel<Wraither>> {
    private static final ResourceLocation TEXTURE_LOCATION = ResourceLocationHelper.withDefaultNamespace(
            "textures/entity/skeleton/wither_skeleton.png");
    private static final ResourceLocation POSSESSED_TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/wither/wraither.png");

    public WraitherRenderer(EntityRendererProvider.Context context) {
        super(context, new SkeletonModel<>(SkeletonModel.createBodyLayer().bakeRoot()), 0.5F);
        this.addLayer(new WitherGlowLayer<>(this));
    }

    @Override
    public ResourceLocation getTextureLocation(Wraither wraither) {
        return wraither.isPossessed() ? POSSESSED_TEXTURE_LOCATION : TEXTURE_LOCATION;
    }

    @Override
    protected void scale(Wraither wraither, PoseStack poseStack, float partialTicks) {
        poseStack.scale(1.2F, 1.2F, 1.2F);
    }
}
