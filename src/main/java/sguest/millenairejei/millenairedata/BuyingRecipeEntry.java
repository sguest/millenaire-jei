package sguest.millenairejei.millenairedata;

import net.minecraft.item.ItemStack;

public class BuyingRecipeEntry {
    private ItemStack buyingItem;
    private int cost;
    private String shopName;

    public BuyingRecipeEntry(ItemStack buyingItem, int cost, String shopName) {
        this.buyingItem = buyingItem;
        this.cost = cost;
        this.shopName = shopName;
    }

    public ItemStack GetBuyingItem() {
        return buyingItem;
    }

    public int getCost() {
        return cost;
    }

    public String getShopName() {
        return shopName;
    }
}