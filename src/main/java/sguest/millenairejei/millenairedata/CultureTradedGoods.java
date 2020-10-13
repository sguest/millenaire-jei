package sguest.millenairejei.millenairedata;

import java.util.Map;
import java.util.TreeMap;

public class CultureTradedGoods {
    private final Map<String, Integer> buyingPrices;
    private final Map<String, Integer> sellingPrices;

    public CultureTradedGoods() {
        buyingPrices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        sellingPrices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void setBuyingPrice(String key, int price) {
        buyingPrices.put(key, price);
    }

    public void setSellingPrice(String key, int price) {
        sellingPrices.put(key, price);
    }

    public int getBuyingPrice(String key) {
        return buyingPrices.containsKey(key) ? buyingPrices.get(key) : 0;
    }

    public int getSellingPrice(String key) {
        return sellingPrices.containsKey(key) ? sellingPrices.get(key) : 0;
    }
}
