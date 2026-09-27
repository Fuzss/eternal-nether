package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.world.level.levelgen.structure.NetherJigsawStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.StructureType;

public final class ModStructureTypes {
    public static final Holder.Reference<StructureType<NetherJigsawStructure>> NETHER_JIGSAW_STRUCTURE_TYPE = ModRegistry.REGISTRIES.register(
            Registries.STRUCTURE_TYPE,
            "nether_jigsaw",
            () -> () -> NetherJigsawStructure.CODEC);

    public static void boostrap() {
        // NO-OP
    }
}
