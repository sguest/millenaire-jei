package sguest.millenairejei.millenairedata;

import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeData {
    private ItemStack tradeItem;
    private int cost;
    private String cultureName;
    private List<String> shopNames;

    public RecipeData(ItemStack tradeItem, int cost, String cultureName, List<String> shopNames) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.cultureName = cultureName;
        this.shopNames = shopNames;
    }

    public ItemStack GetTradeItem() {
        return tradeItem;
    }

    public int getCost() {
        return cost;
    }

    public String getCultureName() {
        return cultureName;
    }

    public List<String> getShopNames() {
        return shopNames;
    }
}