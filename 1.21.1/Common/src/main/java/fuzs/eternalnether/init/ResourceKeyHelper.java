package fuzs.eternalnether.init;

import net.minecraft.Util;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

@Deprecated(forRemoval = true)
public final class ResourceKeyHelper {

    private ResourceKeyHelper() {
        // NO-OP
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
