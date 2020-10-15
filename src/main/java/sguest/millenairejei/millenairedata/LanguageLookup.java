package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.Minecraft;
import sguest.millenairejei.util.DataFileHelper;

public class LanguageLookup {
    private static LanguageLookup instance;

    private Map<String, CultureLanguageData> languageData;

    public static LanguageLookup getInstance() {
        if(instance == null) {
            instance = new LanguageLookup();
        }
        return instance;
    }

    private LanguageLookup() {
        languageData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadLanguageData(String cultureKey, Path rootFolder) {
        Path languageFolder = rootFolder.resolve("languages");
        if(languageFolder.toFile().exists()) {
            String languageCode = Minecraft.getMinecraft().getLanguageManager().getCurrentLanguage().getLanguageCode();

            Path specificLanguageFolder = languageFolder.resolve(languageCode);

            boolean specificFound = false;
            if(specificLanguageFolder.toFile().exists()) {
                specificFound = true;
                loadLanguage(cultureKey, specificLanguageFolder, false);
            }

            if(languageCode.contains("_")) {
                Path baseLanguageFolder = languageFolder.resolve(languageCode.split("_")[0]);
                loadLanguage(cultureKey, baseLanguageFolder, specificFound);
            }

            if(!languageCode.startsWith("en")) {
                Path fallbackLanguageFolder = languageFolder.resolve("en");
                loadLanguage(cultureKey, fallbackLanguageFolder, true);
            }
        }
    }

    public CultureLanguageData getLanguageData(String cultureKey) {
        return languageData.get(cultureKey);
    }

    private void loadLanguage(String cultureKey, Path languageFolder, boolean isFallback) {
        CultureLanguageData cultureLanguage = languageData.get(cultureKey);
        if(cultureLanguage == null) {
            cultureLanguage = new CultureLanguageData();
            languageData.put(cultureKey, cultureLanguage);
        }

        File languageFile = languageFolder.resolve(cultureKey + "_strings.txt").toFile();
        if(languageFile.exists()) {
            Map<String, List<String>> fileData = DataFileHelper.loadDataFile(languageFile);
            if(fileData != null) {
                List<String> shortName = fileData.get("culture." + cultureKey);
                if(shortName != null && (!isFallback || cultureLanguage.getShortName() == null)) {
                    cultureLanguage.setShortName(shortName.get(0));
                }

                List<String> fullName = fileData.get("culture.fullname");
                if(fullName != null && (!isFallback || cultureLanguage.getFullName() == null)) {
                    cultureLanguage.setFullName(fullName.get(0));
                }

                for(Map.Entry<String, List<String>> entry : fileData.entrySet()) {
                    if(entry.getKey().startsWith("shop.")) {
                        String shopKey = entry.getKey().split("\\.", 2)[1];
                        List<String> shopName = entry.getValue();
                        if(!isFallback || cultureLanguage.getShopName(shopKey) == null) {
                            cultureLanguage.setShopName(shopKey, shopName.get(0));
                        }
                    }
                    else if(entry.getKey().startsWith("village.")) {
                        String villageKey = entry.getKey().split("\\.", 2)[1];
                        List<String> villageName = entry.getValue();
                        if(!isFallback || cultureLanguage.getVillageName(villageKey) == null) {
                            cultureLanguage.setVillageName(villageKey, villageName.get(0));
                        }
                    }
                }
            }
        }

        File buildingFile = languageFolder.resolve(cultureKey + "_buildings.txt").toFile();
        if(buildingFile.exists()) {
            Map<String, List<String>> fileData = DataFileHelper.loadDataFile(buildingFile);
            if(fileData != null) {
                for(Map.Entry<String, List<String>> entry : fileData.entrySet()) {
                    String buildingKey = entry.getKey();
                    if(buildingKey.endsWith("0")) {
                        buildingKey = buildingKey.substring(0, buildingKey.length() - 1);
                        cultureLanguage.setBuildingName(buildingKey, entry.getValue().get(0));
                    }
                }
            }
        }
    }
}