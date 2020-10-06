package sguest.millenairejei.millenairedata;

import net.minecraft.item.ItemStack;

public class BuyingRecipeEntry {
    private ItemStack buyingItem;
    private int cost;

    public BuyingRecipeEntry(ItemStack buyingItem, int cost) {
        this.buyingItem = buyingItem;
        this.cost = cost;
    }

    public ItemStack GetBuyingItem() {
        return this.buyingItem;
    }

    public int getCost() {
        return this.cost;
    }
}