package sguest.millenairejei.jei;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.ingredients.VanillaTypes;
import mezz.jei.api.recipe.IRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.BuyingRecipeEntry;
import sguest.millenairejei.util.DenierHelper;

public class BuyingRecipeWrapper implements IRecipeWrapper {
    private final BuyingRecipeEntry recipeEntry;

    public BuyingRecipeWrapper(BuyingRecipeEntry recipeEntry) {
        this.recipeEntry = recipeEntry;
    }

    @Override
    public void getIngredients(IIngredients ingredients) {
        //ingredients.setInput(VanillaTypes.ITEM, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minecraft:cobblestone"))));
        ingredients.setOutput(VanillaTypes.ITEM, recipeEntry.GetBuyingItem());
    }

    public ItemStack getBuyingItem() {
        return recipeEntry.GetBuyingItem();
    }

    @Override
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        DenierHelper.drawPrice(minecraft, recipeEntry.getCost(), 22, 10);
    }
}