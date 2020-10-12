package sguest.millenairejei.millenairedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import sguest.millenairejei.MillenaireJei;

public class CultureData {
    private List<RecipeData> buyingRecipes;
    private List<RecipeData> sellingRecipes;

    private CultureData(List<RecipeData> buyingRecipes, List<RecipeData> sellingRecipes) {
        this.buyingRecipes = buyingRecipes;
        this.sellingRecipes = sellingRecipes;
    }

    public static CultureData loadCulture(String cultureKey) {
        MillenaireJei.getLogger().info("Loading culture " + cultureKey);

        List<RecipeData> buyingRecipes = new ArrayList<>();
        List<RecipeData> sellingRecipes = new ArrayList<>();

        ItemLookup itemLookup = ItemLookup.getInstance();
        
        CultureTradedGoods tradedGoods = TradedGoodsLookup.getInstance().getCulture(cultureKey);
        Map<String, ItemShopData> shopData = ShopLookup.getInstance().getCultureShops(cultureKey);
        CultureLanguageData languageData = LanguageLookup.getInstance().getLanguageData(cultureKey);

        for(Map.Entry<String, ItemShopData> shopEntry : shopData.entrySet()) {
            String itemKey = shopEntry.getKey();

            String cultureName = languageData.getShortName();

            int sellingPrice = tradedGoods.getSellingPrice(itemKey);
            if(sellingPrice > 0) {
                List<String> shopNames = getShopNames(shopEntry.getValue().getBuyingShops(), languageData);
                buyingRecipes.add(new RecipeData(itemLookup.getItem(itemKey), sellingPrice, cultureName, shopNames));
            }

            int buyingPrice = tradedGoods.getBuyingPrice(itemKey);
            if(buyingPrice > 0) {
                List<String> shopNames = getShopNames(shopEntry.getValue().getSellingShops(), languageData);
                sellingRecipes.add(new RecipeData(itemLookup.getItem(itemKey), buyingPrice, cultureName, shopNames));
            }
        }

        return new CultureData(buyingRecipes, sellingRecipes);
    }

    public List<RecipeData> getBuyingRecipes() {
        return buyingRecipes;
    }

    public List<RecipeData> getSellingRecipes() {
        return sellingRecipes;
    }

    private static List<String> getShopNames(List<String> shopKeys, CultureLanguageData languageData) {
        List<String> shopNames = new ArrayList<>();

        for(String shopKey : shopKeys) {
            String shopName = languageData.getShopName(shopKey);
            shopNames.add(shopName == null ? shopKey : shopName);
        }

        return shopNames;
    }
}
