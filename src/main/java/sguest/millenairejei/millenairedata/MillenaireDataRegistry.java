package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import sguest.millenairejei.MillenaireJei;

public class MillenaireDataRegistry {
    private static MillenaireDataRegistry instance;

    private Map<String, CultureData> cultureMap;
    private Path modsDirectory;

    public static MillenaireDataRegistry getInstance() {
        if (instance == null) {
            instance = new MillenaireDataRegistry();
        }
        return instance;
    }

    private MillenaireDataRegistry() {
        this.cultureMap = new HashMap<>();
    }

    public void setModsDirectory(Path configDirectory) {
        this.modsDirectory = configDirectory;
    }

    public void loadMillenaireData() {
        MillenaireJei.getLogger().info("Loading millenaire data");
        List<Path> loadingRoots = getLoadingRoots();

        ItemLookup itemLookup = ItemLookup.getInstance();
        for(Path loadingRoot: loadingRoots) {
            itemLookup.loadItems(loadingRoot);

            Path culturesFolder = loadingRoot.resolve("cultures");
            File[] cultureFiles = culturesFolder.toFile().listFiles();
            for (File cultureFile : cultureFiles) {
                if(cultureFile.isDirectory()) {
                    String cultureKey = cultureFile.getName();
                    Path culturePath = cultureFile.toPath();
                    TradedGoodsLookup.getInstance().loadTradedGoods(cultureKey, culturePath);
                    ShopLookup.getInstance().LoadShopInfo(cultureKey, culturePath);
                }
            }
        }

        Path millenaireDirectory = modsDirectory.resolve("millenaire");

        Path culturesFolder = millenaireDirectory.resolve("cultures");
        File[] cultureFiles = culturesFolder.toFile().listFiles();
        MillenaireJei.getLogger().info("Found " + cultureFiles.length + " cultures");
        for (File cultureFile : cultureFiles) {
            if(cultureFile.isDirectory()) {
                String cultureKey = cultureFile.getName();
                cultureMap.put(cultureKey, CultureData.loadCulture(cultureKey, millenaireDirectory));
            }
        }
    }

    public List<RecipeData> getBuyingRecipes() {
        return cultureMap.values().stream().flatMap(culture -> culture.getBuyingRecipes().stream()).collect(Collectors.toList());
    }
    
    public List<RecipeData> getSellingRecipes() {
        return cultureMap.values().stream().flatMap(culture -> culture.getSellingRecipes().stream()).collect(Collectors.toList());
    }

    private List<Path> getLoadingRoots() {
        List<Path> loadingRoots = new ArrayList<Path>();
        loadingRoots.add(modsDirectory.resolve("millenaire"));

        Path millenaireCustomDirectory = modsDirectory.resolve("millenaire-custom");
        loadingRoots.add(millenaireCustomDirectory);

        Path modsDirectory = millenaireCustomDirectory.resolve("mods");
        File[] modDirectories = modsDirectory.toFile().listFiles();
        for(File modDirectory: modDirectories) {
            if(modDirectory.isDirectory()) {
                loadingRoots.add(modDirectory.toPath());
            }
        }
        return loadingRoots;
    }
}
