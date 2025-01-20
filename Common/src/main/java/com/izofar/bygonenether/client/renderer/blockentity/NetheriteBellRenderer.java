package com.izofar.bygonenether.client.renderer.blockentity;

import com.izofar.bygonenether.world.level.block.entity.NetheriteBellBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.eternalnether.EternalNether;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class NetheriteBellRenderer implements BlockEntityRenderer<NetheriteBellBlockEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/netherite_bell/netherite_bell_body.png");

    private final ModelPart bellBody;

    public NetheriteBellRenderer(BlockEntityRendererProvider.Context context) {
        this.bellBody = context.bakeLayer(ModelLayers.BELL).getChild("bell_body");
    }

    @Override
    public void render(NetheriteBellBlockEntity blockEntity, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        float ageInTicks = (float) blockEntity.ticks + partialTicks;
        float xRot = 0.0F;
        float zRot = 0.0F;
        if (blockEntity.shaking) {
            float swingAmount = Mth.sin(ageInTicks / Mth.PI) / (4.0F + ageInTicks / 3.0F);
            if (blockEntity.clickDirection == Direction.NORTH) {
                xRot = -swingAmount;
            } else if (blockEntity.clickDirection == Direction.SOUTH) {
                xRot = swingAmount;
            } else if (blockEntity.clickDirection == Direction.EAST) {
                zRot = -swingAmount;
            } else if (blockEntity.clickDirection == Direction.WEST) {
                zRot = swingAmount;
            }
        }
        this.bellBody.xRot = xRot;
        this.bellBody.zRot = zRot;
        VertexConsumer vertexconsumer = buffer.getBuffer(RenderType.entitySolid(TEXTURE_LOCATION));
        this.bellBody.render(poseStack, vertexconsumer, packedLight, packedOverlay);
    }
}
