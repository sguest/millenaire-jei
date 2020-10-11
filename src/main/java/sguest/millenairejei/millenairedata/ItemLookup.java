package sguest.millenairejei.millenairedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.ItemHelper;

public class ItemLookup {
    private static ItemLookup instance;
    private Map<String, ItemStack> itemMap;

    public static ItemLookup getInstance() {
        if(instance == null) {
            instance = new ItemLookup();
        }
        return instance;
    }

    public ItemLookup() {
        itemMap = new HashMap<String, ItemStack>();
    }

    public void loadItems(Path configRoot) {
        File itemFile = configRoot.resolve("itemlist.txt").toFile();
        if(itemFile.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(itemFile))) {
                String line;
                while((line = reader.readLine()) != null) {
                    line = line.trim();
                    if(line.length() != 0 && !line.startsWith("//")) {
                        String[] parts = line.split(";");
                        String key = parts[0];
                        String resource = parts[1];
                        int meta = Integer.parseInt(parts[2]);
                        itemMap.put(key, ItemHelper.getStackFromResourceAndMeta(resource, meta));
                    }
                }
            } catch(IOException ex) {
                MillenaireJei.getLogger().error("Could not load item list from " + itemFile);
            }
        }
    }

    public ItemStack getItem(String key) {
        return itemMap.get(key);
    }
}
