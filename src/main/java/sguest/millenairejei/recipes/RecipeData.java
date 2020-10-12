package sguest.millenairejei.recipes;

import java.util.List;

import net.minecraft.item.ItemStack;

public class RecipeData {
    private ItemStack tradeItem;
    private int cost;
    private String cultureName;
    private ItemStack cultureIcon;
    private List<String> shopNames;

    public RecipeData(ItemStack tradeItem, int cost, String cultureName, ItemStack cultureIcon, List<String> shopNames) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.cultureName = cultureName;
        this.cultureIcon = cultureIcon;
        this.shopNames = shopNames;
    }

    public ItemStack getTradeItem() {
        return tradeItem;
    }

    public int getCost() {
        return cost;
    }

    public String getCultureName() {
        return cultureName;
    }

    public ItemStack getCultureIcon() {
        return cultureIcon;
    }

    public List<String> getShopNames() {
        return shopNames;
    }
}