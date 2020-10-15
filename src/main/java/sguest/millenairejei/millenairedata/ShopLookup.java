package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import sguest.millenairejei.util.DataFileHelper;

public class ShopLookup {
    private static ShopLookup instance;

    private Map<String, Map<String, ItemShopData>> cultureShopInfo;

    public static ShopLookup getInstance() {
        if(instance == null) {
            instance = new ShopLookup();
        }
        return instance;
    }

    private ShopLookup() {
        cultureShopInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadShopInfo(String cultureKey, Path cultureFolder) {
        Path shopsFolder = cultureFolder.resolve("shops");
        
        if(shopsFolder.toFile().exists()) {
            Map<String, ItemShopData> cultureShops = cultureShopInfo.get(cultureKey);
            if(cultureShops == null) {
                cultureShops = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                cultureShopInfo.put(cultureKey, cultureShops);
            }
            
            File[] shopFiles = shopsFolder.toFile().listFiles();
            for (File shopFile : shopFiles) {
                String shopKey = FilenameUtils.getBaseName(shopFile.getName());
                Map<String, List<String>> shopFileData = DataFileHelper.loadDataFile(shopFile);
                if(shopFileData != null) {
                    for(Map.Entry<String, List<String>> entry : shopFileData.entrySet()) {
                        String transactionType = entry.getKey();
                        String[] items = entry.getValue().get(0).split(",");
                        for(String item: items) {
                            if(transactionType.equals("sells")) {
                                getItemEntry(cultureShops, item).addSellingShop(shopKey);
                            }
                            else if(transactionType.equals("buys") || transactionType.equals("buysoptional")) {
                                getItemEntry(cultureShops, item).addBuyingShop(shopKey);
                            }
                        }
                    }
                }
            }
        }
    }

    public Map<String, ItemShopData> getCultureShops(String cultureKey) {
        return cultureShopInfo.get(cultureKey);
    }

    private ItemShopData getItemEntry(Map<String, ItemShopData> cultureShops, String item) {
        ItemShopData itemShopData = cultureShops.get(item);
        if(itemShopData == null) {
            itemShopData = new ItemShopData();
            cultureShops.put(item, itemShopData);
        }
        return itemShopData;
    }
}
