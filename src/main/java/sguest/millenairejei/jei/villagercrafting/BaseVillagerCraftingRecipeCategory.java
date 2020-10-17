package sguest.millenairejei.jei.villagercrafting;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.UiHelper;

public abstract class BaseVillagerCraftingRecipeCategory implements IRecipeCategory<VillagerCrafingRecipeWrapper> {
    private final IDrawable background;
    private final String title;
    private final IDrawable arrow;

    public BaseVillagerCraftingRecipeCategory(IGuiHelper guiHelper, String title) {
        this.title = title;
        background = guiHelper.createBlankDrawable(180, 120);
        arrow = UiHelper.staticArrow(guiHelper);
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getModName() {
        return Constants.RECIPE_MOD_DISPLAY;
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, VillagerCrafingRecipeWrapper recipeWrapper,
            IIngredients ingredients) {

        List<ItemStack> inputs = recipeWrapper.getRecipe().getInputs();
        List<ItemStack> outputs = recipeWrapper.getRecipe().getOutputs();

        for(int i = 0; i < inputs.size(); i++) {
            recipeLayout.getItemStacks().init(i, true, 1 + i * 20, 20);
            recipeLayout.getItemStacks().set(i, inputs.get(i));
        }

        for(int i = 0; i < outputs.size(); i++) {
            recipeLayout.getItemStacks().init(i + inputs.size(), false, 86 + i * 20, 20);
            recipeLayout.getItemStacks().set(i + inputs.size(), outputs.get(i));
        }
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 61, 20);
    }
}
