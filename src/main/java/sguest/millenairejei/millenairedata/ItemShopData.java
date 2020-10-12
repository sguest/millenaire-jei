package sguest.millenairejei.millenairedata;

import java.util.ArrayList;
import java.util.List;

public class ItemShopData {
    private List<String> sellingShops;
    private List<String> buyingShops;

    public ItemShopData() {
        sellingShops = new ArrayList<>();
        buyingShops = new ArrayList<>();
    }

    public List<String> getSellingShops() {
        return sellingShops;
    }

    public List<String> getBuyingShops() {
        return buyingShops;
    }

    public void addSellingShop(String shop) {
        if(!sellingShops.contains(shop)) {
            sellingShops.add(shop);
        }
    }

    public void addBuyingShop(String shop) {
        if(!buyingShops.contains(shop)) {
            buyingShops.add(shop);
        }
    }
}
