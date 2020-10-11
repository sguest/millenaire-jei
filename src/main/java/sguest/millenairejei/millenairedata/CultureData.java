package sguest.millenairejei.millenairedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import net.minecraft.client.Minecraft;
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
        String languageCode = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();
        Path languagesFolder = configRoot.resolve("languages");
        Path currentLangFolder = languagesFolder.resolve(languageCode);
        if(!Files.exists(currentLangFolder)) {
            languageCode = languageCode.split("_")[0];
            currentLangFolder = languagesFolder.resolve(languageCode);
        }
        File stringsFile = currentLangFolder.resolve(cultureKey + "_strings.txt").toFile();
        String cultureName = cultureKey;
        Map<String, String> shopNames = new TreeMap<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(stringsFile))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.startsWith("culture." + cultureKey + "=")) {
                    cultureName = line.split("=")[1];
                }
                else if(line.startsWith("shop.")) {
                    String[] sections = line.split("=");
                    String shopName = sections[1];
                    String shopKey = sections[0].split("\\.")[1];
                    shopNames.put(shopKey, shopName);
                }
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().warn("Could not load language information for culture " + cultureKey);
        }

        Path cultureFolder = configRoot.resolve("cultures/" + cultureKey);
        Path shopsFolder = cultureFolder.resolve("shops");

        List<RecipeData> buyingRecipes = new ArrayList<RecipeData>();
        List<RecipeData> sellingRecipes = new ArrayList<RecipeData>();

        ItemLookup itemLookup = ItemLookup.getInstance();
        
        CultureTradedGoods tradedGoods = TradedGoodsLookup.getInstance().getCulture(cultureKey);

        File[] shopFiles = shopsFolder.toFile().listFiles();
        for (File shopFile : shopFiles) {
            String shopKey = FilenameUtils.getBaseName(shopFile.getName());
            String shopName = shopKey;
            if(shopNames.containsKey(shopKey)) {
                shopName = shopNames.get(shopKey);
            }
            shopName = cultureName + " " + shopName;
            try (BufferedReader reader = new BufferedReader(new FileReader(shopFile))) {
                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.contains("=") && !line.startsWith("//")) {
                        String[] parts = line.split("=");
                        String transactionType = parts[0];
                        String[] items = parts[1].split(",");

                        for(String item: items) {
                            int buyingPrice = tradedGoods.getBuyingPrice(item);
                            int sellingPrice = tradedGoods.getSellingPrice(item);

                            if(transactionType.equalsIgnoreCase("sells") && sellingPrice > 0) {
                                buyingRecipes.add(new RecipeData(itemLookup.getItem(item), sellingPrice, shopName));
                            }
                            else if((transactionType.equalsIgnoreCase("buys") || transactionType.equalsIgnoreCase("buysoptional")) && buyingPrice > 0) {
                                sellingRecipes.add(new RecipeData(itemLookup.getItem(item), buyingPrice, shopName));
                            }
                        }
                    }
                }
            } catch(IOException ex) {
                MillenaireJei.getLogger().warn("Could not load shop information for culture " + cultureKey + " shop " + shopKey);
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
