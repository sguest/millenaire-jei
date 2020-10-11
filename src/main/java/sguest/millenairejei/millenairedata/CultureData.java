package sguest.millenairejei.millenairedata;

import java.nio.file.Path;
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

    public static CultureData loadCulture(String cultureKey, Path configRoot) {
        MillenaireJei.getLogger().info("Loading culture " + cultureKey);

        List<RecipeData> buyingRecipes = new ArrayList<RecipeData>();
        List<RecipeData> sellingRecipes = new ArrayList<RecipeData>();

        ItemLookup itemLookup = ItemLookup.getInstance();
        
        CultureTradedGoods tradedGoods = TradedGoodsLookup.getInstance().getCulture(cultureKey);
        Map<String, ShopData> shopData = ShopLookup.getInstance().getCultureShops(cultureKey);
        CultureLanguageData languageData = LanguageLookup.getInstance().getLanguageData(cultureKey);

        for(Map.Entry<String, ShopData> shopEntry : shopData.entrySet()) {
            String shopKey = shopEntry.getKey();
            String shopName = languageData.getShopName(shopKey);
            if(shopName == null) {
                shopName = shopKey;
            }
            shopName = languageData.getShortName() + " " + shopName;

            for(String item: shopEntry.getValue().getSoldItems()) {
                int sellingPrice = tradedGoods.getSellingPrice(item);
                if(sellingPrice > 0) {
                    buyingRecipes.add(new RecipeData(itemLookup.getItem(item), sellingPrice, shopName));
                }
            }

            for(String item: shopEntry.getValue().getSoldItems()) {
                int buyingPrice = tradedGoods.getBuyingPrice(item);
                if(buyingPrice > 0) {
                    sellingRecipes.add(new RecipeData(itemLookup.getItem(item), buyingPrice, shopName));
                }
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
}
