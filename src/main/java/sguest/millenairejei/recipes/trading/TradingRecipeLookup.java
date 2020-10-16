package sguest.millenairejei.recipes.trading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.BuildingData;
import sguest.millenairejei.millenairedata.CultureData;
import sguest.millenairejei.millenairedata.CultureDataLookup;
import sguest.millenairejei.millenairedata.CultureLanguageData;
import sguest.millenairejei.millenairedata.CultureTradedGoods;
import sguest.millenairejei.millenairedata.ItemLookup;
import sguest.millenairejei.millenairedata.LanguageLookup;
import sguest.millenairejei.millenairedata.ShopData;
import sguest.millenairejei.millenairedata.BuildingLookup;
import sguest.millenairejei.millenairedata.ShopLookup;
import sguest.millenairejei.millenairedata.TradedGoodsLookup;
import sguest.millenairejei.millenairedata.VillageData;
import sguest.millenairejei.millenairedata.VillageLookup;

public class TradingRecipeLookup {
    private List<TradingRecipeData> buyingRecipes;
    private List<TradingRecipeData> sellingRecipes;

    public TradingRecipeLookup() {
        buyingRecipes = new ArrayList<>();
        sellingRecipes = new ArrayList<>();
    }

    public void buildRecipes() {
        ItemLookup itemLookup = ItemLookup.getInstance();

        Map<String, CultureData> cultureInfo = CultureDataLookup.getInstance().getCultureInfo();

        for(Map.Entry<String, CultureData> cultureEntry : cultureInfo.entrySet()) {
            String cultureKey = cultureEntry.getKey();
            CultureData cultureData = cultureEntry.getValue();
            CultureTradedGoods tradedGoods = TradedGoodsLookup.getInstance().getCulture(cultureKey);
            Map<String, ShopData> cultureShops = ShopLookup.getInstance().getCultureShops(cultureKey);
            CultureLanguageData languageData = LanguageLookup.getInstance().getLanguageData(cultureKey);
            Map<String, VillageData> villageLookupData = VillageLookup.getInstance().getVillages(cultureKey);
            Map<String, BuildingData> cultureBuildings = BuildingLookup.getInstance().getCultureBuildings(cultureKey);

            String cultureName = languageData.getFullName();
            if(cultureName == null) {
                cultureName = cultureKey;
            }

            ItemStack cultureIcon = null;
            if(cultureData.getIcon() != null) {
                cultureIcon = ItemLookup.getInstance().getItem(cultureData.getIcon());
            }

            for(Map.Entry<String, VillageData> villageEntry : villageLookupData.entrySet()) {
                VillageData villageData = villageEntry.getValue();
                String villageName = languageData.getVillageName(villageEntry.getKey());
                if(villageName == null) {
                    villageName = villageEntry.getKey();
                }

                ItemStack villageIcon = null;
                if(villageData.getIcon() != null) {
                    villageIcon = ItemLookup.getInstance().getItem(villageData.getIcon());
                }

                Map<String, List<RecipeBuildingData>> sellingMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Map<String, Integer> sellingPrices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Map<String, List<RecipeBuildingData>> buyingMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                Map<String, Integer> buyingPrices = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

                for(Map.Entry<String, BuildingData> buildingEntry : getVillageBuildings(villageData, cultureBuildings).entrySet()) {
                    String buildingKey = buildingEntry.getKey();
                    String buildingName = languageData.getBuildingName(buildingKey);
                    if(buildingName == null) {
                        buildingName = buildingKey;
                    }
                    ItemStack icon = null;
                    BuildingData building = buildingEntry.getValue();
                    if(building.getIcon() != null) {
                        icon = itemLookup.getItem(building.getIcon());
                    }
                    RecipeBuildingData recipeBuilding = new RecipeBuildingData(buildingName, icon);
                    for(String shopKey : building.getShops()) {
                        ShopData shopData = cultureShops.get(shopKey);
                        if(shopData != null) {
                            for(String itemKey : shopData.getSoldItems()) {
                                Integer sellingPrice = villageData.getSellingPrices().get(itemKey);
                                if(sellingPrice == null) {
                                    sellingPrice = tradedGoods.getSellingPrice(itemKey);
                                }
                                if(sellingPrice > 0) {
                                    sellingMap.putIfAbsent(itemKey, new ArrayList<RecipeBuildingData>());
                                    sellingMap.get(itemKey).add(recipeBuilding);
                                    sellingPrices.put(itemKey, sellingPrice);
                                }
                            }

                            for(String itemKey : shopData.getBoughtItems()) {
                                Integer buyingPrice = villageData.getBuyingPrices().get(itemKey);
                                if(buyingPrice == null) {
                                    buyingPrice = tradedGoods.getBuyingPrice(itemKey);
                                }
                                if(buyingPrice > 0) {
                                    buyingMap.putIfAbsent(itemKey, new ArrayList<RecipeBuildingData>());
                                    buyingMap.get(itemKey).add(recipeBuilding);
                                    buyingPrices.put(itemKey, buyingPrice);
                                }
                            }
                        }
                    }
                }

                for(Map.Entry<String, List<RecipeBuildingData>> entry : sellingMap.entrySet()) {
                    String itemKey = entry.getKey();
                    int sellingPrice = sellingPrices.get(itemKey);
                    for(List<RecipeBuildingData> buildings : Lists.partition(entry.getValue(), 3)) {
                        buyingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), sellingPrice, cultureName, cultureIcon, villageName, villageIcon, buildings));
                    }
                }

                for(Map.Entry<String, List<RecipeBuildingData>> entry : buyingMap.entrySet()) {
                    String itemKey = entry.getKey();
                    int buyingPrice = buyingPrices.get(itemKey);
                    for(List<RecipeBuildingData> buildings : Lists.partition(entry.getValue(), 3)) {
                        sellingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), buyingPrice, cultureName, cultureIcon, villageName, villageIcon, buildings));
                    }
                }
            }
        }
    }

    public List<TradingRecipeData> getBuyingRecipes() {
        return buyingRecipes;
    }

    public List<TradingRecipeData> getSellingRecipes() {
        return sellingRecipes;
    }

    private static Map<String, BuildingData> getVillageBuildings(VillageData villageData, Map<String, BuildingData> cultureBuildings) {
        Map<String, BuildingData> buildings = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        for(String buildingKey : villageData.getBuildings()) {
            BuildingData building = cultureBuildings.get(buildingKey);
            if(building != null) {
                buildings.put(buildingKey, building);
                addSubBuildings(building, buildings, cultureBuildings);
            }
        }

        return buildings;
    }

    private static void addSubBuildings(BuildingData mainBuilding, Map<String, BuildingData> buildings, Map<String, BuildingData> cultureBuildings) {
        for(String buildingKey : mainBuilding.getSubBuildings()) {
            BuildingData subBuilding = cultureBuildings.get(buildingKey);
            if(subBuilding != null) {
                buildings.put(buildingKey, subBuilding);
                addSubBuildings(subBuilding, buildings, cultureBuildings);
            }
        }
    }
}
