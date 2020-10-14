package sguest.millenairejei.recipes.trading;

import java.util.List;

import net.minecraft.item.ItemStack;

public class TradingRecipeData {
    private ItemStack tradeItem;
    private int cost;
    private String cultureName;
    private ItemStack cultureIcon;
    private List<RecipeBuildingData> buildings;

    public TradingRecipeData(ItemStack tradeItem, int cost, String cultureName, ItemStack cultureIcon, List<RecipeBuildingData> buildings) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.cultureName = cultureName;
        this.cultureIcon = cultureIcon;
        this.buildings = buildings;
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

    public List<RecipeBuildingData> getBuildings() {
        return buildings;
    }
}