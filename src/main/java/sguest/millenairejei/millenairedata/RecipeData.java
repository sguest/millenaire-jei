package sguest.millenairejei.millenairedata;

import net.minecraft.item.ItemStack;

public class RecipeData {
    private ItemStack tradeItem;
    private int cost;
    private String shopName;

    public RecipeData(ItemStack tradeItem, int cost, String shopName) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.shopName = shopName;
    }

    public ItemStack GetTradeItem() {
        return tradeItem;
    }

    public int getCost() {
        return cost;
    }

    public String getShopName() {
        return shopName;
    }
}