package sguest.millenairejei.millenairedata;

import java.util.List;

public class ShopData {
    private final List<String> soldItems;
    private final List<String> boughtItems;

    public ShopData(List<String> soldItems, List<String> boughtItems) {
        this.soldItems = soldItems;
        this.boughtItems = boughtItems;
    }

    public List<String> getSoldItems() {
        return soldItems;
    }

    public List<String> getBoughtItems() {
        return boughtItems;
    }
}
