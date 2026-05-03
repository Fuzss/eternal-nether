package fuzs.eternalnether.fabric.client;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.client.EternalNetherClient;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import net.fabricmc.api.ClientModInitializer;

public class EternalNetherFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ClientModConstructor.construct(EternalNether.MOD_ID, EternalNetherClient::new);
    }
}
