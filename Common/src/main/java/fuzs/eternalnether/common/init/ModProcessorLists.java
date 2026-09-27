package fuzs.eternalnether.common.init;

import com.google.common.collect.ImmutableList;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.structure.templatesystem.AlwaysTrueTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.ProcessorRule;
import net.minecraft.world.level.levelgen.structure.templatesystem.RandomBlockMatchTest;
import net.minecraft.world.level.levelgen.structure.templatesystem.RuleProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessorList;

import java.util.List;

public final class ModProcessorLists {
    public static final ResourceKey<StructureProcessorList> CATACOMB_SKULL = register("catacomb/catacomb_skull");
    public static final ResourceKey<StructureProcessorList> WITHERED_BLACKSTONE = register("catacomb/withered_blackstone");
    public static final ResourceKey<StructureProcessorList> CITADEL = register("citadel/citadel");
    public static final ResourceKey<StructureProcessorList> PIGLIN_MANOR = register("piglin_manor/piglin_manor");

    private static ResourceKey<StructureProcessorList> register(String path) {
        return ModRegistry.REGISTRIES.makeResourceKey(Registries.PROCESSOR_LIST, path);
    }

    private static ProcessorRule rule(Block input, float probability, Block output) {
        return new ProcessorRule(new RandomBlockMatchTest(input, probability),
                AlwaysTrueTest.INSTANCE,
                output.defaultBlockState());
    }

    private static List<ProcessorRule> catacombCommonRules() {
        Block cracked = ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CRACKED).value();
        Block chiseled = ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED).value();
        return List.of(rule(Blocks.BLACKSTONE, 0.4F, Blocks.POLISHED_BLACKSTONE_BRICKS),
                rule(Blocks.BLACKSTONE, 0.4F, Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS),
                rule(Blocks.BLACKSTONE, 0.3F, Blocks.POLISHED_BLACKSTONE),
                rule(ModBlocks.WITHERED_BLACKSTONE.value(), 0.4F, cracked),
                rule(ModBlocks.WITHERED_BLACKSTONE.value(), 0.2F, chiseled));
    }

    public static void bootstrap(BootstrapContext<StructureProcessorList> context) {
        context.register(CATACOMB_SKULL,
                new StructureProcessorList(List.of(new RuleProcessor(ImmutableList.<ProcessorRule>builder()
                        .addAll(catacombCommonRules())
                        .add(rule(ModBlocks.WITHERED_QUARTZ_BLOCK.value(), 0.4F, Blocks.SMOOTH_QUARTZ))
                        .add(rule(ModBlocks.WITHERED_COAL_BLOCK.value(), 0.4F, Blocks.COAL_BLOCK))
                        .add(rule(Blocks.DIRT, 1.0F, Blocks.LAVA))
                        .build()))));
        context.register(WITHERED_BLACKSTONE,
                new StructureProcessorList(List.of(new RuleProcessor(ImmutableList.<ProcessorRule>builder()
                        .addAll(catacombCommonRules())
                        .add(rule(Blocks.GRANITE, 0.25F, Blocks.ANCIENT_DEBRIS))
                        .add(rule(Blocks.GRANITE, 1.0F, ModBlocks.WITHERED_DEBRIS.value()))
                        .add(rule(Blocks.ANDESITE, 0.125F, Blocks.ANCIENT_DEBRIS))
                        .add(rule(Blocks.ANDESITE, 1.0F, ModBlocks.WITHERED_DEBRIS.value()))
                        .add(rule(Blocks.DIORITE, 0.0625F, Blocks.ANCIENT_DEBRIS))
                        .add(rule(Blocks.DIORITE, 1.0F, ModBlocks.WITHERED_DEBRIS.value()))
                        .add(rule(Blocks.DIRT, 1.0F, Blocks.LAVA))
                        .add(rule(Blocks.COBBLESTONE, 0.125F, Blocks.ANCIENT_DEBRIS))
                        .add(rule(Blocks.COBBLESTONE, 1.0F, Blocks.GILDED_BLACKSTONE))
                        .add(rule(Blocks.CRYING_OBSIDIAN, 0.5F, Blocks.OBSIDIAN))
                        .add(rule(ModBlocks.SOUL_STONE.value(), 0.3F, Blocks.SOUL_SAND))
                        .add(rule(ModBlocks.SOUL_STONE.value(), 0.4F, Blocks.SOUL_SOIL))
                        .build()))));
        context.register(CITADEL,
                new StructureProcessorList(List.of(new RuleProcessor(
                        List.of(rule(Blocks.CHEST, 0.6F, Blocks.CAVE_AIR))))));
        context.register(PIGLIN_MANOR,
                new StructureProcessorList(List.of(new RuleProcessor(List.of(rule(Blocks.POLISHED_BLACKSTONE_BRICKS,
                                0.4F,
                                Blocks.CRACKED_POLISHED_BLACKSTONE_BRICKS),
                        rule(ModBlocks.COBBLED_BLACKSTONE.value(), 0.1F, Blocks.GILDED_BLACKSTONE),
                        rule(Blocks.CHEST, 0.7F, Blocks.CAVE_AIR))))));
    }
}
