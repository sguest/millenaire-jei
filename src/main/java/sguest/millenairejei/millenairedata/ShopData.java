package sguest.millenairejei.millenairedata;

import java.util.ArrayList;
import java.util.List;

public class ShopData {
    private List<String> soldItems;
    private List<String> boughtItems;

    public ShopData() {
        soldItems = new ArrayList<>();
        boughtItems = new ArrayList<>();
    }

    public List<String> getSoldItems() {
        return soldItems;
    }

    public List<String> getBoughtItems() {
        return boughtItems;
    }
}
