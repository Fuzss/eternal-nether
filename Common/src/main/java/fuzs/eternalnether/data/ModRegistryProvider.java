package fuzs.eternalnether.data;

import com.izofar.bygonenether.init.ModItems;
import com.izofar.bygonenether.init.ModSoundEvents;
import fuzs.puzzleslib.api.data.v2.AbstractRegistriesDatapackGenerator;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.JukeboxSong;

public class ModRegistryProvider extends AbstractRegistriesDatapackGenerator<JukeboxSong> {

    public ModRegistryProvider(DataProviderContext context) {
        super(Registries.JUKEBOX_SONG, context);
    }

    @Override
    public void addBootstrap(BootstrapContext<JukeboxSong> context) {
        context.register(ModItems.WITHER_WALTZ_JUKEBOX_SONG,
                new JukeboxSong(ModSoundEvents.WITHER_WALTZ,
                        getComponent(ModItems.WITHER_WALTZ_JUKEBOX_SONG),
                        5040.0F,
                        4));
    }

    public static MutableComponent getComponent(ResourceKey<?> resourceKey) {
        return Component.translatable(getTranslationKey(resourceKey));
    }

    public static String getTranslationKey(ResourceKey<?> resourceKey) {
        return Util.makeDescriptionId(resourceKey.registry().getPath(), resourceKey.location());
    }

    public static MutableComponent getComponent(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation resourceLocation) {
        return Component.translatable(getTranslationKey(registryKey, resourceLocation));
    }

    public static String getTranslationKey(ResourceKey<? extends Registry<?>> registryKey, ResourceLocation resourceLocation) {
        return Util.makeDescriptionId(Registries.elementsDirPath(registryKey), resourceLocation);
    }
}
