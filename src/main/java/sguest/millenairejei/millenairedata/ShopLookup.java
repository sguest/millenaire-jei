package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import sguest.millenairejei.util.DataFileHelper;

public class ShopLookup {
    private static ShopLookup instance;

    private Map<String, Map<String, ShopData>> cultureShopInfo;

    public static ShopLookup getInstance() {
        if(instance == null) {
            instance = new ShopLookup();
        }
        return instance;
    }

    private ShopLookup() {
        cultureShopInfo = new TreeMap<>();
    }

    public void loadShopInfo(String cultureKey, Path cultureFolder) {
        Path shopsFolder = cultureFolder.resolve("shops");
        
        if(shopsFolder.toFile().exists()) {
            Map<String, ShopData> cultureShops = cultureShopInfo.get(cultureKey);
            if(cultureShops == null) {
                cultureShops = new TreeMap<>();
                cultureShopInfo.put(cultureKey, cultureShops);
            }
            
            File[] shopFiles = shopsFolder.toFile().listFiles();
            for (File shopFile : shopFiles) {
                String shopKey = FilenameUtils.getBaseName(shopFile.getName());
                Map<String, String> shopFileData = DataFileHelper.loadDataFile(shopFile);
                if(shopFileData != null) {
                    ShopData shopData = new ShopData();

                    for(Map.Entry<String, String> entry : shopFileData.entrySet()) {
                        String transactionType = entry.getKey();
                        String[] items = entry.getValue().split(",");
                        if(transactionType.equals("sells")) {
                            Collections.addAll(shopData.getSoldItems(), items);
                        }
                        else if(transactionType.equals("buys") || transactionType.equals("buysoptional")) {
                            Collections.addAll(shopData.getBoughtItems(), items);
                        }
                    }

                    cultureShops.put(shopKey, shopData);
                }
            }
        }
    }

    public Map<String, ShopData> getCultureShops(String cultureKey) {
        return cultureShopInfo.get(cultureKey);
    }
}
