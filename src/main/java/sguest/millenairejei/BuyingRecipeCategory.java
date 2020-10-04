package sguest.millenairejei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;

public class BuyingRecipeCategory implements IRecipeCategory<BuyingRecipe> {
    private final IDrawable background;

    public BuyingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(180, 100);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BuyingRecipe recipeWrapper, IIngredients ingredients){
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return "millenaire";
    }

    @Override
    public String getTitle() {
        return "Villager Trading";
    }

    @Override
    public String getUid() {
        return MillenaireJei.MODID + ".buying";
    }
}