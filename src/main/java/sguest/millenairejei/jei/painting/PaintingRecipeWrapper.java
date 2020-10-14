package sguest.millenairejei.jei.painting;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.painting.PaintingRecipeData;

public class PaintingRecipeWrapper implements IRecipeWrapper {
    private final PaintingRecipeData recipe;

    public PaintingRecipeWrapper(PaintingRecipeData recipe) {
        this.recipe = recipe;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<List<ItemStack>> inputs = new ArrayList<>();
        List<ItemStack> paintList = new ArrayList<>();
        paintList.add(recipe.getPaintItem());

        inputs.add(recipe.getInputs());
        inputs.add(paintList);
        ingredients.setInputLists(VanillaTypes.ITEM, inputs);

        ingredients.setOutput(VanillaTypes.ITEM, recipe.getOutput());
    }
    
    public PaintingRecipeData getRecipe() {
        return recipe;
    }
}
