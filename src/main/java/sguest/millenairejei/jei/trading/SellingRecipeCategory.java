package sguest.millenairejei.jei.trading;

import mezz.jei.api.IGuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.jei.MillenaireJeiPlugin;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.ItemHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;

public class SellingRecipeCategory extends BaseTradingRecipeCategory<SellingRecipeWrapper> {
    public SellingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, I18n.format("millenairejei.sellingrecipes.tabtitle"), Constants.DENIER_ARGENT);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, SellingRecipeWrapper recipeWrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 1, 35);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getRecipeEntry().getTradeItem());

        recipeLayout.getItemStacks().init(1, false, 45, 35);
        recipeLayout.getItemStacks().set(1, ItemHelper.getStackFromResource(Constants.DENIER_POUCH));
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.SELLING;
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 22, 35);
    }
}