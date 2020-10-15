package sguest.millenairejei.millenairedata;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.DataFileHelper;

public class VillageLookup {
    private static VillageLookup instance;

    private Map<String, Map<String, VillageData>> cultureData;

    public static VillageLookup getInstance() {
        if(instance == null) {
            instance = new VillageLookup();
        }
        return instance;
    }

    private VillageLookup() {
        cultureData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void LoadVillages(String cultureKey, Path cultureFolder) {
        Path villagesFolder = cultureFolder.resolve("villages");
        if(villagesFolder.toFile().exists()) {
            Map<String, VillageData> cultureVillages = cultureData.get(cultureKey);
            if(cultureVillages == null) {
                cultureVillages = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                cultureData.put(cultureKey, cultureVillages);
            }
            final Map<String, VillageData> villageMap = cultureVillages;
            try {
                Files.walk(villagesFolder).filter(Files::isRegularFile).forEach(file -> {
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        List<String> iconValues = fileData.get("icon");
                        String icon = iconValues == null ? null : iconValues.get(0);
                        List<String> buildings = new ArrayList<>();
                        for(String key : new String[] { "centre", "start", "core", "secondary"}) {
                            if(fileData.containsKey(key)) {
                                buildings.addAll(fileData.get(key));
                            }
                        }
                        Map<String, Integer> buyingPrices = getPrices("buyingPrice", fileData);
                        Map<String, Integer> sellingPrices = getPrices("sellingPrice", fileData);

                        String villageKey = FilenameUtils.getBaseName(file.toFile().getName());
                        villageMap.put(villageKey, new VillageData(icon, buildings, buyingPrices, sellingPrices));
                    }
                });
            } catch(IOException ex) {
                MillenaireJei.getLogger().error("Failed to load villages for culture " + cultureKey, ex);
            }
        }
    }

    public Map<String, VillageData> getVillages(String cultureKey) {
        return cultureData.get(cultureKey);
    }

    private Map<String, Integer> getPrices(String key, Map<String, List<String>> fileData) {
        Map<String, Integer> prices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        if(fileData.containsKey(key)) {
            for(String dataItem : fileData.get(key)) {
                String[] parts = dataItem.split(",", 2);
                if(parts.length == 2) {
                    String item = parts[0];
                    String[] priceParts = parts[1].split("/", 3);
                    int price = 0;
                    if(priceParts.length == 3) {
                        price = Integer.parseInt(priceParts[0]) * 64 * 64 + Integer.parseInt(priceParts[1]) * 64 + Integer.parseInt(priceParts[2]);
                    }
                    else if(priceParts.length == 2) {
                        price = Integer.parseInt(priceParts[0]) * 64 + Integer.parseInt(priceParts[1]);
                    }
                    else {
                        price = Integer.parseInt(priceParts[0]);
                    }
                    prices.put(item, price);
                }
            }
        }
        return prices;
    }
}
