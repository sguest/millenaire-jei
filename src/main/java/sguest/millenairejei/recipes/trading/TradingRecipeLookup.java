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

                int sellingPrice = tradedGoods.getSellingPrice(itemKey);
                if(sellingPrice > 0) {
                    List<RecipeBuildingData> buildings = getBuildings(shopEntry.getValue().getSellingShops(), cultureKey, languageData);
                    for(List<RecipeBuildingData> subList: Lists.partition(buildings, 4)) {
                        buyingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), sellingPrice, cultureName, cultureIcon, subList));
                    }
                }

                int buyingPrice = tradedGoods.getBuyingPrice(itemKey);
                if(buyingPrice > 0) {
                    List<RecipeBuildingData> buildings = getBuildings(shopEntry.getValue().getBuyingShops(), cultureKey, languageData);
                    for(List<RecipeBuildingData> subList: Lists.partition(buildings, 4)) {
                        sellingRecipes.add(new TradingRecipeData(itemLookup.getItem(itemKey), buyingPrice, cultureName, cultureIcon, subList));
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

    private static List<RecipeBuildingData> getBuildings(List<String> shopKeys, String cultureKey, CultureLanguageData languageData) {
        List<RecipeBuildingData> buildingData = new ArrayList<>();

        for(String shopKey : shopKeys) {
            List<BuildingData> buildings = ShopBuildingLookup.getInstance().getShopBuildings(cultureKey, shopKey);
            if(buildings != null) {
                for(BuildingData building : buildings) {
                    String buildingName = languageData.getBuildingName(building.getBuildingKey());
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

        return buildingData;
    }
}
