package fuzs.eternalnether.common.data.recipes;

import fuzs.eternalnether.common.init.ModBlockFamilies;
import fuzs.eternalnether.common.init.ModBlocks;
import fuzs.eternalnether.common.init.ModItems;
import fuzs.puzzleslib.common.api.data.v3.recipes.AbstractRecipeProvider;
import net.minecraft.advancements.Advancement;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.*;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.ShieldDecorationRecipe;
import net.minecraft.world.level.block.Blocks;

public class ModRecipeProvider extends AbstractRecipeProvider {
    private final HolderGetter<Item> itemLookup;

    public ModRecipeProvider(BootstrapContext<Recipe<?>> recipeOutput, BootstrapContext<Advancement> advancementOutput) {
        super(recipeOutput, advancementOutput);
        this.itemLookup = recipeOutput.lookup(Registries.ITEM);
    }

    @Override
    public void buildRecipes() {
        this.generateFor(ModBlockFamilies.WITHERED_BLACKSTONE_FAMILY);
        this.generateFor(ModBlockFamilies.CRACKED_WITHERED_BLACKSTONE_FAMILY);
        this.generateFor(ModBlockFamilies.WARPED_NETHER_BRICKS_FAMILY);
        ShapedRecipeBuilder.shaped(this.itemLookup, RecipeCategory.BUILDING_BLOCKS, ModBlocks.WARPED_NETHER_BRICKS.value())
                .define('W', Items.WARPED_ROOTS)
                .define('N', Items.NETHER_BRICK)
                .pattern("NW")
                .pattern("WN")
                .unlockedBy(getHasName(Items.WARPED_ROOTS), this.has(Items.WARPED_ROOTS))
                .save(this.output);
        this.smeltingResultFromBase(ModBlocks.COBBLED_BLACKSTONE.value(), Blocks.BLACKSTONE);
        ShapelessRecipeBuilder.shapeless(this.itemLookup, RecipeCategory.MISC, ModItems.WITHERED_BONE_MEAL.value(), 3)
                .requires(ModItems.WITHERED_BONE.value())
                .group(getItemName(ModItems.WITHERED_BONE_MEAL.value()))
                .unlockedBy(getHasName(ModItems.WITHERED_BONE.value()), this.has(ModItems.WITHERED_BONE.value()))
                .save(this.output);
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
}
