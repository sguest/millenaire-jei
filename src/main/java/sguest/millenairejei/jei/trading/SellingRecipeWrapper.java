package sguest.millenairejei.jei.trading;

import java.util.ArrayList;
import java.util.List;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.trading.TradingRecipeData;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.DenierHelper;
import sguest.millenairejei.util.ItemHelper;

public class SellingRecipeWrapper extends BaseTradingRecipeWrapper {
    public SellingRecipeWrapper(TradingRecipeData recipeEntry, IGuiHelper guiHelper) {
        super(recipeEntry, guiHelper);
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        ingredients.setInput(VanillaTypes.ITEM, recipeEntry.getTradeItem());
        List<ItemStack> outputs = new ArrayList<ItemStack>();
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_POUCH));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_ARGENT));
        outputs.add(ItemHelper.getStackFromResource(Constants.DENIER_OR));
        ingredients.setOutputs(VanillaTypes.ITEM, outputs);
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        super.drawInfo(minecraft, recipeWidth, recipeHeight, mouseX, mouseY);
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 65, 41);
    }
}