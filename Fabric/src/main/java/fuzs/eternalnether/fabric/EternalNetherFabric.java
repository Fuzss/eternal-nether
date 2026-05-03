package fuzs.eternalnether.fabric;

import fuzs.eternalnether.common.EternalNether;
import fuzs.puzzleslib.common.api.core.v1.ModConstructor;
import net.fabricmc.api.ModInitializer;

public class EternalNetherFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
    }
}
