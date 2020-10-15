package sguest.millenairejei.millenairedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
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
        loadData(cultureShops, cultureFolder.resolve("buildings"));
        loadData(cultureShops, cultureFolder.resolve("lonebuildings"));
    }

    public List<BuildingData> getShopBuildings(String cultureKey, String shopKey) {
        Map<String, List<BuildingData>> byCulture = shopsByCulture.get(cultureKey);
        if(byCulture == null) {
            return null;
        }
        return byCulture.get(shopKey);
    }

    private void loadData(Map<String, List<BuildingData>> cultureShops, Path loadingFolder) {
        if(loadingFolder.toFile().exists()) {
            try {
                Files.walk(loadingFolder).filter(Files::isRegularFile).forEach(file -> {
                    File buildingFile = file.toFile();
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(buildingFile);
                    if(fileData != null && fileData.size() == 0) {
                        fileData = loadSemicolonFormat(buildingFile);
                    }
                    if(fileData != null) {
                        for(Map.Entry<String, List<String>> entry : fileData.entrySet()) {
                            if(entry.getKey().equals("shop") || entry.getKey().endsWith(".shop")) {
                                String buildingKey = FilenameUtils.getBaseName(buildingFile.getName());
                                String shopKey = entry.getValue().get(0);
                                List<BuildingData> buildings = cultureShops.get(shopKey);
                                if(buildings == null) {
                                    buildings = new ArrayList<>();
                                    cultureShops.put(shopKey, buildings);
                                }
                                List<String> icon = fileData.get("building.icon");
                                if(icon == null) {
                                    icon = fileData.get("icon");
                                }
                                buildings.add(new BuildingData(buildingKey, icon == null ? null : icon.get(0)));
                            }
                        }
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load shops from folder " + loadingFolder, ex);
            }
        }
    }

    private Map<String, List<String>> loadSemicolonFormat(File buildingFile) {
        Map<String, List<String>> fileData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        try (BufferedReader reader = new BufferedReader(new FileReader(buildingFile))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.length() > 0 && !line.startsWith("//")) {
                    String[] params = line.split(";");
                    for(String param : params) {
                        String[] parts = param.split(":", 2);
                        if(parts.length == 2) {
                            List<String> dataItems = fileData.get(parts[0]);
                            if(dataItems == null) {
                                dataItems = new ArrayList<>();
                                fileData.put(parts[0], dataItems);
                            }
                            dataItems.add(parts[1]);
                        }
                    }
                }
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().error("Failed to load data file " + buildingFile, ex);
            return null;
        }
        return fileData;
    }
}
