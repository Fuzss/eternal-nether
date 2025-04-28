package fuzs.eternalnether.neoforge;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.data.ModAdvancementProvider;
import fuzs.eternalnether.data.ModRecipeProvider;
import fuzs.eternalnether.data.loot.ModBlockLootProvider;
import fuzs.eternalnether.data.loot.ModChestLootProvider;
import fuzs.eternalnether.data.loot.ModEntityTypeLootProvider;
import fuzs.eternalnether.data.registries.ModDatapackRegistriesProvider;
import fuzs.eternalnether.data.tags.ModBiomeTagProvider;
import fuzs.eternalnether.data.tags.ModBlockTagProvider;
import fuzs.eternalnether.data.tags.ModEntityTypeTagProvider;
import fuzs.eternalnether.data.tags.ModItemTagProvider;
import fuzs.eternalnether.world.entity.projectile.EnderPearlTeleportCallback;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.api.event.v1.core.EventResult;
import fuzs.puzzleslib.api.event.v1.data.MutableFloat;
import fuzs.puzzleslib.neoforge.api.data.v2.core.DataProviderHelper;
import fuzs.puzzleslib.neoforge.api.event.v1.core.NeoForgeEventInvokerRegistry;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.entity.EntityTeleportEvent;

@Mod(EternalNether.MOD_ID)
public class EternalNetherNeoForge {

    public EternalNetherNeoForge() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
        DataProviderHelper.registerDataProviders(EternalNether.MOD_ID,
                ModDatapackRegistriesProvider::new,
                ModBlockTagProvider::new,
                ModItemTagProvider::new,
                ModEntityTypeTagProvider::new,
                ModBiomeTagProvider::new,
                ModBlockLootProvider::new,
                ModEntityTypeLootProvider::new,
                ModChestLootProvider::new,
                ModRecipeProvider::new,
                ModAdvancementProvider::new);
        registerEventHandlers();
    }

    @Deprecated(forRemoval = true)
    private static void registerEventHandlers() {
        NeoForgeEventInvokerRegistry.INSTANCE.register(EnderPearlTeleportCallback.class,
                EntityTeleportEvent.EnderPearl.class,
                (EnderPearlTeleportCallback callback, EntityTeleportEvent.EnderPearl evt) -> {
                    EventResult result = callback.onEnderPearlTeleport(evt.getPlayer(),
                            evt.getTarget(),
                            evt.getPearlEntity(),
                            MutableFloat.fromEvent(evt::setAttackDamage, evt::getAttackDamage),
                            evt.getHitResult());
                    if (result.isInterrupt()) evt.setCanceled(true);
                });
    }
}
