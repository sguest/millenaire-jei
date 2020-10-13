package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.DataFileHelper;

public class ShopBuildingLookup {
    private static ShopBuildingLookup instance;

    private Map<String, Map<String, List<BuildingData>>> shopsByCulture;

    public static ShopBuildingLookup getInstance() {
        if(instance == null) {
            instance = new ShopBuildingLookup();
        }
        return instance;
    }

    private ShopBuildingLookup() {
        shopsByCulture = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadData(String cultureKey, Path cultureFolder) {
        Map<String, List<BuildingData>> cultureShops = shopsByCulture.get(cultureKey);
        if(cultureShops == null) {
            cultureShops = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            shopsByCulture.put(cultureKey, cultureShops);
        }
        loadDataRecursive(cultureShops, cultureFolder.resolve("buildings"), 0);
        loadDataRecursive(cultureShops, cultureFolder.resolve("lonebuildings"), 0);
    }

    public List<BuildingData> getShopBuildings(String cultureKey, String shopKey) {
        Map<String, List<BuildingData>> byCulture = shopsByCulture.get(cultureKey);
        if(byCulture == null) {
            return null;
        }
        return byCulture.get(shopKey);
    }

    private void loadDataRecursive(Map<String, List<BuildingData>> cultureShops, Path loadingFolder, int depth) {
        if(depth >= 20) {
            // Something has gone wrong, no one likes infinite recursion oopsies
            MillenaireJei.getLogger().warn("Excessive recursion detected loading building data at path " + loadingFolder);
            return;
        }

        if(loadingFolder.toFile().exists()) {
            File[] buildingFiles = loadingFolder.toFile().listFiles();
            for (File buildingFile : buildingFiles) {
                if(buildingFile.isDirectory()) {
                    loadDataRecursive(cultureShops, buildingFile.toPath(), depth + 1);
                }
                else if(FilenameUtils.getExtension(buildingFile.getName()).equals("txt")) {
                    Map<String, String> fileData = DataFileHelper.loadDataFile(buildingFile);
                    if(fileData != null) {
                        for(Map.Entry<String, String> entry : fileData.entrySet()) {
                            if(entry.getKey().endsWith(".shop")) {
                                String buildingKey = FilenameUtils.getBaseName(buildingFile.getName());
                                String shopKey = entry.getValue();
                                List<BuildingData> buildings = cultureShops.get(shopKey);
                                if(buildings == null) {
                                    buildings = new ArrayList<>();
                                    cultureShops.put(shopKey, buildings);
                                }
                                buildings.add(new BuildingData(buildingKey, fileData.get("building.icon")));
                            }
                        }
                    }
                }
            }
        }
    }
}
