package sguest.millenairejei.jei.villagercrafting;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.jei.DrawableWithLabel;
import sguest.millenairejei.recipes.villagercrafting.VillagerSlaughterRecipeData;
import sguest.millenairejei.util.UiHelper;

public class VillagerSlaughteringRecipeWrapper implements IRecipeWrapper {
    private final VillagerSlaughterRecipeData recipeEntry;
    private final DrawableWithLabel culture;
    private final List<DrawableWithLabel> villagers;

    public VillagerSlaughteringRecipeWrapper(VillagerSlaughterRecipeData recipeEntry, IGuiHelper guiHelper) {
        this.recipeEntry = recipeEntry;
        this.culture = UiHelper.toDrawable(recipeEntry.getCulture(), guiHelper);
        this.villagers = recipeEntry.getVillagers().stream().map(v -> UiHelper.toDrawable(v, guiHelper)).collect(Collectors.toList());
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setOutputs(VanillaTypes.ITEM, recipeEntry.getOutputs());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        UiHelper.renderIconWithLabel(minecraft, 1, 1, culture);

        minecraft.fontRenderer.drawString(recipeEntry.getAnimalName(), 1, 21, 0xFFFFFFFF);

        for(int i = 0; i < villagers.size(); i++) {
            UiHelper.renderIconWithLabel(minecraft, 1, 60 + i * 20, villagers.get(i));
        }
    }

    public VillagerSlaughterRecipeData getRecipe() {
        return recipeEntry;
    }
}
