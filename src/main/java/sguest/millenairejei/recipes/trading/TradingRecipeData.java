package sguest.millenairejei.recipes.trading;

import java.util.List;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.IconWithLabel;

public class TradingRecipeData {
    private final ItemStack tradeItem;
    private final int cost;
    private final IconWithLabel culture;
    private final IconWithLabel village;
    private final List<IconWithLabel> buildings;

    public TradingRecipeData(ItemStack tradeItem, int cost, IconWithLabel culture, IconWithLabel village, List<IconWithLabel> buildings) {
        this.tradeItem = tradeItem;
        this.cost = cost;
        this.culture = culture;
        this.village = village;
        this.buildings = buildings;
    }

    public ItemStack getTradeItem() {
        return tradeItem;
    }

    public int getCost() {
        return cost;
    }

    public IconWithLabel getCulture() {
        return culture;
    }

    public IconWithLabel getVillage() {
        return village;
    }

    public List<IconWithLabel> getBuildings() {
        return buildings;
    }
}