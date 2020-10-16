package sguest.millenairejei.millenairedata.trading;

import java.util.List;
import java.util.Map;

public class VillageData {
    private final String icon;
    private final List<String> buildings;
    private final Map<String, Integer> buyingPrices;
    private final Map<String, Integer> sellingPrices;

    public VillageData(String icon, List<String> buildings, Map<String, Integer> buyingPrices, Map<String, Integer> sellingPrices) {
        this.icon = icon;
        this.buildings = buildings;
        this.buyingPrices = buyingPrices;
        this.sellingPrices = sellingPrices;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getBuildings() {
        return buildings;
    }

    public Map<String, Integer> getBuyingPrices() {
        return buyingPrices;
    }

    public Map<String, Integer> getSellingPrices() {
        return sellingPrices;
    }
}
