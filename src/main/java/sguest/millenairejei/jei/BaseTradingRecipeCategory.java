package sguest.millenairejei.jei;

import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.gui.ITooltipCallback;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.GuiElementHelper;
import sguest.millenairejei.util.ItemHelper;

public abstract class BaseTradingRecipeCategory<T extends BaseTradingRecipeWrapper>
        implements IRecipeCategory<T>, ITooltipCallback<ItemStack> {

    private final IDrawable background;
    private final String title;
    private final IDrawable icon;

    protected final IDrawable arrow;

    protected BaseTradingRecipeCategory(IGuiHelper guiHelper, String title, String icon) {
        background = guiHelper.createBlankDrawable(180, 110);
        this.title = title;
        this.icon = guiHelper.createDrawableIngredient(ItemHelper.getStackFromResource(icon));
        arrow = GuiElementHelper.staticArrow(guiHelper);
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, T recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(2, false, 1, -2);
        recipeLayout.getItemStacks().set(2, recipeWrapper.getRecipeEntry().getCultureIcon());
        recipeLayout.getItemStacks().addTooltipCallback(this);
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public String getModName() {
        return Constants.RECIPE_MOD_DISPLAY;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
        if(slotIndex >= 2) {
            tooltip.clear();
        }
    }
}
