package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.GuiElementHelper;
import sguest.millenairejei.util.ItemHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;

public class BuyingRecipeCategory implements IRecipeCategory<BuyingRecipeWrapper> {
    private final IDrawable background;
    private final String title;
    private final IDrawable icon;
    private final IDrawable arrow;

    public BuyingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(180, 50);
        title = I18n.format("millenairejei.buyingrecipes.tabtitle");
        icon = guiHelper.createDrawableIngredient(ItemHelper.getStackFromResource(Constants.DENIER_OR));
        arrow = GuiElementHelper.staticArrow(guiHelper);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BuyingRecipeWrapper recipeWrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 85, 15);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getBuyingItem());

        recipeLayout.getItemStacks().init(1, false, 1, 15);
        recipeLayout.getItemStacks().set(1, ItemHelper.getStackFromResource(Constants.DENIER_POUCH));
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return Constants.RECIPE_MOD_DISPLAY;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.BUYING;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 55, 15);
    }
}