package fuzs.eternalnether.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.model.geom.ModModelLayers;
import fuzs.eternalnether.world.level.block.entity.NetheriteBellBlockEntity;
import net.minecraft.client.model.BellModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.world.phys.Vec3;

public class NetheriteBellRenderer implements BlockEntityRenderer<NetheriteBellBlockEntity> {
    public static final Material NETHERITE_BELL_MATERIAL = new Material(TextureAtlas.LOCATION_BLOCKS,
            EternalNether.id("entity/bell/netherite_bell_body"));

    private final BellModel model;

    public NetheriteBellRenderer(BlockEntityRendererProvider.Context context) {
        this.model = new BellModel(context.bakeLayer(ModModelLayers.NETHERITE_BELL));
    }

    @Override
    public void render(NetheriteBellBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource buffer, int packedLight, int packedOverlay, Vec3 cameraPos) {
        VertexConsumer vertexConsumer = NETHERITE_BELL_MATERIAL.buffer(buffer, RenderType::entitySolid);
        this.model.setupAnim(blockEntity, partialTick);
        this.model.renderToBuffer(poseStack, vertexConsumer, packedLight, packedOverlay);
    }
}
