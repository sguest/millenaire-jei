package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sguest.millenairejei.MillenaireJei;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.gui.IRecipeLayout;

public class BuyingRecipeCategory implements IRecipeCategory<BuyingRecipeWrapper> {
    private final IDrawable background;
    private final IGuiHelper guiHelper;

    public BuyingRecipeCategory(IGuiHelper guiHelper) {
        this.guiHelper = guiHelper;
        background = guiHelper.createBlankDrawable(180, 50);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, BuyingRecipeWrapper recipeWrapper, IIngredients ingredients){
        recipeLayout.getItemStacks().init(0, true, 1, 15);
        recipeLayout.getItemStacks().set(0, recipeWrapper.getBuyingItem());
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return "Millenaire";
    }

    @Override
    public String getTitle() {
        return I18n.format("millenairejei.buyingrecipes.tabtitle");
    }

    @Override
    public String getUid() {
        return MillenaireJei.MODID + ".buying";
    }

    @Override
    public IDrawable getIcon() {
        return guiHelper.createDrawableIngredient(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("millenaire:denierargent"))));
    }
}