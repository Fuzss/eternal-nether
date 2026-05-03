package fuzs.eternalnether.fabric;

import fuzs.eternalnether.EternalNether;
import fuzs.eternalnether.world.entity.projectile.EnderPearlTeleportCallback;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import fuzs.puzzleslib.fabric.api.event.v1.core.FabricEventFactory;
import fuzs.puzzleslib.fabric.api.event.v1.core.FabricEventInvokerRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.Event;

public class EternalNetherFabric implements ModInitializer {
    /**
     * Fires when an ender pearl lands and is about to teleport the player that threw it.
     */
    @Deprecated(forRemoval = true)
    public static final Event<EnderPearlTeleportCallback> ENDER_PEARL_TELEPORT = FabricEventFactory.createResult(
            EnderPearlTeleportCallback.class);

    @Override
    public void onInitialize() {
        ModConstructor.construct(EternalNether.MOD_ID, EternalNether::new);
        registerEventHandlers();
    }

    @Deprecated(forRemoval = true)
    private static void registerEventHandlers() {
        FabricEventInvokerRegistry.INSTANCE.register(EnderPearlTeleportCallback.class, ENDER_PEARL_TELEPORT);
    }
}
