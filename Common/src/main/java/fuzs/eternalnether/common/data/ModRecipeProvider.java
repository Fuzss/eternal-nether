package fuzs.eternalnether.common.data;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.eternalnether.common.init.ModItems;
import fuzs.puzzleslib.common.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.common.api.data.v2.core.DataProviderContext;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.level.block.Blocks;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        this.generateForEnabledBlockFamilies(FeatureFlags.DEFAULT_FLAGS);
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.BUILDING_BLOCKS, ModBlocks.WARPED_NETHER_BRICKS.value())
                .define('W', Items.WARPED_ROOTS)
                .define('N', Items.NETHER_BRICK)
                .pattern("NW")
                .pattern("WN")
                .unlockedBy(getHasName(Items.WARPED_ROOTS), this.has(Items.WARPED_ROOTS))
                .save(recipeOutput);
        this.smeltingResultFromBase(ModBlocks.COBBLED_BLACKSTONE.value(), Blocks.BLACKSTONE);
        ShapelessRecipeBuilder.shapeless(this.items(), RecipeCategory.MISC, ModItems.WITHERED_BONE_MEAL.value(), 3)
                .requires(ModItems.WITHERED_BONE.value())
                .group(getItemName(ModItems.WITHERED_BONE_MEAL.value()))
                .unlockedBy(getHasName(ModItems.WITHERED_BONE.value()), this.has(ModItems.WITHERED_BONE.value()))
                .save(recipeOutput);
        this.nineBlockStorageRecipesRecipesWithCustomUnpacking(RecipeCategory.MISC,
                ModItems.WITHERED_BONE_MEAL.value(),
                RecipeCategory.BUILDING_BLOCKS,
                ModItems.WITHERED_BONE_BLOCK.value(),
                getConversionRecipeName(ModItems.WITHERED_BONE_MEAL.value(), ModItems.WITHERED_BONE_BLOCK.value()),
                getItemName(ModItems.WITHERED_BONE_MEAL.value()));
        SpecialRecipeBuilder.special(() -> new ShieldDecorationRecipe(this.tag(ItemTags.BANNERS),
                        Ingredient.of(ModItems.GILDED_NETHERITE_SHIELD.value()),
                        new ItemStackTemplate(ModItems.GILDED_NETHERITE_SHIELD.value())))
                .save(this.output, "shield_decoration");
    }

    @Override
    public void generateForEnabledBlockFamilies(FeatureFlagSet flagSet) {
        ModBlockFamilies.getAllFamilies().forEach((BlockFamily blockFamily) -> {
            this.generateRecipes(blockFamily, flagSet);
        });
    }
}
