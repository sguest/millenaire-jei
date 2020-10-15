package sguest.millenairejei.jei.trading;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.trading.RecipeBuildingData;
import sguest.millenairejei.recipes.trading.TradingRecipeData;

public abstract class BaseTradingRecipeWrapper implements IRecipeWrapper {
    private final IDrawable cultureIcon;
    private final IDrawable villageIcon;
    private final List<IDrawable> buildingIcons;
    protected final TradingRecipeData recipeEntry;

    protected BaseTradingRecipeWrapper(TradingRecipeData recipeEntry, IGuiHelper guiHelper) {
        this.recipeEntry = recipeEntry;
        this.cultureIcon = getIcon(this.recipeEntry.getCultureIcon(), guiHelper);
        this.villageIcon = getIcon(this.recipeEntry.getVillageIcon(), guiHelper);
        buildingIcons = recipeEntry.getBuildings().stream().map(b -> getIcon(b.getIcon(), guiHelper)).collect(Collectors.toList());
    }

    public TradingRecipeData getRecipeEntry() {
        return recipeEntry;
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        cultureIcon.draw(minecraft, 1, 1);
        minecraft.fontRenderer.drawString(recipeEntry.getCultureName(), 20, 4, 0xFFFFFFFF);
        villageIcon.draw(minecraft, 1, 21);
        minecraft.fontRenderer.drawString(recipeEntry.getVillageName(), 20, 24, 0xFFFFFFFF);

        List<RecipeBuildingData> buildings = recipeEntry.getBuildings();

        for(int i = 0; i < buildings.size(); i++) {
            minecraft.fontRenderer.drawString(buildings.get(i).getName(), 20, 60 + i * 20, 0xFFFFFFFF);
            buildingIcons.get(i).draw(minecraft, 1, 54 + i * 20);
        }
    }

    private IDrawable getIcon(ItemStack itemStack, IGuiHelper guiHelper) {
        if(itemStack == null) {
            return guiHelper.createBlankDrawable(16, 16);
        }

        return guiHelper.createDrawableIngredient(itemStack);
    }
}
