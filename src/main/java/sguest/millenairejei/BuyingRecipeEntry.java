package sguest.millenairejei;

import net.minecraft.item.ItemStack;

public class BuyingRecipeEntry {
    private ItemStack buyingItem;

    public BuyingRecipeEntry(ItemStack buyingItem) {
        this.buyingItem = buyingItem;
    }

    public ItemStack GetBuyingItem() {
        return this.buyingItem;
    }
}