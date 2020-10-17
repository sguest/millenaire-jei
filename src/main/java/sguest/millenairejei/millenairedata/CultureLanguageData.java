package sguest.millenairejei.millenairedata;

import java.util.Map;
import java.util.TreeMap;

public class CultureLanguageData {
    private String shortName;
    private String fullName;
    private Map<String, String> shopNames;
    private Map<String, String> buildingNames;
    private Map<String, String> villageNames;
    private Map<String, String> villagerNames;

    public CultureLanguageData() {
        shopNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        buildingNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        villageNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        villagerNames = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getShopName(String key) {
        return shopNames.get(key);
    }

    public void setShopName(String key, String name) {
        shopNames.put(key, name);
    }

    public String getBuildingName(String key) {
        return buildingNames.get(key);
    }

    public void setBuildingName(String key, String name) {
        buildingNames.put(key, name);
    }

    public String getVillageName(String key) {
        return villageNames.get(key);
    }

    public void setVillageName(String key, String name) {
        villageNames.put(key, name);
    }

    public String getVillagerName(String key) {
        return villagerNames.get(key);
    }

    public void setVillagerName(String key, String name) {
        villagerNames.put(key, name);
    }
}
