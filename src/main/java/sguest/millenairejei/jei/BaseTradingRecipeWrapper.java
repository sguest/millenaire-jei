package sguest.millenairejei.jei;

import java.util.List;

import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.recipes.RecipeData;

public abstract class BaseTradingRecipeWrapper implements IRecipeWrapper {
    protected final RecipeData recipeEntry;

    protected BaseTradingRecipeWrapper(RecipeData recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    public RecipeData getRecipeEntry() {
        return recipeEntry;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipeEntry.getCultureName(), 20, 4, 0xFFFFFFFF);
        List<String> shopNames = recipeEntry.getShopNames();

        for(int i = 0; i < shopNames.size(); i++) {
            minecraft.fontRenderer.drawString(shopNames.get(i), 1, 40 + i * 20, 0xFFFFFFFF);
        }
    }
}
