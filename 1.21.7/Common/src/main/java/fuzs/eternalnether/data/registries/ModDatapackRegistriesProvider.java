package fuzs.eternalnether.data.registries;

import fuzs.eternalnether.init.*;
import fuzs.eternalnether.world.level.levelgen.structure.CatacombStructure;
import fuzs.eternalnether.world.level.levelgen.structure.CitadelStructure;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Vec3i;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.JukeboxSong;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.heightproviders.UniformHeight;
import net.minecraft.world.level.levelgen.structure.*;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.JUKEBOX_SONG, ModDatapackRegistriesProvider::bootstrapJukeboxSongs);
        consumer.add(Registries.STRUCTURE, ModDatapackRegistriesProvider::bootstrapStructures);
        consumer.add(Registries.STRUCTURE_SET, ModDatapackRegistriesProvider::bootstrapStructureSets);
    }

    public static void bootstrapJukeboxSongs(BootstrapContext<JukeboxSong> context) {
        registerJukeboxSong(context, ModItems.WITHER_WALTZ_JUKEBOX_SONG, ModSoundEvents.WITHER_WALTZ, 5040.0F, 4);
    }

    public static void bootstrapStructures(BootstrapContext<Structure> context) {
        context.register(ModStructures.CATACOMB_STRUCTURE,
                new CatacombStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModRegistry.HAS_CATACOMB_BIOME_TAG_KEY)).spawnOverrides(Map.of(MobCategory.MONSTER,
                        new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.MAGMA_CUBE,
                                        1,
                                        1,
                                        1))))).generationStep(GenerationStep.Decoration.UNDERGROUND_DECORATION).build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructures.CATACOMB_START_POOL),
                        Optional.empty(),
                        3,
                        UniformHeight.of(VerticalAnchor.absolute(56), VerticalAnchor.absolute(84)),
                        Optional.empty(),
                        128));
        context.register(ModStructures.CITADEL_STRUCTURE,
                new CitadelStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModRegistry.HAS_CITADEL_BIOME_TAG_KEY)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                        WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.ENDERMAN,
                                                        1,
                                                        1,
                                                        1),
                                                new MobSpawnSettings.SpawnerData(ModEntityTypes.WARPED_ENDERMAN.value(),
                                                        1,
                                                        1,
                                                        1)))))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructures.CITADEL_START_POOL),
                        Optional.empty(),
                        4,
                        UniformHeight.of(VerticalAnchor.absolute(48), VerticalAnchor.absolute(70)),
                        Optional.empty(),
                        116));
        context.register(ModStructures.PIGLIN_MANOR_STRUCTURE,
                new CitadelStructure(new Structure.StructureSettings.Builder(context.lookup(Registries.BIOME)
                        .getOrThrow(ModRegistry.HAS_PIGLIN_MANOR_BIOME_TAG_KEY)).spawnOverrides(Map.of(MobCategory.MONSTER,
                                new StructureSpawnOverride(StructureSpawnOverride.BoundingBoxType.PIECE,
                                        WeightedRandomList.create(new MobSpawnSettings.SpawnerData(EntityType.PIGLIN, 2, 1, 1),
                                                new MobSpawnSettings.SpawnerData(ModEntityTypes.PIGLIN_HUNTER.value(),
                                                        1,
                                                        1,
                                                        1)))))
                        .generationStep(GenerationStep.Decoration.SURFACE_STRUCTURES)
                        .terrainAdapation(TerrainAdjustment.BEARD_THIN)
                        .build(),
                        context.lookup(Registries.TEMPLATE_POOL).getOrThrow(ModStructures.PIGLIN_MANOR_START_POOL),
                        Optional.empty(),
                        1,
                        UniformHeight.of(VerticalAnchor.absolute(34), VerticalAnchor.absolute(72)),
                        Optional.empty(),
                        116));
    }

    public static void bootstrapStructureSets(BootstrapContext<StructureSet> context) {
        context.register(ModStructures.CATACOMB_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.CATACOMB_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                1163018812,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.NETHER_COMPLEXES), 8)),
                                12,
                                4,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.CITADEL_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.CITADEL_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                1621815507,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.NETHER_COMPLEXES), 4)),
                                12,
                                4,
                                RandomSpreadType.LINEAR)));
        context.register(ModStructures.PIGLIN_MANOR_STRUCTURE_SET,
                new StructureSet(List.of(StructureSet.entry(context.lookup(Registries.STRUCTURE)
                        .getOrThrow(ModStructures.PIGLIN_MANOR_STRUCTURE))),
                        new RandomSpreadStructurePlacement(Vec3i.ZERO,
                                StructurePlacement.FrequencyReductionMethod.DEFAULT,
                                1.0F,
                                292421824,
                                Optional.of(new StructurePlacement.ExclusionZone(context.lookup(Registries.STRUCTURE_SET)
                                        .getOrThrow(BuiltinStructureSets.NETHER_COMPLEXES), 6)),
                                12,
                                4,
                                RandomSpreadType.LINEAR)));
    }
}
