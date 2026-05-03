package fuzs.eternalnether.common.client.renderer.blockentity;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.eternalnether.common.client.model.geom.ModModelLayers;
import net.minecraft.client.model.object.bell.BellModel;
import net.minecraft.client.renderer.Sheets;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.blockentity.BellRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.state.BellRenderState;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.sprite.SpriteGetter;
import net.minecraft.client.resources.model.sprite.SpriteId;

public class NetheriteBellRenderer extends BellRenderer {
    public static final SpriteId NETHERITE_BELL_TEXTURE = Sheets.BLOCK_ENTITIES_MAPPER.defaultNamespaceApply(
            "bell/netherite_bell_body");

    private final SpriteGetter sprites;
    private final BellModel model;

    public NetheriteBellRenderer(BlockEntityRendererProvider.Context context) {
        super(context);
        this.sprites = context.sprites();
        this.model = new BellModel(context.bakeLayer(ModModelLayers.NETHERITE_BELL));
    }

    /**
     * @see BellRenderer#submit(BellRenderState, PoseStack, SubmitNodeCollector, CameraRenderState)
     */
    @Override
    public void submit(BellRenderState state, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, CameraRenderState camera) {
        // Use our custom texture.
        BellModel.State modelState = new BellModel.State(state.ticks, state.shakeDirection);
        this.model.setupAnim(modelState);
        submitNodeCollector.submitModel(this.model,
                modelState,
                poseStack,
                state.lightCoords,
                OverlayTexture.NO_OVERLAY,
                -1,
                NETHERITE_BELL_TEXTURE,
                this.sprites,
                0,
                state.breakProgress);
    }
}
