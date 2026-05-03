package fuzs.eternalnether.common.client.renderer.entity;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.client.model.geom.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.EquineRenderState;
import net.minecraft.resources.Identifier;

public class WitherSkeletonHorseRenderer extends LegacyUndeadHorseRenderer {
    private static final Identifier TEXTURE_LOCATION = EternalNether.id(
            "textures/entity/horse/wither_skeleton_horse.png");

    public WitherSkeletonHorseRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModModelLayers.WITHER_SKELETON_HORSE,
                ModModelLayers.WITHER_SKELETON_HORSE_BABY,
                ModModelLayers.WITHER_SKELETON_HORSE_SADDLE);
    }

    @Override
    public Identifier getTextureLocation(EquineRenderState state) {
        return TEXTURE_LOCATION;
    }
}
