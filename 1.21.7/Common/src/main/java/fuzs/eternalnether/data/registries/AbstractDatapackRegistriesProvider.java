package fuzs.eternalnether.data.registries;

import fuzs.eternalnether.init.ResourceKeyHelper;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.core.RegistriesDataProvider;
import net.minecraft.Util;
import net.minecraft.core.*;
import net.minecraft.data.PackOutput;
import net.minecraft.data.registries.RegistriesDatapackGenerator;
import net.minecraft.data.registries.RegistryPatchGenerator;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.JukeboxSong;

import java.util.concurrent.CompletableFuture;

@Deprecated(forRemoval = true)
public abstract class AbstractDatapackRegistriesProvider extends RegistriesDatapackGenerator implements RegistriesDataProvider {
    private final CompletableFuture<HolderLookup.Provider> fullRegistries;

    public AbstractDatapackRegistriesProvider(DataProviderContext context) {
        this(context.getPackOutput(), context.getRegistries());
    }

    public AbstractDatapackRegistriesProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, CompletableFuture.completedFuture(RegistryAccess.EMPTY));
        CompletableFuture<RegistrySetBuilder.PatchedRegistries> patchedRegistries = RegistryPatchGenerator.createLookup(
                registries,
                Util.make(new RegistrySetBuilder(), (RegistrySetBuilder registrySetBuilder) -> {
                    this.addBootstrap(registrySetBuilder::add);
                }));
        this.registries = patchedRegistries.thenApply(RegistrySetBuilder.PatchedRegistries::patches);
        this.fullRegistries = patchedRegistries.thenApply(RegistrySetBuilder.PatchedRegistries::full);
    }

    public abstract void addBootstrap(RegistryBoostrapConsumer consumer);

    @Override
    public CompletableFuture<HolderLookup.Provider> getRegistries() {
        return this.fullRegistries;
    }

    public static void registerJukeboxSong(BootstrapContext<JukeboxSong> context, ResourceKey<JukeboxSong> resourceKey, Holder<SoundEvent> soundEvent, float lengthInSeconds, int comparatorOutput) {
        context.register(resourceKey,
                new JukeboxSong(soundEvent,
                        ResourceKeyHelper.getComponent(resourceKey),
                        lengthInSeconds,
                        comparatorOutput));
    }

    @FunctionalInterface
    public interface RegistryBoostrapConsumer {

        <T> void add(ResourceKey<? extends Registry<T>> key, RegistrySetBuilder.RegistryBootstrap<T> bootstrap);
    }
}
