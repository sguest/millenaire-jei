package sguest.millenairejei.millenairedata.villagercrafting;

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

public class VillagerLookup {
    private static VillagerLookup instance;

    private Map<String, Map<String, VillagerData>> cultureVillagerData;

    public static VillagerLookup getInstance() {
        if (instance == null) {
            instance = new VillagerLookup();
        }
        return instance;
    }

    private VillagerLookup() {
        cultureVillagerData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadVillagers(String cultureKey, Path cultureFolder) {
        cultureVillagerData.putIfAbsent(cultureKey, new TreeMap<>(String.CASE_INSENSITIVE_ORDER));
        Path villagersFolder = cultureFolder.resolve("villagers");
        if(villagersFolder.toFile().exists()) {
            try {
                Files.walk(villagersFolder).filter(Files::isRegularFile).forEach(file -> {
                    String villagerKey = FilenameUtils.getBaseName(file.toFile().getName());
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        List<String> iconList = fileData.get("icon");
                        String icon = iconList == null ? null : iconList.get(0);
                        List<String> goals = fileData.get("goal");
                        if(goals == null) {
                            goals = new ArrayList<String>();
                        }
                        cultureVillagerData.get(cultureKey).put(villagerKey, new VillagerData(icon, goals));
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load villagers from folder " + villagersFolder, ex);
            }
        }
    }

    public Map<String, VillagerData> getVillagers(String cultureKey) {
        return cultureVillagerData.get(cultureKey);
    }
}
