package sguest.millenairejei.recipes.painting;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.util.ItemHelper;

public class PaintingRecipeLookup {
    private final static String[] colours = {
        "white",
        "orange",
        "magenta",
        "light_blue",
        "yellow",
        "lime",
        "pink",
        "gray",
        "silver",
        "cyan",
        "purple",
        "blue",
        "brown",
        "green",
        "red",
        "black",
    };

    private static final String[] itemTypes = {
        "painted_brick",
        "stairs_painted_brick",
        "wall_painted_brick",
        "slab_painted_brick",
        "painted_brick_decorated",
    };

    private final List<PaintingRecipeData> recipes;

    public PaintingRecipeLookup() {
        recipes = new ArrayList<>();
    }

    public void buildRecipes() {
        Map<String, ItemStack> buckets = new HashMap<>();
        for(String colour : colours) {
            buckets.put(colour, ItemHelper.getStackFromResource("millenaire:paint_bucket_" + colour));
        }

        for(String itemType: itemTypes) {
            Map<String, ItemStack> items = new HashMap<>();
            for(String colour : colours) {
                items.put(colour, ItemHelper.getStackFromResource("millenaire:" + itemType + "_" + colour));
            }

            List<ItemStack> itemList = new ArrayList<ItemStack>(items.values());

            for(Map.Entry<String, ItemStack> entry : items.entrySet()) {
                recipes.add(new PaintingRecipeData(itemList, entry.getValue(), buckets.get(entry.getKey())));
            }
        }
    }

    public List<PaintingRecipeData> getRecipes() {
        return recipes;
    }
}
