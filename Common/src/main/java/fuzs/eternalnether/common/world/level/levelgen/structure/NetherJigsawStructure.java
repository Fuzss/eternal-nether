package fuzs.eternalnether.common.world.level.levelgen.structure;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.eternalnether.common.init.ModStructureTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

import java.util.List;
import java.util.Optional;

/**
 * A {@link JigsawStructure} variant for dimensions without a usable surface heightmap, such as the Nether. Instead of
 * projecting the start piece onto a heightmap, it locates a cave floor in a single center column and places the start
 * piece on top of it, then relies on terrain adaptation (beard) to blend the structure into the surrounding terrain.
 */
public class NetherJigsawStructure extends JigsawStructure {
    public static final MapCodec<NetherJigsawStructure> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                    JigsawStructure.CODEC.forGetter(NetherJigsawStructure::jigsaw),
                    NetherJigsawStructure.Placement.CODEC.forGetter(NetherJigsawStructure::placement))
            .apply(instance, NetherJigsawStructure::new));

    private final Placement placement;

    public NetherJigsawStructure(JigsawStructure jigsaw, Placement placement) {
        super(new StructureSettings(jigsaw.biomes(),
                        jigsaw.spawnOverrides(),
                        jigsaw.step(),
                        jigsaw.terrainAdaptation()),
                jigsaw.startPool,
                jigsaw.startJigsawName,
                jigsaw.maxDepth,
                jigsaw.startHeight,
                jigsaw.useExpansionHack,
                jigsaw.projectStartToHeightmap,
                jigsaw.maxDistanceFromCenter,
                jigsaw.poolAliases,
                jigsaw.dimensionPadding,
                jigsaw.liquidSettings);
        this.placement = placement;
    }

    public NetherJigsawStructure(Structure.StructureSettings settings, Holder<StructureTemplatePool> startPool, Optional<Identifier> startJigsawName, int maxDepth, HeightProvider startHeight, Optional<Heightmap.Types> projectStartToHeightmap, JigsawStructure.MaxDistance maxDistanceFromCenter, Placement placement) {
        super(settings,
                startPool,
                startJigsawName,
                maxDepth,
                startHeight,
                false,
                projectStartToHeightmap,
                maxDistanceFromCenter,
                List.of(),
                JigsawStructure.DEFAULT_DIMENSION_PADDING,
                JigsawStructure.DEFAULT_LIQUID_SETTINGS);
        this.placement = placement;
    }

    public Placement placement() {
        return this.placement;
    }

    private JigsawStructure jigsaw() {
        return this;
    }

    @Override
    public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        int blockX = chunkPos.getMiddleBlockX();
        int blockZ = chunkPos.getMiddleBlockZ();
        NoiseColumn column = context.chunkGenerator()
                .getBaseColumn(blockX, blockZ, context.heightAccessor(), context.randomState());
        WorldGenerationContext worldGenerationContext = new WorldGenerationContext(context.chunkGenerator(),
                context.heightAccessor());
        int minY = context.heightAccessor().getMinY() + 1;
        int maxY = context.heightAccessor().getMaxY();
        int anchorY = Mth.clamp(this.startHeight.sample(context.random(), worldGenerationContext), minY, maxY);
        if (this.placement.checkLavaLake() && isLavaLake(column, context.chunkGenerator().getSeaLevel(), anchorY)) {
            return Optional.empty();
        }

        int endY = Mth.clamp(this.placement.endHeight().resolveY(worldGenerationContext), minY, maxY);
        int blockY = findElevation(column, anchorY, endY, this.placement.requiredVerticalSpace());
        if (blockY == Integer.MIN_VALUE) {
            return Optional.empty();
        }

        BlockPos blockPos = new BlockPos(blockX, blockY, blockZ);
        return JigsawPlacement.addPieces(context,
                this.startPool,
                this.startJigsawName,
                this.maxDepth,
                blockPos,
                this.useExpansionHack,
                this.projectStartToHeightmap,
                this.maxDistanceFromCenter,
                PoolAliasLookup.create(this.poolAliases, blockPos, context.seed()),
                this.dimensionPadding,
                this.liquidSettings);
    }

    /**
     * Finds the first cave floor between the sampled {@code anchorY} and {@code endY}, requiring the given amount of
     * air above the floor.
     */
    private static int findElevation(NoiseColumn column, int anchorY, int endY, int requiredVerticalSpace) {
        int step = anchorY <= endY ? 1 : -1;
        for (int y = anchorY; y != endY + step; y += step) {
            if (!isFloor(column.getBlock(y - 1)) || !column.getBlock(y).isAir()) {
                continue;
            }

            if (hasHeadroom(column, y, requiredVerticalSpace)) {
                return y;
            }
        }

        return Integer.MIN_VALUE;
    }

    private static boolean hasHeadroom(NoiseColumn column, int y, int requiredVerticalSpace) {
        for (int i = 1; i < requiredVerticalSpace; i++) {
            if (!column.getBlock(y + i).isAir()) {
                return false;
            }
        }

        return true;
    }

    private static boolean isFloor(BlockState blockState) {
        return !blockState.isAir() && blockState.getFluidState().isEmpty();
    }

    /**
     * Checks whether the column consists of a lava sea at the dimension's sea level with only air above it.
     */
    private static boolean isLavaLake(NoiseColumn column, int seaLevel, int airTop) {
        int lavaY = seaLevel - 1;
        if (airTop <= lavaY || !column.getBlock(lavaY).is(Blocks.LAVA)) {
            return false;
        }

        for (int y = lavaY + 1; y <= airTop; y++) {
            if (!column.getBlock(y).isAir()) {
                return false;
            }
        }

        return true;
    }

    @Override
    public StructureType<?> type() {
        return ModStructureTypes.NETHER_JIGSAW_STRUCTURE_TYPE.value();
    }

    public record Placement(VerticalAnchor endHeight, int requiredVerticalSpace, boolean checkLavaLake) {
        public static final MapCodec<Placement> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                        VerticalAnchor.CODEC.fieldOf("end_height").forGetter(Placement::endHeight),
                        Codec.intRange(0, 128   ).fieldOf("required_vertical_space").forGetter(Placement::requiredVerticalSpace),
                        Codec.BOOL.fieldOf("check_lava_lake").forGetter(Placement::checkLavaLake))
                .apply(instance, Placement::new));
    }
}
