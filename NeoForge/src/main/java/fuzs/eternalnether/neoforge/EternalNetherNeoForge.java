package fuzs.eternalnether.neoforge;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.data.ModRegistryProvider;
import fuzs.eternalnether.data.tags.ModBlockTagProvider;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import net.neoforged.fml.common.Mod;

@Mod(EternalNether.MOD_ID)
public class EternalNetherNeoForge {

    public EternalNetherNeoForge() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
        DataProviderHelper.registerDataProviders(EternalNether.MOD_ID, ModRegistryProvider::new, ModBlockTagProvider::new);
    }
}
