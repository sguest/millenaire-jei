package sguest.millenairejei.jei.painting;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.jei.MillenaireJeiPlugin;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.UiHelper;
import sguest.millenairejei.util.ItemHelper;

public class PaintingRecipeCategory implements IRecipeCategory<PaintingRecipeWrapper> {
    private final String title;
    private final IDrawable background;
    private final IDrawable icon;
    private final IDrawable arrow;

    public PaintingRecipeCategory(IGuiHelper guiHelper) {
        title = I18n.format("millenairejei.paintingrecipes.tabtitle");
        background = guiHelper.createBlankDrawable(100, 50);
        icon = guiHelper.createDrawableIngredient(ItemHelper.getStackFromResource(Constants.WHITEWASH_BUCKET));
        arrow = UiHelper.staticArrow(guiHelper);
    }
    
    @Override
    public String getUid() {
        return MillenaireJeiPlugin.PAINTING;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getModName() {
        return Constants.RECIPE_MOD_DISPLAY;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, PaintingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 5, 30);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getRecipe().getInputs());

        recipeLayout.getItemStacks().init(1, true, 42, 5);
        recipeLayout.getItemStacks().set(1, recipeWrapper.getRecipe().getPaintItem());

        recipeLayout.getItemStacks().init(2, false, 79, 30);
        recipeLayout.getItemStacks().set(2, recipeWrapper.getRecipe().getOutput());
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 42, 30);
    }
}
