package sguest.millenairejei.jei.trading;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.recipe.IRecipeCategory;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.GuiElementHelper;
import sguest.millenairejei.util.ItemHelper;

public abstract class BaseTradingRecipeCategory<T extends BaseTradingRecipeWrapper> implements IRecipeCategory<T> {

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
}
