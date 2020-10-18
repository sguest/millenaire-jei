package sguest.millenairejei.jei.villagercrafting;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.jei.MillenaireJeiPlugin;
import sguest.millenairejei.util.Constants;

public class VillagerSlaughteringRecipeCategory implements IRecipeCategory<VillagerSlaughteringRecipeWrapper> {
    private final IDrawable background;
    private final String title;

    public VillagerSlaughteringRecipeCategory(IGuiHelper guiHelper) {
        title = I18n.format("millenairejei.villagerslaughtering.tabtitle");
        background = guiHelper.createBlankDrawable(180, 120);
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.VILLAGER_SLAUGHTERING;
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
    public void setRecipe(IRecipeLayout recipeLayout, VillagerSlaughteringRecipeWrapper recipeWrapper, IIngredients ingredients) {
        List<ItemStack> outputs = recipeWrapper.getRecipe().getOutputs();

        for(int i = 0; i < outputs.size(); i++) {
            recipeLayout.getItemStacks().init(i, false, 1 + i * 20, 38);
            recipeLayout.getItemStacks().set(i, outputs.get(i));
        }
    }
}