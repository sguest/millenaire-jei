package sguest.millenairejei.recipes;

import net.minecraft.item.ItemStack;

public class IconWithLabel {
    private final String label;
    private final ItemStack icon;

    public IconWithLabel(String label, ItemStack icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public ItemStack getIcon() {
        return icon;
    }
}
