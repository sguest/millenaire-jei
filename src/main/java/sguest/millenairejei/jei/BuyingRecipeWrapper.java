package sguest.millenairejei.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.millenairedata.RecipeData;
import sguest.millenairejei.util.DenierHelper;

public class BuyingRecipeWrapper extends BaseTradingRecipeWrapper {
    public BuyingRecipeWrapper(RecipeData recipeEntry) {
        super(recipeEntry);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipeEntry.GetTradeItem());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 20, 21);
    }
}