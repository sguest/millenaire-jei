package sguest.millenairejei.millenairedata;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sguest.millenairejei.MillenaireJei;

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

        try (BufferedReader reader = new BufferedReader(new FileReader(itemFile))) {
            String line;
            while((line = reader.readLine()) != null) {
                line = line.trim();
                if(line.length() != 0 && !line.startsWith("//")) {
                    String[] parts = line.split(";");
                    String key = parts[0];
                    String resource = parts[1];
                    int meta = Integer.parseInt(parts[2]);
                    itemMap.put(key, new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(resource)), 1, meta));
                }
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().error("Could not load item list");
        }
    }

    public ItemStack getItem(String key) {
        return itemMap.get(key);
    }
}
