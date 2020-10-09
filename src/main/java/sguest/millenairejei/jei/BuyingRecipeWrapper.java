package sguest.millenairejei.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.RecipeData;
import sguest.millenairejei.util.DenierHelper;
import sguest.millenairejei.util.ItemHelper;

public class BuyingRecipeWrapper implements IRecipeWrapper {
    private final RecipeData recipeEntry;

    public BuyingRecipeWrapper(RecipeData recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, ItemHelper.getStackFromResource("millenaire:purse"));
        ingredients.setOutput(VanillaTypes.ITEM, recipeEntry.GetTradeItem());
    }

    public ItemStack getBuyingItem() {
        return recipeEntry.GetTradeItem();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipeEntry.getShopName(), 1, 1, 0xFFFFFFFF);
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 1, 21);
    }
}