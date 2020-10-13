package sguest.millenairejei.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import sguest.millenairejei.MillenaireJei;

public class DataFileHelper {
    public static Map<String, String> loadDataFile(File dataFile) {
        Map<String, String> fileData = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

        try (BufferedReader reader = new BufferedReader(new FileReader(dataFile))) {
            String line;
            while((line = reader.readLine()) != null) {
                if(line.length() > 0 && !line.startsWith("//")) {
                    String[] parts = line.split("=", 2);
                    if(parts.length == 2) {
                        fileData.put(parts[0], parts[1]);
                    }
                }
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().error("Failed to load data file " + dataFile, ex);
            return null;
        }

        return fileData;
    }
}