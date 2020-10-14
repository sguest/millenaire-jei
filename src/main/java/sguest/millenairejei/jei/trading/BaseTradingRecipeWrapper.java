package sguest.millenairejei.jei.trading;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.recipes.trading.RecipeBuildingData;
import sguest.millenairejei.recipes.trading.TradingRecipeData;

public abstract class BaseTradingRecipeWrapper implements IRecipeWrapper {
    private final IDrawable cultureIcon;
    private final List<IDrawable> buildingIcons;
    protected final TradingRecipeData recipeEntry;

    protected BaseTradingRecipeWrapper(TradingRecipeData recipeEntry, IGuiHelper guiHelper) {
        this.recipeEntry = recipeEntry;
        this.cultureIcon = guiHelper.createDrawableIngredient(this.recipeEntry.getCultureIcon());
        buildingIcons = recipeEntry.getBuildings().stream().map(b -> guiHelper.createDrawableIngredient(b.getIcon())).collect(Collectors.toList());
    }

    public TradingRecipeData getRecipeEntry() {
        return recipeEntry;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        cultureIcon.draw(minecraft, 1, 1);
        minecraft.fontRenderer.drawString(recipeEntry.getCultureName(), 20, 4, 0xFFFFFFFF);
        List<RecipeBuildingData> buildings = recipeEntry.getBuildings();

        for(int i = 0; i < buildings.size(); i++) {
            minecraft.fontRenderer.drawString(buildings.get(i).getName(), 20, 40 + i * 20, 0xFFFFFFFF);
            buildingIcons.get(i).draw(minecraft, 1, 34 + i * 20);
        }
    }
}
