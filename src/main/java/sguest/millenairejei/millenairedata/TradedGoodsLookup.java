package sguest.millenairejei.millenairedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.TreeMap;

import sguest.millenairejei.MillenaireJei;

public class TradedGoodsLookup {
    private static TradedGoodsLookup instance;

    private Map<String, CultureTradedGoods> cultureData;

    public static TradedGoodsLookup getInstance() {
        if(instance == null) {
            instance = new TradedGoodsLookup();
        }
        return instance;
    }

    private TradedGoodsLookup() {
        cultureData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadTradedGoods(String cultureKey, Path cultureFolder) {
        File goodsFile = cultureFolder.resolve("traded_goods.txt").toFile();
        if(goodsFile.exists()) {
            CultureTradedGoods cultureGoods = cultureData.get(cultureKey);
            if(cultureGoods == null) {
                cultureGoods = new CultureTradedGoods();
                cultureData.put(cultureKey, cultureGoods);
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(goodsFile))) {
                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.length() > 0 && !line.startsWith("//")) {
                        String[] values = line.split(",");
                        if(values.length >= 3) {
                            String itemKey = values[0];
                            int sellingPrice = parsePrice(values[1]);
                            int buyingPrice = parsePrice(values[2]);

                            if(sellingPrice > 0) {
                                cultureGoods.setSellingPrice(itemKey, sellingPrice);
                            }

                            if(buyingPrice > 0) {
                                cultureGoods.setBuyingPrice(itemKey, buyingPrice);
                            }
                        }
                    }
                }
            } catch(IOException ex) {
                MillenaireJei.getLogger().warn("Could not load trading information for culture " + cultureKey + " from file " + goodsFile);
            }
        }
    }

    public CultureTradedGoods getCulture(String cultureKey) {
        return cultureData.get(cultureKey);
    }

    private int parsePrice(String priceString) {
        priceString = priceString.trim();
        if(priceString.length() == 0) {
            return 0;
        }
        if(priceString.contains("*")) {
            String[] priceParts = priceString.split("\\*");
            int value = 1;
            for(String part : priceParts) {
                value *= Integer.parseInt(part);
            }
            return value;
        }
        return Integer.parseInt(priceString);
    }
}
