package sguest.millenairejei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;

public class BuyingRecipe implements IRecipeWrapper {
    private final BuyingRecipeEntry recipeEntry;

    public BuyingRecipe(BuyingRecipeEntry recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipeEntry.GetBuyingItem());
    }
}