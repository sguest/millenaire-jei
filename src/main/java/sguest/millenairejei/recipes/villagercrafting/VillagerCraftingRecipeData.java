package sguest.millenairejei.recipes.villagercrafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.IconWithLabel;

public class VillagerCraftingRecipeData {
    private final List<ItemStack> inputs;
    private final List<ItemStack> outputs;
    private final IconWithLabel culture;
    private final List<IconWithLabel> villagers;

    public VillagerCraftingRecipeData(List<ItemStack> inputs, List<ItemStack> outputs, IconWithLabel culture, List<IconWithLabel> villagers) {
        this.inputs = inputs;
        this.outputs = outputs;
        this.culture = culture;
        this.villagers = villagers;
    }

    public List<ItemStack> getInputs() {
        return inputs;
    }

    public List<ItemStack> getOutputs() {
        return outputs;
    }

    public IconWithLabel getCulture() {
        return culture;
    }

    public List<IconWithLabel> getVillagers() {
        return villagers;
    }
}
