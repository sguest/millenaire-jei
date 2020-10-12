package sguest.millenairejei.recipes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.millenairedata.CultureData;
import sguest.millenairejei.millenairedata.CultureDataLookup;
import sguest.millenairejei.millenairedata.CultureLanguageData;
import sguest.millenairejei.millenairedata.CultureTradedGoods;
import sguest.millenairejei.millenairedata.ItemLookup;
import sguest.millenairejei.millenairedata.ItemShopData;
import sguest.millenairejei.millenairedata.LanguageLookup;
import sguest.millenairejei.millenairedata.ShopLookup;
import sguest.millenairejei.millenairedata.TradedGoodsLookup;

public class RecipeLookup {
    private List<RecipeData> buyingRecipes;
    private List<RecipeData> sellingRecipes;

    public RecipeLookup() {
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
                    List<String> shopNames = getShopNames(shopEntry.getValue().getBuyingShops(), languageData);
                    buyingRecipes.add(new RecipeData(itemLookup.getItem(itemKey), sellingPrice, cultureName, cultureIcon, shopNames));
                }

                int buyingPrice = tradedGoods.getBuyingPrice(itemKey);
                if(buyingPrice > 0) {
                    List<String> shopNames = getShopNames(shopEntry.getValue().getSellingShops(), languageData);
                    sellingRecipes.add(new RecipeData(itemLookup.getItem(itemKey), buyingPrice, cultureName, cultureIcon, shopNames));
                }
            }
        }
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
