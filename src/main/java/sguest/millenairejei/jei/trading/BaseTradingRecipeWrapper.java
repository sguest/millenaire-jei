package sguest.millenairejei.jei.trading;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.jei.DrawableWithLabel;
import sguest.millenairejei.recipes.trading.TradingRecipeData;
import sguest.millenairejei.util.UiHelper;

public abstract class BaseTradingRecipeWrapper implements IRecipeWrapper {
    private final DrawableWithLabel culture;
    private final DrawableWithLabel village;
    private final List<DrawableWithLabel> buildings;
    protected final TradingRecipeData recipeEntry;

    protected BaseTradingRecipeWrapper(TradingRecipeData recipeEntry, IGuiHelper guiHelper) {
        this.recipeEntry = recipeEntry;
        this.culture = UiHelper.toDrawable(this.recipeEntry.getCulture(), guiHelper);
        this.village = UiHelper.toDrawable(this.recipeEntry.getVillage(), guiHelper);
        buildings = recipeEntry.getBuildings().stream().map(b -> UiHelper.toDrawable(b, guiHelper)).collect(Collectors.toList());
    }

    public TradingRecipeData getRecipeEntry() {
        return recipeEntry;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        UiHelper.renderIconWithLabel(minecraft, 1, 1, culture);
        UiHelper.renderIconWithLabel(minecraft, 1, 21, village);

        for(int i = 0; i < buildings.size(); i++) {
            UiHelper.renderIconWithLabel(minecraft, 1, 54 + i * 20, buildings.get(i));
        }
    }
}
