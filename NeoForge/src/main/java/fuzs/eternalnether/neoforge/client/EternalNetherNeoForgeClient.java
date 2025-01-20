package fuzs.eternalnether.neoforge.client;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.client.EternalNetherClient;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = EternalNether.MOD_ID, dist = Dist.CLIENT)
public class EternalNetherNeoForgeClient {

    public EternalNetherNeoForgeClient() {
        ClientModConstructor.construct(EternalNether.MOD_ID, EternalNetherClient::new);
    }
}
