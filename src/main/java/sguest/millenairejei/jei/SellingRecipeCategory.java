package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.ItemHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;

public class SellingRecipeCategory implements IRecipeCategory<SellingRecipeWrapper> {
    private final IDrawable background;
    private final String title;
    private final IDrawable icon;

    public SellingRecipeCategory(IGuiHelper guiHelper) {
        background = guiHelper.createBlankDrawable(180, 50);
        title = I18n.format("millenairejei.sellingrecipes.tabtitle");
        icon = guiHelper.createDrawableIngredient(ItemHelper.getStackFromResource("millenaire:denierargent"));
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SellingRecipeWrapper recipeWrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 1, 15);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getSellingItem());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return "Millenaire";
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getUid() {
        return MillenaireJei.MODID + ".selling";
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }
}