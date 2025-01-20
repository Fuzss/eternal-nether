package com.izofar.bygonenether.world.level.levelgen.structure;

import com.izofar.bygonenether.init.ModStructures;
import com.izofar.bygonenether.util.ModStructureUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Optional;

public class CatacombStructure extends Structure {
    public static final MapCodec<CatacombStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            CatacombStructure.settingsCodec(instance),
            StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
            ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name")
                    .forGetter(structure -> structure.startJigsawName),
            Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
            HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
            Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap")
                    .forGetter(structure -> structure.projectStartToHeightmap),
            Codec.intRange(1, 128)
                    .fieldOf("max_distance_from_center")
                    .forGetter(structure -> structure.maxDistanceFromCenter)).apply(instance, CatacombStructure::new));

    private final Holder<StructureTemplatePool> startPool;
    private final Optional<ResourceLocation> startJigsawName;
    private final int size;
    private final HeightProvider startHeight;
    private final Optional<Heightmap.Types> projectStartToHeightmap;
    private final int maxDistanceFromCenter;

    public CatacombStructure(Structure.StructureSettings config, Holder<StructureTemplatePool> startPool, Optional<ResourceLocation> startJigsawName, int size, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, int maxDistanceFromCenter) {
        super(config);
        this.startPool = startPool;
        this.startJigsawName = startJigsawName;
        this.size = size;
        this.startHeight = startHeight;
        this.projectStartToHeightmap = projectStartToHeightmap;
        this.maxDistanceFromCenter = maxDistanceFromCenter;
    }

    private static boolean checkLocation(Structure.GenerationContext context) {
        BlockPos blockpos = context.chunkPos().getMiddleBlockPosition(0);
        NoiseColumn blockReader = context.chunkGenerator()
                .getBaseColumn(blockpos.getX(), blockpos.getZ(), context.heightAccessor(), context.randomState());
        return !ModStructureUtils.isBuried(blockReader, 48, ModStructureUtils.getScaledNetherHeight(context, 72)) &&
                !ModStructureUtils.isLavaLake(blockReader);
    }

    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        if (checkLocation(context)) {
            BlockPos blockPos = ModStructureUtils.getElevation(context,
                    56,
                    ModStructureUtils.getScaledNetherHeight(context, 84));
            return JigsawPlacement.addPieces(context,
                    this.startPool,
                    this.startJigsawName,
                    this.size,
                    blockPos,
                    false,
                    this.projectStartToHeightmap,
                    this.maxDistanceFromCenter,
                    PoolAliasLookup.create(List.of(), blockPos, context.seed()),
                    JigsawStructure.DEFAULT_DIMENSION_PADDING,
                    JigsawStructure.DEFAULT_LIQUID_SETTINGS);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public StructureType<?> type() {
        return ModStructures.CATACOMB_STRUCTURE_TYPE.value();
    }

}
