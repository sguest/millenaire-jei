package sguest.millenairejei.jei;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.RecipeData;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.DenierHelper;
import sguest.millenairejei.util.ItemHelper;

public class SellingRecipeWrapper implements IRecipeWrapper {
    private final RecipeData recipeEntry;

    public SellingRecipeWrapper(RecipeData recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipeEntry.GetTradeItem());
        List<ItemStack> outputs = new ArrayList<ItemStack>();
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_POUCH));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_ARGENT));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_OR));
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    public ItemStack getSellingItem() {
        return recipeEntry.GetTradeItem();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.fontRenderer.drawString(recipeEntry.getShopName(), 1, 1, 0xFFFFFFFF);
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 40, 21);
    }
}