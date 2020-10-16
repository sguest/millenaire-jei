package sguest.millenairejei.millenairedata.trading;

import java.util.List;

public class BuildingData {
    private List<String> shops;
    private List<String> subBuildings;
    private String icon;

    public BuildingData(List<String> shops, List<String> subBuildings, String icon) {
        this.shops = shops;
        this.subBuildings = subBuildings;
        this.icon = icon;
    }

    public List<String> getShops() {
        return shops;
    }

    public List<String> getSubBuildings() {
        return subBuildings;
    }

    public String getIcon() {
        return icon;
    }
}
