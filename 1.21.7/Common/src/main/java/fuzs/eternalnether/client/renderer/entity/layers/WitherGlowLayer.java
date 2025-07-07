package fuzs.eternalnether.client.renderer.entity.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.world.entity.monster.Wraither;
import net.minecraft.client.model.SkeletonModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.monster.AbstractSkeleton;

public class WitherGlowLayer<T extends AbstractSkeleton, M extends SkeletonModel<T>> extends EyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(EternalNether.id(
            "textures/entity/skeleton/wraither_eyes.png"));

    public WitherGlowLayer(RenderLayerParent<T, M> layer) {
        super(layer);
    }

    @Override
    public void render(PoseStack poseStack, MultiBufferSource buffer, int packedLight, T livingEntity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if (livingEntity instanceof Wraither wraither && wraither.isPossessed()) {
            super.render(poseStack,
                    buffer,
                    packedLight,
                    livingEntity,
                    limbSwing,
                    limbSwingAmount,
                    partialTicks,
                    ageInTicks,
                    netHeadYaw,
                    headPitch);
        }
    }

    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}
