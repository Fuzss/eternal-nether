package com.izofar.bygonenether.init;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.player.Player;

import java.util.Optional;

public abstract class ModMemoryModuleTypes {
    public static final Holder.Reference<MemoryModuleType<Player>> NEAREST_TARGETABLE_PLAYER_NOT_WEARING_GILD_MEMORY_MODULE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.MEMORY_MODULE_TYPE,
            "nearest_targetable_player_not_wearing_gild",
            () -> new MemoryModuleType<>(Optional.empty()));

    public static void boostrap() {
        // NO-OP
    }
}
