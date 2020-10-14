package sguest.millenairejei.jei.trading;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.recipes.trading.TradingRecipeData;
import sguest.millenairejei.util.DenierHelper;

public class BuyingRecipeWrapper extends BaseTradingRecipeWrapper {
    public BuyingRecipeWrapper(TradingRecipeData recipeEntry, IGuiHelper guiHelper) {
        super(recipeEntry, guiHelper);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutput(VanillaTypes.ITEM, recipeEntry.getTradeItem());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 20, 21);
    }
}