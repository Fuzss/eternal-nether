package fuzs.eternalnether.common.client.renderer.entity;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.client.model.geom.ModModelLayers;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.PiglinRenderState;
import net.minecraft.resources.Identifier;

public class PiglinPrisonerRenderer extends AbstractPiglinRenderer {
    private static final Identifier TEXTURE_LOCATION = EternalNether.id("textures/entity/piglin/piglin_prisoner.png");

    public PiglinPrisonerRenderer(EntityRendererProvider.Context context) {
        super(context,
                ModModelLayers.PIGLIN_PRISONER,
                ModModelLayers.PIGLIN_PRISONER_BABY,
                ModModelLayers.PIGLIN_PRISONER_ARMOR,
                ModModelLayers.PIGLIN_PRISONER_BABY_ARMOR);
    }

    @Override
    public Identifier getTextureLocation(PiglinRenderState state) {
        return TEXTURE_LOCATION;
    }
}
