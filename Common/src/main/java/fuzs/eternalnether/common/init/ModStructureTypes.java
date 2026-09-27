package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.world.level.levelgen.structure.CatacombStructure;
import fuzs.eternalnether.common.world.level.levelgen.structure.CitadelStructure;
import fuzs.eternalnether.common.world.level.levelgen.structure.PiglinManorStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class ModStructureTypes {
    public static final Holder.Reference<StructureType<PiglinManorStructure>> PIGLIN_MANOR_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "piglin_manor",
            () -> () -> PiglinManorStructure.CODEC);
    public static final Holder.Reference<StructureType<CitadelStructure>> CITADEL_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "citadel",
            () -> () -> CitadelStructure.CODEC);
    public static final Holder.Reference<StructureType<CatacombStructure>> CATACOMB_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "catacomb",
            () -> () -> CatacombStructure.CODEC);

    public static void boostrap() {
        // NO-OP
    }
}
