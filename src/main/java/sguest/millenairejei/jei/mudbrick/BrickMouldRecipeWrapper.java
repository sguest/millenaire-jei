package sguest.millenairejei.jei.mudbrick;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.ItemHelper;

public class BrickMouldRecipeWrapper implements IRecipeWrapper {

    @Override
    public void getIngredients(IIngredients ingredients) {
        List<ItemStack> inputs = new ArrayList<>();
        inputs.add(new ItemStack(Item.getItemFromBlock(Blocks.DIRT)));
        inputs.add(new ItemStack(Item.getItemFromBlock(Blocks.SAND)));
        ingredients.setInputs(VanillaTypes.ITEM, inputs);

        ItemStack output = ItemHelper.getStackFromResource(Constants.WET_BRICK);
        output.setCount(4);
        ingredients.setOutput(VanillaTypes.ITEM, output);
    }
}
