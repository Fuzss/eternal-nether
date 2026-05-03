package fuzs.eternalnether.neoforge.client;

import fuzs.eternalnether.common.EternalNether;
import fuzs.eternalnether.common.client.EternalNetherClient;
import fuzs.eternalnether.common.data.client.ModLanguageProvider;
import fuzs.eternalnether.common.data.client.ModModelProvider;
import fuzs.eternalnether.common.data.client.ModAtlasProvider;
import fuzs.eternalnether.neoforge.data.client.ModSoundProvider;
import fuzs.puzzleslib.common.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;

@Mod(value = EternalNether.MOD_ID, dist = Dist.CLIENT)
public class EternalNetherNeoForgeClient {

    public EternalNetherNeoForgeClient() {
        ClientModConstructor.construct(EternalNether.MOD_ID, EternalNetherClient::new);
        DataProviderHelper.registerDataProviders(EternalNether.MOD_ID,
                ModLanguageProvider::new,
                ModModelProvider::new,
                ModAtlasProvider::new,
                ModSoundProvider::new);
    }
}
