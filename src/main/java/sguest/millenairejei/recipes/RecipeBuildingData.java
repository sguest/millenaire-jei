package sguest.millenairejei.recipes;

import net.minecraft.item.ItemStack;

public class RecipeBuildingData {
    private String name;
    private ItemStack icon;

    public RecipeBuildingData(String name, ItemStack icon) {
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
