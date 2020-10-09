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
        File tradedFile = cultureFolder.resolve("traded_goods.txt").toFile();

        Map<String, Integer> buyingPrices = new TreeMap<>();
        Map<String, Integer> sellingPrices = new TreeMap<>();
        
        try (BufferedReader reader = new BufferedReader(new FileReader(tradedFile))) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.length() > 0 && !line.startsWith("//")) {
                    String[] values = line.split(",");
                    String itemKey = values[0];
                    int sellingPrice = parsePrice(values[1]);
                    int buyingPrice = parsePrice(values[2]);

                    if(sellingPrice > 0) {
                        sellingPrices.put(itemKey, sellingPrice);
                    }

                    if(buyingPrice > 0) {
                        buyingPrices.put(itemKey, buyingPrice);
                    }
                }
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().warn("Could not load trading information for culture " + cultureKey);
            return null;
        }

        Path shopsFolder = cultureFolder.resolve("shops");

        List<RecipeData> buyingRecipes = new ArrayList<RecipeData>();
        List<RecipeData> sellingRecipes = new ArrayList<RecipeData>();

        ItemLookup itemLookup = ItemLookup.getInstance();
        
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
                            if(transactionType.equalsIgnoreCase("sells") && sellingPrices.containsKey(item)) {
                                buyingRecipes.add(new RecipeData(itemLookup.getItem(item), sellingPrices.get(item), shopName));
                            }
                            else if((transactionType.equalsIgnoreCase("buys") || transactionType.equalsIgnoreCase("buysoptional")) && buyingPrices.containsKey(item)) {
                                sellingRecipes.add(new RecipeData(itemLookup.getItem(item), buyingPrices.get(item), shopName));
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

    private static int parsePrice(String priceString) {
        priceString = priceString.trim();
        if(priceString.length() == 0) {
            return 0;
        }
        if(priceString.contains("*")) {
            String[] priceParts = priceString.split("\\*");
            int value = 1;
            for(String part : priceParts) {
                value *= Integer.parseInt(part);
            }
            return value;
        }
        return Integer.parseInt(priceString);
    }
}
