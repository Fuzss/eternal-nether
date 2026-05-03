package fuzs.eternalnether.data.client;

import com.google.gson.JsonElement;
import fuzs.eternalnether.client.renderer.ShieldItemRenderer;
import fuzs.eternalnether.init.ModBlockFamilies;
import fuzs.eternalnether.init.ModBlocks;
import fuzs.eternalnether.init.ModItems;
import fuzs.puzzleslib.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.api.client.data.v2.ItemModelProperties;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.Direction;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.data.models.blockstates.PropertyDispatch;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.blockstates.VariantProperties;
import net.minecraft.data.models.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.HashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class ModModelProvider extends AbstractModelProvider {
    public static final ModelTemplate SHIELD_MODEL_TEMPLATE = ModelTemplates.createItem("shield", TextureSlot.PARTICLE);
    public static final ModelTemplate SHIELD_BLOCKING_MODEL_TEMPLATE = ModelTemplates.createItem("shield_blocking",
            TextureSlot.PARTICLE);
    public static final TextureSlot BAR_TEXTURE_SLOT = TextureSlot.create("bar");
    public static final TextureSlot POST_TEXTURE_SLOT = TextureSlot.create("post");
    public static final ModelTemplate BELL_BETWEEN_WALLS_MODEL_TEMPLATE = ModelTemplates.create("bell_between_walls",
            TextureSlot.PARTICLE,
            BAR_TEXTURE_SLOT);
    public static final ModelTemplate BELL_CEILING_MODEL_TEMPLATE = ModelTemplates.create("bell_ceiling",
            TextureSlot.PARTICLE,
            BAR_TEXTURE_SLOT);
    public static final ModelTemplate BELL_FLOOR_MODEL_TEMPLATE = ModelTemplates.create("bell_floor",
            TextureSlot.PARTICLE,
            BAR_TEXTURE_SLOT,
            POST_TEXTURE_SLOT);
    public static final ModelTemplate BELL_WALL_MODEL_TEMPLATE = ModelTemplates.create("bell_wall",
            TextureSlot.PARTICLE,
            BAR_TEXTURE_SLOT);

    public ModModelProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBlockModels(BlockModelGenerators builder) {
        builder.texturedModels = new HashMap<>(builder.texturedModels);
        builder.texturedModels.put(ModBlocks.WITHERED_BLACKSTONE.value(),
                TexturedModel.COLUMN_WITH_WALL.get(ModBlocks.WITHERED_BLACKSTONE.value())
                        .updateTextures(map -> map.put(TextureSlot.SIDE,
                                TextureMapping.getBlockTexture(ModBlocks.WITHERED_BLACKSTONE.value()))));
        builder.texturedModels.put(ModBlocks.CHISELED_WITHERED_BLACKSTONE.value(),
                TexturedModel.COLUMN.get(ModBlocks.CHISELED_WITHERED_BLACKSTONE.value())
                        .updateTextures(map -> map.put(TextureSlot.SIDE,
                                TextureMapping.getBlockTexture(ModBlocks.CHISELED_WITHERED_BLACKSTONE.value()))));
        ModBlockFamilies.getAllFamilies()
                .filter(BlockFamily::shouldGenerateModel)
                .forEach(blockFamily -> builder.family(blockFamily.getBaseBlock()).generateFor(blockFamily));
        builder.family(ModBlocks.WITHERED_BLACKSTONE.value())
                .slab(ModBlocks.WITHERED_BLACKSTONE_SLAB.value())
                .stairs(ModBlocks.WITHERED_BLACKSTONE_STAIRS.value())
                .wall(ModBlocks.WITHERED_BLACKSTONE_WALL.value())
                .fullBlockVariant(ModBlocks.CHISELED_WITHERED_BLACKSTONE.value());
        builder.createTrivialCube(ModBlocks.COBBLED_BLACKSTONE.value());
        builder.createTrivialCube(ModBlocks.WITHERED_BASALT.value());
        builder.createTrivialCube(ModBlocks.WITHERED_COAL_BLOCK.value());
        builder.createTrivialCube(ModBlocks.WITHERED_QUARTZ_BLOCK.value());
        builder.createTrivialBlock(ModBlocks.WITHERED_DEBRIS.value(), TexturedModel.COLUMN);
        builder.createTrivialCube(ModBlocks.SOUL_STONE.value());
        builder.createAxisAlignedPillarBlock(ModBlocks.WITHERED_BONE_BLOCK.value(), TexturedModel.COLUMN);
        TextureMapping textureMapping = bell(ModBlocks.NETHERITE_BELL.value(),
                Blocks.CRIMSON_PLANKS,
                Blocks.BLACKSTONE);
        this.createBell(ModBlocks.NETHERITE_BELL.value(), textureMapping, builder);
    }

    @Override
    public void addItemModels(ItemModelGenerators builder) {
        builder.generateFlatItem(ModItems.PIGLIN_PRISONER_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.PIGLIN_HUNTER_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WEX_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WARPED_ENDERMAN_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WRAITHER_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.CORPOR_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WITHER_SKELETON_KNIGHT_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WITHER_SKELETON_HORSE_SPAWN_EGG.value(), SPAWN_EGG);
        builder.generateFlatItem(ModItems.WITHER_WALTZ_MUSIC_DISC.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.WARPED_ENDER_PEARL.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.WITHERED_BONE.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.WITHERED_BONE_MEAL.value(), ModelTemplates.FLAT_ITEM);
        builder.generateFlatItem(ModItems.CUTLASS.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateShieldItem(ModItems.GILDED_NETHERITE_SHIELD.value(),
                Blocks.CRIMSON_PLANKS,
                ShieldItemRenderer.BLOCKING_ITEM_MODEL_PROPERTY,
                builder.output);
    }

    public static void generateShieldItem(Item item, Block particleBlock, ResourceLocation propertyResourceLocation, BiConsumer<ResourceLocation, Supplier<JsonElement>> modelOutput) {
        ResourceLocation overrideResourceLocation = ModelLocationUtils.getModelLocation(item, "_blocking");
        SHIELD_MODEL_TEMPLATE.create(ModelLocationUtils.getModelLocation(item),
                TextureMapping.particle(particleBlock),
                modelOutput,
                ItemModelProperties.overridesFactory(SHIELD_MODEL_TEMPLATE,
                        ItemModelProperties.singleOverride(overrideResourceLocation, propertyResourceLocation, 1.0F)));
        SHIELD_BLOCKING_MODEL_TEMPLATE.create(overrideResourceLocation,
                TextureMapping.particle(particleBlock),
                modelOutput);
    }

    public static TextureMapping bell(Block bellBlock, Block barBlock, Block postBlock) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(bellBlock, "_bottom"))
                .put(BAR_TEXTURE_SLOT, TextureMapping.getBlockTexture(barBlock))
                .put(POST_TEXTURE_SLOT, TextureMapping.getBlockTexture(postBlock));
    }

    private void createBell(Block block, TextureMapping textureMapping, BlockModelGenerators builder) {
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block, "_floor");
        BELL_FLOOR_MODEL_TEMPLATE.create(resourceLocation, textureMapping, builder.modelOutput);
        ResourceLocation resourceLocation2 = ModelLocationUtils.getModelLocation(block, "_ceiling");
        BELL_CEILING_MODEL_TEMPLATE.create(resourceLocation2, textureMapping, builder.modelOutput);
        ResourceLocation resourceLocation3 = ModelLocationUtils.getModelLocation(block, "_wall");
        BELL_WALL_MODEL_TEMPLATE.create(resourceLocation3, textureMapping, builder.modelOutput);
        ResourceLocation resourceLocation4 = ModelLocationUtils.getModelLocation(block, "_between_walls");
        BELL_BETWEEN_WALLS_MODEL_TEMPLATE.create(resourceLocation4, textureMapping, builder.modelOutput);
        builder.createSimpleFlatItemModel(block.asItem());
        builder.blockStateOutput.accept(MultiVariantGenerator.multiVariant(block)
                .with(PropertyDispatch.properties(BlockStateProperties.HORIZONTAL_FACING,
                                BlockStateProperties.BELL_ATTACHMENT)
                        .select(Direction.NORTH,
                                BellAttachType.FLOOR,
                                Variant.variant().with(VariantProperties.MODEL, resourceLocation))
                        .select(Direction.SOUTH,
                                BellAttachType.FLOOR,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST,
                                BellAttachType.FLOOR,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST,
                                BellAttachType.FLOOR,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.NORTH,
                                BellAttachType.CEILING,
                                Variant.variant().with(VariantProperties.MODEL, resourceLocation2))
                        .select(Direction.SOUTH,
                                BellAttachType.CEILING,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.EAST,
                                BellAttachType.CEILING,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.WEST,
                                BellAttachType.CEILING,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation2)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.NORTH,
                                BellAttachType.SINGLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation3)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.SOUTH,
                                BellAttachType.SINGLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation3)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.EAST,
                                BellAttachType.SINGLE_WALL,
                                Variant.variant().with(VariantProperties.MODEL, resourceLocation3))
                        .select(Direction.WEST,
                                BellAttachType.SINGLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation3)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))
                        .select(Direction.SOUTH,
                                BellAttachType.DOUBLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation4)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R90))
                        .select(Direction.NORTH,
                                BellAttachType.DOUBLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation4)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R270))
                        .select(Direction.EAST,
                                BellAttachType.DOUBLE_WALL,
                                Variant.variant().with(VariantProperties.MODEL, resourceLocation4))
                        .select(Direction.WEST,
                                BellAttachType.DOUBLE_WALL,
                                Variant.variant()
                                        .with(VariantProperties.MODEL, resourceLocation4)
                                        .with(VariantProperties.Y_ROT, VariantProperties.Rotation.R180))));
    }
}
