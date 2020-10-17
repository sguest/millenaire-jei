package sguest.millenairejei.recipes.villagercrafting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.CultureData;
import sguest.millenairejei.millenairedata.CultureDataLookup;
import sguest.millenairejei.millenairedata.CultureLanguageData;
import sguest.millenairejei.millenairedata.ItemLookup;
import sguest.millenairejei.millenairedata.LanguageLookup;
import sguest.millenairejei.millenairedata.villagercrafting.CraftingGoalData;
import sguest.millenairejei.millenairedata.villagercrafting.GoalLookup;
import sguest.millenairejei.millenairedata.villagercrafting.VillagerData;
import sguest.millenairejei.millenairedata.villagercrafting.VillagerLookup;
import sguest.millenairejei.recipes.IconWithLabel;

public class VillagerCraftingRecipeLookup {
    private final List<VillagerCraftingRecipeData> craftingRecipes;
    private final List<VillagerCraftingRecipeData> cookingRecipes;

    public VillagerCraftingRecipeLookup() {
        craftingRecipes = new ArrayList<>();
        cookingRecipes = new ArrayList<>();
    }

    public void BuildRecipes() {
        ItemLookup itemLookup = ItemLookup.getInstance();
        GoalLookup goalLookup = GoalLookup.getInstance();

        Map<String, CultureData> cultureInfo = CultureDataLookup.getInstance().getCultureInfo();
        for(Map.Entry<String, CultureData> cultureEntry : cultureInfo.entrySet()) {
            String cultureKey = cultureEntry.getKey();
            try {
                CultureData cultureData = cultureEntry.getValue();
                Map<String, VillagerData> villagerData = VillagerLookup.getInstance().getVillagers(cultureKey);
                CultureLanguageData languageData = LanguageLookup.getInstance().getLanguageData(cultureKey);

                String cultureName = languageData.getFullName();
                if(cultureName == null) {
                    cultureName = cultureKey;
                }
                ItemStack cultureIcon = null;
                if(cultureData.getIcon() != null) {
                    cultureIcon = ItemLookup.getInstance().getItem(cultureData.getIcon());
                }
                IconWithLabel culture = new IconWithLabel(cultureName, cultureIcon);

                Map<String, List<IconWithLabel>> villagersByGoal = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

                for(Map.Entry<String, VillagerData> villagerEntry : villagerData.entrySet()) {
                    VillagerData villager = villagerEntry.getValue();

                    String villagerKey = villagerEntry.getKey();
                    String villagerName = languageData.getVillagerName(villagerKey);
                    if(villagerName == null) {
                        villagerName = villagerKey;
                    }
                    ItemStack villagerIcon = null;
                    if(villager.getIcon() != null) {
                        villagerIcon = itemLookup.getItem(villager.getIcon());
                    }
                    IconWithLabel villagerLabel = new IconWithLabel(villagerName, villagerIcon);

                    for(String goal : villager.getGoals()) {
                        villagersByGoal.putIfAbsent(goal, new ArrayList<>());
                        villagersByGoal.get(goal).add(villagerLabel);
                    }
                }

                for(Map.Entry<String, List<IconWithLabel>> entry : villagersByGoal.entrySet()) {
                    String goalKey = entry.getKey();
                    CraftingGoalData craftingGoal = goalLookup.getCrafingGoal(goalKey);
                    if(craftingGoal != null) {
                        List<ItemStack> inputs = new ArrayList<>();
                        for(Map.Entry<String, Integer> inputEntry : craftingGoal.getInputs().entrySet()) {
                            ItemStack itemStack = itemLookup.getItem(inputEntry.getKey());
                            if(itemStack != null) {
                                itemStack = itemStack.copy();
                                itemStack.setCount(inputEntry.getValue());
                                inputs.add(itemStack);
                            }
                        }

                        List<ItemStack> outputs = new ArrayList<>();
                        for(Map.Entry<String, Integer> outputEntry : craftingGoal.getOutputs().entrySet()) {
                            ItemStack itemStack = itemLookup.getItem(outputEntry.getKey());
                            if(itemStack != null) {
                                itemStack = itemStack.copy();
                                itemStack.setCount(outputEntry.getValue());
                                outputs.add(itemStack);
                            }
                        }

                        for(List<IconWithLabel> villagers : Lists.partition(entry.getValue(), 4)) {
                            craftingRecipes.add(new VillagerCraftingRecipeData(inputs, outputs, culture, villagers));
                        }
                    }

                    String cookingGoal = goalLookup.getCookingGoalInput(goalKey);
                    if(cookingGoal != null) {
                        List<ItemStack> inputs = new ArrayList<>();
                        ItemStack inputItem = itemLookup.getItem(cookingGoal);
                        if(inputItem != null) {
                            inputs.add(inputItem);

                            ItemStack outputItem = FurnaceRecipes.instance().getSmeltingResult(inputItem);
                            if(outputItem != null) {
                                List<ItemStack> outputs = new ArrayList<>();
                                outputs.add(outputItem);

                                for(List<IconWithLabel> villagers : Lists.partition(entry.getValue(), 4)) {
                                    cookingRecipes.add(new VillagerCraftingRecipeData(inputs, outputs, culture, villagers));
                                }
                            }
                        }
                    }
                }
            } catch (Exception ex) {
                MillenaireJei.getLogger().error("Error loading villager crafting recipes for culture " + cultureKey, ex);
            }
        }
    }

    public List<VillagerCraftingRecipeData> getCraftingRecipes() {
        return craftingRecipes;
    }

    public List<VillagerCraftingRecipeData> getCookingRecipes() {
        return cookingRecipes;
    }
}
