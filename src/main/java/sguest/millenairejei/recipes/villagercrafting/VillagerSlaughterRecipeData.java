package sguest.millenairejei.recipes.villagercrafting;

import java.util.List;

import net.minecraft.item.ItemStack;
import sguest.millenairejei.recipes.IconWithLabel;

public class VillagerSlaughterRecipeData {
    private final String animalName;
    private final List<ItemStack> outputs;
    private final IconWithLabel culture;
    private final List<IconWithLabel> villagers;

    public VillagerSlaughterRecipeData(String animalName, List<ItemStack> outputs, IconWithLabel culture, List<IconWithLabel> villagers) {
        this.animalName = animalName;
        this.outputs = outputs;
        this.culture = culture;
        this.villagers = villagers;
    }

    public String getAnimalName() {
        return animalName;
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
