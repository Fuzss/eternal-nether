package com.izofar.bygonenether.init;

import fuzs.eternalnether.EternalNether;
import fuzs.puzzleslib.api.init.v3.registry.RegistryManager;
import fuzs.puzzleslib.api.init.v3.tags.TagFactory;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.structure.Structure;

public class ModRegistry {
    static final RegistryManager REGISTRIES = RegistryManager.from(EternalNether.MOD_ID);
    static final TagFactory TAGS = TagFactory.make(EternalNether.MOD_ID);
    public static final TagKey<Block> WITHERED_BLOCK_TAG_KEY = TAGS.registerBlockTag("withered");
    public static final TagKey<Structure> NO_BASALT_STRUCTURE_TAG_KEY = TAGS.registerTagKey(Registries.STRUCTURE,
            "no_basalt");

    public static void boostrap() {
        ModSoundEvents.boostrap();
    }
}
