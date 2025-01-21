package fuzs.eternalnether.client.renderer.entity;

import fuzs.eternalnether.EternalNether;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.UndeadHorseRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class WitherSkeletonHorseRenderer extends UndeadHorseRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/wither/wither_skeleton_horse.png");

    public WitherSkeletonHorseRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.SKELETON_HORSE);
    }

    @Override
    public ResourceLocation getTextureLocation(AbstractHorse horse) {
        return TEXTURE_LOCATION;
    }
}
