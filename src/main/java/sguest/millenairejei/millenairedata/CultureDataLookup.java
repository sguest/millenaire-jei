package sguest.millenairejei.millenairedata;

import java.io.File;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import sguest.millenairejei.util.DataFileHelper;

public class CultureDataLookup {
    private static CultureDataLookup instance;

    private Map<String, CultureData> cultureInfo;

    public static CultureDataLookup getInstance() {
        if(instance == null) {
            instance = new CultureDataLookup();
        }
        return instance;
    }

    private CultureDataLookup() {
        cultureInfo = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadCultureData(String cultureKey, Path culturePath) {
        CultureData cultureData = cultureInfo.get(cultureKey);
        if(cultureData == null) {
            cultureData = new CultureData();
            cultureInfo.put(cultureKey, cultureData);
        }

        File cultureFile = culturePath.resolve("culture.txt").toFile();
        if(cultureFile.exists()) {
            Map<String, List<String>> fileData = DataFileHelper.loadDataFile(cultureFile);
            if(fileData != null && fileData.containsKey("icon")) {
                cultureData.setIcon(fileData.get("icon").get(0));
            }
        }
    }

    public Map<String, CultureData> getCultureInfo() {
        return cultureInfo;
    }
}
