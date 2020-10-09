package sguest.millenairejei.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class ItemHelper {
    public static ItemStack getStackFromResource(String resourceLocation) {
        return getStackFromResourceAndMeta(resourceLocation, 0);
    }

    public static ItemStack getStackFromResourceAndMeta(String resourceLocation, int meta) {
        return new ItemStack(Item.REGISTRY.getObject(new ResourceLocation(resourceLocation)), 1, meta);
    }
}
