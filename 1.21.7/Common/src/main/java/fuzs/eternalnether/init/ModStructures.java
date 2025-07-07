package fuzs.eternalnether.init;

import fuzs.eternalnether.world.level.levelgen.structure.CatacombStructure;
import fuzs.eternalnether.world.level.levelgen.structure.CitadelStructure;
import fuzs.eternalnether.world.level.levelgen.structure.PiglinManorStructure;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSet;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

public final class ModStructures {
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
    public static final ResourceKey<Structure> CATACOMB_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "catacomb");
    public static final ResourceKey<Structure> CITADEL_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "citadel");
    public static final ResourceKey<Structure> PIGLIN_MANOR_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "piglin_manor");
    public static final ResourceKey<StructureTemplatePool> CATACOMB_START_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "catacomb/start_pool");
    public static final ResourceKey<StructureTemplatePool> CITADEL_START_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "citadel/start_pool");
    public static final ResourceKey<StructureTemplatePool> PIGLIN_MANOR_START_POOL = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.TEMPLATE_POOL,
            "piglin_manor/start_pool");
    public static final ResourceKey<StructureSet> CATACOMB_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "catacomb");
    public static final ResourceKey<StructureSet> CITADEL_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "citadel");
    public static final ResourceKey<StructureSet> PIGLIN_MANOR_STRUCTURE_SET = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE_SET,
            "piglin_manor");

    public static void boostrap() {
        // NO-OP
    }
}
