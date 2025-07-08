package fuzs.eternalnether.client.model.geom;

import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.client.init.v1.ModelLayerFactory;
import net.minecraft.client.model.geom.ModelLayerLocation;

public class ModModelLayers {
    static final ModelLayerFactory MODEL_LAYERS = ModelLayerFactory.from(EternalNether.MOD_ID);
    public static final ModelLayerLocation NETHERITE_BELL = MODEL_LAYERS.registerModelLayer("netherite_bell");
}
