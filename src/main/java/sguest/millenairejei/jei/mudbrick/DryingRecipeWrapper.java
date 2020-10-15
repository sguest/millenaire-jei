package sguest.millenairejei.jei.mudbrick;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.ItemHelper;

public class DryingRecipeWrapper implements IRecipeWrapper {

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, ItemHelper.getStackFromResource(Constants.WET_BRICK));
        ingredients.setOutput(VanillaTypes.ITEM, ItemHelper.getStackFromResource(Constants.MUD_BRICK));
    }
}
