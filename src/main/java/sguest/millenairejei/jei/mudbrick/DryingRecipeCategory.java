package sguest.millenairejei.jei.mudbrick;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import sguest.millenairejei.jei.MillenaireJeiPlugin;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.UiHelper;
import sguest.millenairejei.util.ItemHelper;

public class DryingRecipeCategory implements IRecipeCategory<DryingRecipeWrapper> {
    private final String title;
    private final IDrawable background;
    private final IDrawable sun;
    private final IDrawable staticArrow;
    private final IDrawable animatedArrow;

    public DryingRecipeCategory(IGuiHelper guiHelper) {
        title = I18n.format("millenairejei.dryingrecipes.tabtitle");
        background = guiHelper.createBlankDrawable(100, 50);
        sun = guiHelper.drawableBuilder(new ResourceLocation(Constants.SUN_TEXTURE), 8, 8, 16, 16)
            .setTextureSize(32, 32)
            .trim(4, 4, 4, 4)
            .build();
        staticArrow = UiHelper.staticArrow(guiHelper);
        animatedArrow = UiHelper.animatedArrow(guiHelper, 200);
    }
    @Override
    public String getUid() {
        return MillenaireJeiPlugin.DRYING;
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
    public IDrawable getIcon() {
        return sun;
    }

    @Override
    public void setRecipe(IRecipeLayout recipeLayout, DryingRecipeWrapper recipeWrapper, IIngredients ingredients) {
        recipeLayout.getItemStacks().init(0, true, 5, 30);
        recipeLayout.getItemStacks().set(0, ItemHelper.getStackFromResource(Constants.WET_BRICK));

        recipeLayout.getItemStacks().init(1, false, 79, 30);
        recipeLayout.getItemStacks().set(1, ItemHelper.getStackFromResource(Constants.MUD_BRICK));
    }

    @Override
    public void drawExtras(Minecraft minecraft) {
        staticArrow.draw(minecraft, 39, 30);
        animatedArrow.draw(minecraft, 39, 30);
        sun.draw(minecraft, 45, 5);
    }
}
