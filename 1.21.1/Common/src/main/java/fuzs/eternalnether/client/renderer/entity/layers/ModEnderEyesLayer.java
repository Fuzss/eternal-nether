package fuzs.eternalnether.client.renderer.entity.layers;

import fuzs.eternalnether.EternalNether;
import net.minecraft.client.model.EndermanModel;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.EyesLayer;
import net.minecraft.world.entity.monster.EnderMan;

public class ModEnderEyesLayer<T extends EnderMan, M extends EndermanModel<T>> extends EyesLayer<T, M> {
    private static final RenderType RENDER_TYPE = RenderType.eyes(EternalNether.id(
            "textures/entity/enderman/warped_enderman_eyes.png"));

    public ModEnderEyesLayer(RenderLayerParent<T, M> layer) {
        super(layer);
    }

    @Override
    public RenderType renderType() {
        return RENDER_TYPE;
    }
}
