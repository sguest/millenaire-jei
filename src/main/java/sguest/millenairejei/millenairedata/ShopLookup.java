package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
        cultureShopInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadShopInfo(String cultureKey, Path cultureFolder) {
        Path shopsFolder = cultureFolder.resolve("shops");
        
        if(shopsFolder.toFile().exists()) {
            Map<String, ShopData> cultureShops = cultureShopInfo.get(cultureKey);
            if(cultureShops == null) {
                cultureShops = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                cultureShopInfo.put(cultureKey, cultureShops);
            }
            
            File[] shopFiles = shopsFolder.toFile().listFiles();
            for (File shopFile : shopFiles) {
                String shopKey = FilenameUtils.getBaseName(shopFile.getName());
                Map<String, List<String>> shopFileData = DataFileHelper.loadDataFile(shopFile);
                if(shopFileData != null) {
                    List<String> soldItems = getItemsFromFile(shopFileData, "sells");
                    List<String> boughtItems = getItemsFromFile(shopFileData, "buys");
                    boughtItems.addAll(getItemsFromFile(shopFileData, "buysoptional"));
                    cultureShops.put(shopKey, new ShopData(soldItems, boughtItems));
                }
            }
        }
    }

    public Map<String, ShopData> getCultureShops(String cultureKey) {
        return cultureShopInfo.get(cultureKey);
    }

    private List<String> getItemsFromFile(Map<String, List<String>> fileData, String key) {
        List<String> items = new ArrayList<>();
        List<String> fileLines = fileData.get(key);
        if(fileLines != null) {
            for(String line : fileLines) {
                Collections.addAll(items, line.split(","));
            }
        }

        return items;
    }
}
