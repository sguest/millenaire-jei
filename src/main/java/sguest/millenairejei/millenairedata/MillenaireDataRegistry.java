package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.trading.BuildingLookup;
import sguest.millenairejei.millenairedata.trading.ShopLookup;
import sguest.millenairejei.millenairedata.trading.TradedGoodsLookup;
import sguest.millenairejei.millenairedata.trading.VillageLookup;
import sguest.millenairejei.millenairedata.villagercrafting.GoalLookup;
import sguest.millenairejei.millenairedata.villagercrafting.VillagerLookup;

public class MillenaireDataRegistry {
    private static MillenaireDataRegistry instance;

    private Path modsDirectory;

    public static MillenaireDataRegistry getInstance() {
        if (instance == null) {
            instance = new MillenaireDataRegistry();
        }
        return instance;
    }

    private MillenaireDataRegistry() {
    }

    public void setModsDirectory(Path configDirectory) {
        this.modsDirectory = configDirectory;
    }

    public void loadMillenaireData() {
        MillenaireJei.getLogger().info("Loading millenaire data");
        List<Path> loadingRoots = getLoadingRoots();

        ItemLookup itemLookup = ItemLookup.getInstance();

        for(Path loadingRoot: loadingRoots) {
            try {
                itemLookup.loadItems(loadingRoot);
                GoalLookup.getInstance().loadGoals(loadingRoot);

                Path culturesFolder = loadingRoot.resolve("cultures");
                File[] cultureFiles = culturesFolder.toFile().listFiles();
                for (File cultureFile : cultureFiles) {
                    if(cultureFile.isDirectory()) {
                        String cultureKey = cultureFile.getName();
                        Path culturePath = cultureFile.toPath();
                        TradedGoodsLookup.getInstance().loadTradedGoods(cultureKey, culturePath);
                        ShopLookup.getInstance().loadShopInfo(cultureKey, culturePath);
                        LanguageLookup.getInstance().loadLanguageData(cultureKey, loadingRoot);
                        CultureDataLookup.getInstance().loadCultureData(cultureKey, culturePath);
                        BuildingLookup.getInstance().loadData(cultureKey, culturePath);
                        VillageLookup.getInstance().LoadVillages(cultureKey, culturePath);
                        VillagerLookup.getInstance().loadVillagers(cultureKey, culturePath);
                    }
                }
            } catch(Exception ex) {
                MillenaireJei.getLogger().error("Error loading millenaire data from " + loadingRoot, ex);
            }
        }
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
