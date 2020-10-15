package sguest.millenairejei.jei.mudbrick;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.jei.MillenaireJeiPlugin;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.GuiElementHelper;
import sguest.millenairejei.util.ItemHelper;

public class BrickMouldRecipeCategory implements IRecipeCategory<BrickMouldRecipeWrapper> {
    private final String title;
    private final IDrawable background;
    private final IDrawable arrow;

    public BrickMouldRecipeCategory(IGuiHelper guiHelper) {
        title = I18n.format("millenairejei.brickmouldrecipes.tabtitle");
        background = guiHelper.createBlankDrawable(100, 50);
        arrow = GuiElementHelper.staticArrow(guiHelper);
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.BRICK_MOULD;
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
    public void setRecipe(IRecipeLayout recipeLayout, BrickMouldRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 5, 30);
        recipeLayout.getItemStacks().set(0, new ItemStack(Item.getItemFromBlock(Blocks.DIRT)));

        recipeLayout.getItemStacks().init(1, true, 20, 30);
        recipeLayout.getItemStacks().set(1, new ItemStack(Item.getItemFromBlock(Blocks.SAND)));

        recipeLayout.getItemStacks().init(2, false, 79, 30);
        ItemStack output = ItemHelper.getStackFromResource(Constants.WET_BRICK);
        output.setCount(4);
        recipeLayout.getItemStacks().set(2, output);

        recipeLayout.getItemStacks().init(3, true, 42, 5);
        recipeLayout.getItemStacks().set(3, ItemHelper.getStackFromResource(Constants.BRICK_MOULD));
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        arrow.draw(minecraft, 42, 30);
    }
}