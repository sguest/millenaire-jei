package sguest.millenairejei;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyingRecipeRegistry {
    private Map<String, BuyingRecipeEntry> entryMap;
    private static BuyingRecipeRegistry instance;

    public static BuyingRecipeRegistry getInstance() {
        if (instance == null) {
            instance = new BuyingRecipeRegistry();
        }
        return instance;
    }

    private BuyingRecipeRegistry() {
        this.entryMap = new HashMap<>();
    }

    public void AddEntry(BuyingRecipeEntry entry) {
        String key = entry.GetBuyingItem().getTranslationKey();
        entryMap.put(key, entry);
    }

    public List<BuyingRecipeEntry> getBuyingRecipes() {
        return new ArrayList<>(entryMap.values());
    }
}