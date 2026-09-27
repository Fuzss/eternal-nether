package fuzs.eternalnether.common.init;

import fuzs.puzzleslib.common.api.init.v3.registry.ContentRegistrationHelper;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

public final class ModJukeboxSongs {
    public static final ResourceKey<JukeboxSong> WITHER_WALTZ_JUKEBOX_SONG = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.JUKEBOX_SONG,
            "wither_waltz");

    public static void bootstrap(BootstrapContext<JukeboxSong> context) {
        ContentRegistrationHelper.registerJukeboxSong(context,
                WITHER_WALTZ_JUKEBOX_SONG,
                ModSoundEvents.WITHER_WALTZ,
                5040.0F,
                4);
    }
}
