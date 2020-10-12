package sguest.millenairejei.jei;

import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.RecipeData;

public abstract class BaseTradingRecipeWrapper implements IRecipeWrapper {
    protected final RecipeData recipeEntry;

    public BaseTradingRecipeWrapper(RecipeData recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    public ItemStack getTradeItem() {
        return recipeEntry.GetTradeItem();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipeEntry.getCultureName(), 1, 1, 0xFFFFFFFF);
        List<String> shopNames = recipeEntry.getShopNames();

        for(int i = 0; i < shopNames.size(); i++) {
            minecraft.fontRenderer.drawString(shopNames.get(i), 1, 40 + i * 20, 0xFFFFFFFF);
        }
    }
}
