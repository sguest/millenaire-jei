package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sguest.millenairejei.MillenaireJei;

public class MillenaireDataRegistry {
    private static MillenaireDataRegistry instance;

    private Map<String, CultureData> cultureMap;
    private Path configDirectory;

    public static MillenaireDataRegistry getInstance() {
        if (instance == null) {
            instance = new MillenaireDataRegistry();
        }
        return instance;
    }

    private MillenaireDataRegistry() {
        this.cultureMap = new HashMap<>();
    }

    public void setConfigDirectory(Path configDirectory) {
        this.configDirectory = configDirectory;
    }

    public void loadMillenaireData() {
        MillenaireJei.getLogger().info("Loading millenaire data");
        ItemLookup.getInstance().loadItems(configDirectory);

        Path culturesFolder = configDirectory.resolve("cultures");
        File[] cultureFiles = culturesFolder.toFile().listFiles();
        MillenaireJei.getLogger().info("Found " + cultureFiles.length + " cultures");
        for (File cultureFile : cultureFiles) {
            if(cultureFile.isDirectory()) {
                String cultureKey = cultureFile.getName();
                cultureMap.put(cultureKey, CultureData.loadCulture(cultureKey, configDirectory));
            }
        }
    }

    public List<RecipeData> getBuyingRecipes() {
        return cultureMap.values().stream().flatMap(culture -> culture.getBuyingRecipes().stream()).collect(Collectors.toList());
    }
    
    public List<RecipeData> getSellingRecipes() {
        return cultureMap.values().stream().flatMap(culture -> culture.getSellingRecipes().stream()).collect(Collectors.toList());
    }
}
