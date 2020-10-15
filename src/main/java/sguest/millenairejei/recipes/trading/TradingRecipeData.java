package sguest.millenairejei.recipes.trading;

import java.util.List;

import net.minecraft.item.ItemStack;

public class TradingRecipeData {
    private final ItemStack tradeItem;
    private final int cost;
    private final String cultureName;
    private final ItemStack cultureIcon;
    private final String villageName;
    private final ItemStack villageIcon;
    private final List<RecipeBuildingData> buildings;

    public TradingRecipeData(ItemStack tradeItem, int cost, String cultureName, ItemStack cultureIcon, String villageName, ItemStack villageIcon, List<RecipeBuildingData> buildings) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.cultureName = cultureName;
        this.cultureIcon = cultureIcon;
        this.villageName = villageName;
        this.villageIcon = villageIcon;
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

    public String getVillageName() {
        return villageName;
    }

    public ItemStack getVillageIcon() {
        return villageIcon;
    }

    public List<RecipeBuildingData> getBuildings() {
        return buildings;
    }
}