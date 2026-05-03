package fuzs.eternalnether.client.renderer.entity;

import fuzs.eternalnether.client.model.PiglinHunterModel;
import fuzs.eternalnether.world.entity.monster.piglin.PiglinHunter;
import fuzs.eternalnether.EternalNether;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.HumanoidMobRenderer;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.resources.ResourceLocation;

public class PiglinHunterRenderer extends HumanoidMobRenderer<PiglinHunter, PiglinHunterModel> {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id("textures/entity/piglin/piglin_hunter.png");

    public PiglinHunterRenderer(EntityRendererProvider.Context context) {
        // TODO assuming this is here to fix z-fighting, instead scale the model with a cube deformation
        super(context,
                new PiglinHunterModel(PiglinHunterModel.createBodyLayer().bakeRoot()),
                0.5F,
                1.0019531F,
                1.0F,
                1.0019531F);
        this.addLayer(new HumanoidArmorLayer<>(this,
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_INNER_ARMOR)),
                new HumanoidArmorModel<>(context.bakeLayer(ModelLayers.PIGLIN_OUTER_ARMOR)),
                context.getModelManager()));
    }

    @Override
    public ResourceLocation getTextureLocation(PiglinHunter piglinHunter) {
        return TEXTURE_LOCATION;
    }

    @Override
    protected boolean isShaking(PiglinHunter piglinHunter) {
        return super.isShaking(piglinHunter) || piglinHunter.isConverting();
    }
}
