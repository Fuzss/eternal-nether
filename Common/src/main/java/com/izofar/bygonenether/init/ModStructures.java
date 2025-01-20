package com.izofar.bygonenether.init;

import com.izofar.bygonenether.world.level.levelgen.structure.CatacombStructure;
import com.izofar.bygonenether.world.level.levelgen.structure.CitadelStructure;
import com.izofar.bygonenether.world.level.levelgen.structure.PiglinManorStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public class ModStructures {
    public static final Holder.Reference<StructureType<CatacombStructure>> CATACOMB_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "catacomb",
            () -> () -> CatacombStructure.CODEC);
    public static final Holder.Reference<StructureType<CitadelStructure>> CITADEL_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "citadel",
            () -> () -> CitadelStructure.CODEC);
    public static final Holder.Reference<StructureType<PiglinManorStructure>> PIGLIN_MANOR_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "piglin_manor",
            () -> () -> PiglinManorStructure.CODEC);

    public static void boostrap() {
        // NO-OP
    }
}
