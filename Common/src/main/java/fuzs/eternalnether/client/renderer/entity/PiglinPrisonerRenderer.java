package fuzs.eternalnether.client.renderer.entity;

import fuzs.eternalnether.EternalNether;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.PiglinRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;

public class PiglinPrisonerRenderer extends PiglinRenderer {
    private static final ResourceLocation TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/piglin/piglin_prisoner.png");

    public PiglinPrisonerRenderer(EntityRendererProvider.Context context) {
        super(context, ModelLayers.PIGLIN, ModelLayers.PIGLIN_INNER_ARMOR, ModelLayers.PIGLIN_OUTER_ARMOR, false);
    }

    @Override
    public ResourceLocation getTextureLocation(Mob mob) {
        return TEXTURE_LOCATION;
    }
}
