package sguest.millenairejei.recipes.trading;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.BuildingData;
import sguest.millenairejei.millenairedata.CultureData;
import sguest.millenairejei.millenairedata.CultureDataLookup;
import sguest.millenairejei.millenairedata.CultureLanguageData;
import sguest.millenairejei.millenairedata.CultureTradedGoods;
import sguest.millenairejei.millenairedata.ItemLookup;
import sguest.millenairejei.millenairedata.ItemShopData;
import sguest.millenairejei.millenairedata.LanguageLookup;
import sguest.millenairejei.millenairedata.ShopBuildingLookup;
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
            Map<String, ItemShopData> shopData = ShopLookup.getInstance().getCultureShops(cultureKey);
            CultureLanguageData languageData = LanguageLookup.getInstance().getLanguageData(cultureKey);
            Map<String, VillageData> villageLookupData = VillageLookup.getInstance().getVillages(cultureKey);

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

                for(Map.Entry<String, ItemShopData> shopEntry : shopData.entrySet()) {
                    String itemKey = shopEntry.getKey();

                    String cultureName = languageData.getFullName();
                    if(cultureName == null) {
                        cultureName = cultureKey;
                    }

                    ItemStack cultureIcon = null;
                    if(cultureData.getIcon() != null) {
                        cultureIcon = ItemLookup.getInstance().getItem(cultureData.getIcon());
                    }

                    Integer sellingPrice = villageData.getSellingPrices().get(itemKey);
                    if(sellingPrice == null) {
                        sellingPrice = tradedGoods.getSellingPrice(itemKey);
                    }
                    if(sellingPrice > 0) {
                        List<RecipeBuildingData> buildings = getBuildings(shopEntry.getValue().getSellingShops(), villageData.getBuildings(), cultureKey, languageData);
                        for(List<RecipeBuildingData> subList: Lists.partition(buildings, 3)) {
                            buyingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), sellingPrice, cultureName, cultureIcon, villageName, villageIcon, subList));
                        }
                    }

                    Integer buyingPrice = villageData.getBuyingPrices().get(itemKey);
                    if(buyingPrice == null) {
                        buyingPrice = tradedGoods.getBuyingPrice(itemKey);
                    }
                    if(buyingPrice > 0) {
                        List<RecipeBuildingData> buildings = getBuildings(shopEntry.getValue().getBuyingShops(), villageData.getBuildings(), cultureKey, languageData);
                        for(List<RecipeBuildingData> subList: Lists.partition(buildings, 3)) {
                            sellingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), buyingPrice, cultureName, cultureIcon, villageName, villageIcon, subList));
                        }
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

    private static List<RecipeBuildingData> getBuildings(List<String> shopKeys, List<String> possibleBuildings, String cultureKey, CultureLanguageData languageData) {
        List<RecipeBuildingData> buildingData = new ArrayList<>();

        for(String shopKey : shopKeys) {
            List<BuildingData> buildings = ShopBuildingLookup.getInstance().getShopBuildings(cultureKey, shopKey);
            if(buildings != null) {
                for(BuildingData building : buildings) {
                    String buildingKey = building.getBuildingKey();
                    String normalizedBuildingKey = buildingKey.substring(0, buildingKey.length() - 2);
                    if(possibleBuildings.contains(normalizedBuildingKey)) {
                        String buildingName = languageData.getBuildingName(buildingKey);
                        if(buildingName == null) {
                            buildingName = building.getBuildingKey();
                        }
                        final String bName = buildingName;
                        if(!buildingData.stream().anyMatch(b -> b.getName().equals(bName))) {
                            ItemStack icon = null;
                            if(building.getIcon() != null) {
                                icon = ItemLookup.getInstance().getItem(building.getIcon());
                            }
                            buildingData.add(new RecipeBuildingData(buildingName, icon));
                        }
                    }
                }
            }
        }

        return buildingData;
    }
}
