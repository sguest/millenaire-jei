package sguest.millenairejei.millenairedata.trading;

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
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.DataFileHelper;

public class BuildingLookup {
    private static BuildingLookup instance;

    private Map<String, Map<String, BuildingData>> cultureBuildingData;

    public static BuildingLookup getInstance() {
        if(instance == null) {
            instance = new BuildingLookup();
        }
        return instance;
    }

    private BuildingLookup() {
        cultureBuildingData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadData(String cultureKey, Path cultureFolder) {
        Map<String, BuildingData> cultureBuildings = cultureBuildingData.get(cultureKey);
        if(cultureBuildings == null) {
            cultureBuildings = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
            cultureBuildingData.put(cultureKey, cultureBuildings);
        }
        loadDataFolder(cultureBuildings, cultureFolder.resolve("buildings"));
        loadDataFolder(cultureBuildings, cultureFolder.resolve("lonebuildings"));
    }

    public Map<String, BuildingData> getCultureBuildings(String cultureKey) {
        return cultureBuildingData.get(cultureKey);
    }

    private void loadDataFolder(Map<String, BuildingData> cultureBuildings, Path loadingFolder) {
        if(loadingFolder.toFile().exists()) {
            try {
                Files.walk(loadingFolder)
                .filter(Files::isRegularFile)
                .filter(file -> FilenameUtils.getExtension(file.toFile().getName()).equals("txt"))
                .forEach(file -> {
                    File buildingFile = file.toFile();
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(buildingFile);
                    if(fileData != null && fileData.size() == 0) {
                        fileData = loadSemicolonFormat(buildingFile);
                    }
                    if(fileData != null) {
                        String buildingKey = FilenameUtils.getBaseName(buildingFile.getName());
                        buildingKey = buildingKey.replaceAll(Constants.BUILDING_CLEAN_REGEX, "");
                        List<String> shops = new ArrayList<>();
                        List<String> subBuildings = new ArrayList<>();
                        String icon = null;
                        for(Map.Entry<String, List<String>> entry : fileData.entrySet()) {
                            if(entry.getKey().equals("shop") || entry.getKey().endsWith(".shop")) {
                                shops.addAll(entry.getValue());
                            }
                            else if(entry.getKey().equals("subbuilding") || entry.getKey().endsWith(".subbuilding")) {
                                subBuildings.addAll(entry.getValue());
                            }
                            else if(entry.getKey().equals("icon") || entry.getKey().endsWith(".icon")) {
                                icon = entry.getValue().get(0);
                            }
                        }

                        cultureBuildings.put(buildingKey, new BuildingData(shops, subBuildings, icon));
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
