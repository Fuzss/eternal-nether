package fuzs.eternalnether.common.data.client;

import fuzs.eternalnether.common.client.renderer.special.GildedNetheriteShieldSpecialRenderer;
import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.eternalnether.common.init.ModItems;
import fuzs.puzzleslib.common.api.client.data.v2.AbstractModelProvider;
import fuzs.puzzleslib.common.api.client.data.v2.models.ItemModelGenerationHelper;
import fuzs.puzzleslib.common.api.client.data.v2.models.ModelLocationHelper;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.common.api.init.v3.family.BlockSetVariant;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.MultiVariant;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.special.ShieldSpecialRenderer;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.core.Direction;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.properties.BellAttachType;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.Collections;
import java.util.function.Supplier;

public class ModModelProvider extends AbstractModelProvider {
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
    public void addBlockModels(BlockModelGenerators blockModelGenerators) {
        BlockModelGenerators.TEXTURED_MODELS.put(ModBlocks.WITHERED_BLACKSTONE.value(),
                TexturedModel.COLUMN_WITH_WALL.get(ModBlocks.WITHERED_BLACKSTONE.value())
                        .updateTextures((TextureMapping map) -> {
                            map.put(TextureSlot.SIDE,
                                    TextureMapping.getBlockTexture(ModBlocks.WITHERED_BLACKSTONE.value()));
                        }));
        BlockModelGenerators.TEXTURED_MODELS.put(ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED)
                        .value(),
                TexturedModel.COLUMN.get(ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(BlockSetVariant.CHISELED)
                        .value()).updateTextures((TextureMapping map) -> {
                    map.put(TextureSlot.SIDE,
                            TextureMapping.getBlockTexture(ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY.getBlock(
                                    BlockSetVariant.CHISELED).value()));
                }));
        blockModelGenerators.createTrivialBlock(ModBlocks.WITHERED_BLACKSTONE.value(),
                BlockModelGenerators.TEXTURED_MODELS::get);
        blockModelGenerators.createTrivialCube(ModBlocks.WARPED_NETHER_BRICKS.value());
        // TODO use proper Puzzles Lib method
        this.generateForBlocks(blockModelGenerators,
                ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY,
                Collections.emptyMap(),
                BlockModelGenerators.TEXTURED_MODELS.get(ModBlocks.WITHERED_BLACKSTONE.value()));
        this.generateForBlocks(blockModelGenerators,
                ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY,
                Collections.emptyMap());
        this.generateForBlocks(blockModelGenerators,
                ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY,
                Collections.emptyMap());
        blockModelGenerators.createTrivialCube(ModBlocks.COBBLED_BLACKSTONE.value());
        blockModelGenerators.createTrivialCube(ModBlocks.WITHERED_BASALT.value());
        blockModelGenerators.createTrivialCube(ModBlocks.WITHERED_COAL_BLOCK.value());
        blockModelGenerators.createTrivialCube(ModBlocks.WITHERED_QUARTZ_BLOCK.value());
        blockModelGenerators.createTrivialBlock(ModBlocks.WITHERED_DEBRIS.value(), TexturedModel.COLUMN);
        blockModelGenerators.createTrivialCube(ModBlocks.SOUL_STONE.value());
        blockModelGenerators.createAxisAlignedPillarBlock(ModBlocks.WITHERED_BONE_BLOCK.value(), TexturedModel.COLUMN);
        TextureMapping textureMapping = bell(ModBlocks.NETHERITE_BELL.value(),
                Blocks.CRIMSON_PLANKS,
                Blocks.BLACKSTONE);
        this.createBell(ModBlocks.NETHERITE_BELL.value(), textureMapping, blockModelGenerators);
    }

    public static TextureMapping bell(Block bellBlock, Block barBlock, Block postBlock) {
        return new TextureMapping().put(TextureSlot.PARTICLE, TextureMapping.getBlockTexture(bellBlock, "_bottom"))
                .put(BAR_TEXTURE_SLOT, TextureMapping.getBlockTexture(barBlock))
                .put(POST_TEXTURE_SLOT, TextureMapping.getBlockTexture(postBlock));
    }

    /**
     * @see BlockModelGenerators#createBell()
     */
    public final void createBell(Block block, TextureMapping textureMapping, BlockModelGenerators blockModelGenerators) {
        Identifier floorTexture = BELL_FLOOR_MODEL_TEMPLATE.create(ModelLocationHelper.getBlockModel(block, "_floor"),
                textureMapping,
                blockModelGenerators.modelOutput);
        Identifier ceilingTexture = BELL_CEILING_MODEL_TEMPLATE.create(ModelLocationHelper.getBlockModel(block,
                "_ceiling"), textureMapping, blockModelGenerators.modelOutput);
        Identifier wallTexture = BELL_WALL_MODEL_TEMPLATE.create(ModelLocationHelper.getBlockModel(block, "_wall"),
                textureMapping,
                blockModelGenerators.modelOutput);
        Identifier betweenWallsTexture = BELL_BETWEEN_WALLS_MODEL_TEMPLATE.create(ModelLocationHelper.getBlockModel(
                block,
                "_between_walls"), textureMapping, blockModelGenerators.modelOutput);
        MultiVariant floor = BlockModelGenerators.plainVariant(floorTexture);
        MultiVariant ceiling = BlockModelGenerators.plainVariant(ceilingTexture);
        MultiVariant wall = BlockModelGenerators.plainVariant(wallTexture);
        MultiVariant betweenWalls = BlockModelGenerators.plainVariant(betweenWallsTexture);
        blockModelGenerators.registerSimpleFlatItemModel(block.asItem());
        blockModelGenerators.blockStateOutput.accept(MultiVariantGenerator.dispatch(block)
                .with(PropertyDispatch.initial(BlockStateProperties.HORIZONTAL_FACING,
                                BlockStateProperties.BELL_ATTACHMENT)
                        .select(Direction.NORTH, BellAttachType.FLOOR, floor)
                        .select(Direction.SOUTH, BellAttachType.FLOOR, floor.with(BlockModelGenerators.Y_ROT_180))
                        .select(Direction.EAST, BellAttachType.FLOOR, floor.with(BlockModelGenerators.Y_ROT_90))
                        .select(Direction.WEST, BellAttachType.FLOOR, floor.with(BlockModelGenerators.Y_ROT_270))
                        .select(Direction.NORTH, BellAttachType.CEILING, ceiling)
                        .select(Direction.SOUTH, BellAttachType.CEILING, ceiling.with(BlockModelGenerators.Y_ROT_180))
                        .select(Direction.EAST, BellAttachType.CEILING, ceiling.with(BlockModelGenerators.Y_ROT_90))
                        .select(Direction.WEST, BellAttachType.CEILING, ceiling.with(BlockModelGenerators.Y_ROT_270))
                        .select(Direction.NORTH, BellAttachType.SINGLE_WALL, wall.with(BlockModelGenerators.Y_ROT_270))
                        .select(Direction.SOUTH, BellAttachType.SINGLE_WALL, wall.with(BlockModelGenerators.Y_ROT_90))
                        .select(Direction.EAST, BellAttachType.SINGLE_WALL, wall)
                        .select(Direction.WEST, BellAttachType.SINGLE_WALL, wall.with(BlockModelGenerators.Y_ROT_180))
                        .select(Direction.SOUTH,
                                BellAttachType.DOUBLE_WALL,
                                betweenWalls.with(BlockModelGenerators.Y_ROT_90))
                        .select(Direction.NORTH,
                                BellAttachType.DOUBLE_WALL,
                                betweenWalls.with(BlockModelGenerators.Y_ROT_270))
                        .select(Direction.EAST, BellAttachType.DOUBLE_WALL, betweenWalls)
                        .select(Direction.WEST,
                                BellAttachType.DOUBLE_WALL,
                                betweenWalls.with(BlockModelGenerators.Y_ROT_180))));
    }

    @Override
    public void addItemModels(ItemModelGenerators itemModelGenerators) {
        this.generateForItems(itemModelGenerators, ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY, Collections.emptyMap());
        this.generateForItems(itemModelGenerators,
                ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY,
                Collections.emptyMap());
        this.generateForItems(itemModelGenerators,
                ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY,
                Collections.emptyMap());
        itemModelGenerators.generateFlatItem(ModItems.PIGLIN_PRISONER_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.PIGLIN_HUNTER_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WEX_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WARPED_ENDERMAN_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WRAITHER_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CORPOR_SPAWN_EGG.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WITHER_SKELETON_KNIGHT_SPAWN_EGG.value(),
                ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WITHER_SKELETON_HORSE_SPAWN_EGG.value(),
                ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WITHER_WALTZ_MUSIC_DISC.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WARPED_ENDER_PEARL.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WITHERED_BONE.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.WITHERED_BONE_MEAL.value(), ModelTemplates.FLAT_ITEM);
        itemModelGenerators.generateFlatItem(ModItems.CUTLASS.value(), ModelTemplates.FLAT_HANDHELD_ITEM);
        generateShield(ModItems.GILDED_NETHERITE_SHIELD.value(),
                Blocks.CRIMSON_PLANKS,
                GildedNetheriteShieldSpecialRenderer.Unbaked::new,
                itemModelGenerators);
    }

    /**
     * TODO this has moved to Puzzles Lib
     */
    @Deprecated
    public static void generateShield(Item item, Block particleBlock, Supplier<SpecialModelRenderer.Unbaked<?>> specialModelSupplier, ItemModelGenerators itemModelGenerators) {
        Identifier normalModel = ItemModelGenerationHelper.SHIELD_MODEL_TEMPLATE.create(ModelLocationHelper.getItemModel(
                item), TextureMapping.particle(particleBlock), itemModelGenerators.modelOutput);
        Identifier blockingModel = ItemModelGenerationHelper.SHIELD_BLOCKING_MODEL_TEMPLATE.create(ModelLocationHelper.getItemModel(
                item,
                "_blocking"), TextureMapping.particle(particleBlock), itemModelGenerators.modelOutput);
        ItemModel.Unbaked normal = ItemModelUtils.specialModel(normalModel, specialModelSupplier.get());
        ItemModel.Unbaked blocking = ItemModelUtils.specialModel(blockingModel, specialModelSupplier.get());
        itemModelGenerators.itemModelOutput.accept(item,
                ItemModelUtils.conditional(ShieldSpecialRenderer.DEFAULT_TRANSFORMATION,
                        ItemModelUtils.isUsingItem(),
                        blocking,
                        normal));
    }
}
