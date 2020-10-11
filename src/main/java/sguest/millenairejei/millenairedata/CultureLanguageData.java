package sguest.millenairejei.millenairedata;

import java.util.Map;
import java.util.TreeMap;

public class CultureLanguageData {
    private String shortName;
    private String fullName;
    private Map<String, String> shopNames;

    public CultureLanguageData() {
        shopNames = new TreeMap<>();
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
}
