package sguest.millenairejei.recipes.painting;

import java.util.List;

import net.minecraft.item.ItemStack;

public class PaintingRecipeData {
    private List<ItemStack> inputs;
    private ItemStack output;
    private ItemStack paintItem;

    public PaintingRecipeData(List<ItemStack> inputs, ItemStack output, ItemStack paintItem) {
        this.inputs = inputs;
        this.output = output;
        this.paintItem = paintItem;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public ItemStack getOutput() {
        return output;
    }

    public ItemStack getPaintItem() {
        return paintItem;
    }
}
