package fuzs.eternalnether.common.init;

import fuzs.eternalnether.common.world.level.levelgen.structure.NetherJigsawStructure;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.random.Weighted;
import net.minecraft.util.random.WeightedList;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.entity.EntityTypes;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureSpawnOverride;
import net.minecraft.world.level.levelgen.structure.TerrainAdjustment;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.Map;
import java.util.Optional;

public final class ModStructures {
    public static final ResourceKey<Structure> CATACOMB_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "catacomb");
    public static final ResourceKey<Structure> CITADEL_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(Registries.STRUCTURE,
            "citadel");
    public static final ResourceKey<Structure> PIGLIN_MANOR_STRUCTURE = ModRegistry.REGISTRIES.makeResourceKey(
            Registries.STRUCTURE,
            "piglin_manor");

    public static void bootstrap(BootstrapContext<Structure> context) {
        context.register(CATACOMB_STRUCTURE,
                new NetherJigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.Biomes.HAS_CATACOMB)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                        WeightedList.of(new MobSpawnSettings.SpawnerData(EntityTypes.MAGMA_CUBE,
                                                UniformInt.of(1, 1))))))
                        .generationStep(GenerationStep.Decoration.UNDERGROUND_DECORATION)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.CATACOMB_START_POOL),
                        Optional.empty(),
                        3,
                        UniformHeight.of(VerticalAnchor.absolute(56), VerticalAnchor.absolute(84)),
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(128),
                        new NetherJigsawStructure.Placement(VerticalAnchor.absolute(56), 0, true)));
        context.register(CITADEL_STRUCTURE,
                new NetherJigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.Biomes.HAS_CITADEL)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                        WeightedList.of(new Weighted<>(new MobSpawnSettings.SpawnerData(EntityTypes.ENDERMAN,
                                                        UniformInt.of(1, 1)), 1),
                                                new Weighted<>(new MobSpawnSettings.SpawnerData(ModEntityTypes.WARPED_ENDERMAN.value(),
                                                        UniformInt.of(1, 1)), 1)))))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.CITADEL_START_POOL),
                        Optional.empty(),
                        4,
                        UniformHeight.of(VerticalAnchor.absolute(48), VerticalAnchor.absolute(70)),
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(116),
                        new NetherJigsawStructure.Placement(VerticalAnchor.absolute(48), 12, true)));
        context.register(PIGLIN_MANOR_STRUCTURE,
                new NetherJigsawStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModTags.Biomes.HAS_PIGLIN_MANOR)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                        WeightedList.of(new Weighted<>(new MobSpawnSettings.SpawnerData(EntityTypes.PIGLIN,
                                                        UniformInt.of(1, 1)), 2),
                                                new Weighted<>(new MobSpawnSettings.SpawnerData(ModEntityTypes.PIGLIN_HUNTER.value(),
                                                        UniformInt.of(1, 1)), 1)))))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModTemplatePools.PIGLIN_MANOR_START_POOL),
                        Optional.empty(),
                        1,
                        UniformHeight.of(VerticalAnchor.absolute(34), VerticalAnchor.absolute(72)),
                        Optional.empty(),
                        new JigsawStructure.MaxDistance(116),
                        new NetherJigsawStructure.Placement(VerticalAnchor.absolute(34), 24, true)));
    }
}
