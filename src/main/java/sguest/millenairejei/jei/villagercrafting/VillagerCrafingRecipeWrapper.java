package sguest.millenairejei.jei.villagercrafting;

import java.util.List;
import java.util.stream.Collectors;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import sguest.millenairejei.jei.DrawableWithLabel;
import sguest.millenairejei.recipes.villagercrafting.VillagerCraftingRecipeData;
import sguest.millenairejei.util.UiHelper;

public class VillagerCrafingRecipeWrapper implements IRecipeWrapper {
    private final VillagerCraftingRecipeData recipeEntry;
    private final DrawableWithLabel culture;
    private final List<DrawableWithLabel> villagers;

    public VillagerCrafingRecipeWrapper(VillagerCraftingRecipeData recipeEntry, IGuiHelper guiHelper) {
        this.recipeEntry = recipeEntry;
        this.culture = UiHelper.toDrawable(recipeEntry.getCulture(), guiHelper);
        this.villagers = recipeEntry.getVillagers().stream().map(v -> UiHelper.toDrawable(v, guiHelper)).collect(Collectors.toList());
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInputs(VanillaTypes.ITEM, recipeEntry.getInputs());
        ingredients.setOutputs(VanillaTypes.ITEM, recipeEntry.getOutputs());
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        UiHelper.renderIconWithLabel(minecraft, 1, 1, culture);

        for(int i = 0; i < villagers.size(); i++) {
            UiHelper.renderIconWithLabel(minecraft, 1, 40 + i * 20, villagers.get(i));
        }
    }

    public VillagerCraftingRecipeData getRecipe() {
        return recipeEntry;
    }
}
