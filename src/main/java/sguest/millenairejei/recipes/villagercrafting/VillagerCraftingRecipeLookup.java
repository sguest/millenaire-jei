package sguest.millenairejei.recipes.villagercrafting;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.CultureData;
import sguest.millenairejei.millenairedata.CultureDataLookup;
import sguest.millenairejei.millenairedata.CultureLanguageData;
import sguest.millenairejei.millenairedata.ItemLookup;
import sguest.millenairejei.millenairedata.LanguageLookup;
import sguest.millenairejei.millenairedata.villagercrafting.CraftingGoalData;
import sguest.millenairejei.millenairedata.villagercrafting.GoalLookup;
import sguest.millenairejei.millenairedata.villagercrafting.HarvestGoalData;
import sguest.millenairejei.millenairedata.villagercrafting.SlaughterGoalData;
import sguest.millenairejei.millenairedata.villagercrafting.VillagerData;
import sguest.millenairejei.millenairedata.villagercrafting.VillagerLookup;
import sguest.millenairejei.recipes.IconWithLabel;

public class VillagerCraftingRecipeLookup {
    private final List<VillagerCraftingRecipeData> craftingRecipes;
    private final List<VillagerCraftingRecipeData> cookingRecipes;
    private final List<VillagerCraftingRecipeData> harvestRecipes;
    private final List<VillagerSlaughterRecipeData> slaughterRecipes;

    public VillagerCraftingRecipeLookup() {
        craftingRecipes = new ArrayList<>();
        cookingRecipes = new ArrayList<>();
        harvestRecipes = new ArrayList<>();
        slaughterRecipes = new ArrayList<>();
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

                    HarvestGoalData harvestGoal = goalLookup.getHarvestGoal(goalKey);
                    if(harvestGoal != null) {
                        List<ItemStack> inputs = new ArrayList<>();
                        if(harvestGoal.getHarvestBlockState() != null) {
                            String[] stateParts = harvestGoal.getHarvestBlockState().split(";", 2);
                            Block harvestBlock = Block.REGISTRY.getObject(new ResourceLocation(stateParts[0]));
                            Item item = Item.getItemFromBlock(harvestBlock);

                            if(item.getHasSubtypes()) {
                                IBlockState blockState = harvestBlock.getBlockState().getBaseState();
                                if(stateParts.length == 2) {
                                    String[] stateItems = stateParts[1].split(",");
                                    Map<String, String> stateValues = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                                    for(String stateItem : stateItems) {
                                        String[] itemParts = stateItem.split("=", 2);
                                        if(itemParts.length == 2) {
                                            stateValues.put(itemParts[0], itemParts[1]);
                                        }
                                    }

                                    for(IProperty<?> property : blockState.getPropertyKeys()) {
                                        String propertyName = property.getName();
                                        if(stateValues.containsKey(propertyName)) {
                                            blockState = blockStateSetHelper(blockState, property, stateValues.get(propertyName));
                                        }
                                    }
                                }

                                int meta = harvestBlock.getMetaFromState(blockState);
                                inputs.add(new ItemStack(Item.getItemFromBlock(harvestBlock), 1, meta));
                            }
                            else {
                                inputs.add(new ItemStack(Item.getItemFromBlock(harvestBlock)));
                            }
                        }
                        else if(harvestGoal.getCropType() != null) {
                            Block harvestBlock = Block.REGISTRY.getObject(new ResourceLocation(harvestGoal.getCropType()));
                            inputs.add(new ItemStack(Item.getItemFromBlock(harvestBlock)));
                        }

                        List<ItemStack> outputs = new ArrayList<>();
                        for(String output : harvestGoal.getOutputs()) {
                            outputs.add(itemLookup.getItem(output));
                        }

                        for(List<IconWithLabel> villagers : Lists.partition(entry.getValue(), 4)) {
                            harvestRecipes.add(new VillagerCraftingRecipeData(inputs, outputs, culture, villagers));
                        }
                    }

                    SlaughterGoalData slaughterGoal = goalLookup.getSlaugherGoal(goalKey);
                    if(slaughterGoal != null) {
                        Class<? extends Entity> entityClass = EntityList.getClassFromName(slaughterGoal.getAnimal());
                        Entity entity = getEntity(entityClass);
                        String animalName = "";
                        if(entity != null) {
                            animalName = entity.getName();
                        }
                        List<ItemStack> outputs = new ArrayList<>();
                        for(String bonusItem : slaughterGoal.getBonusItems()) {
                            ItemStack item = itemLookup.getItem(bonusItem);
                            if(item != null) {
                                outputs.add(item);
                            }
                        }

                        for(List<IconWithLabel> villagers : Lists.partition(entry.getValue(), 4)) {
                            slaughterRecipes.add(new VillagerSlaughterRecipeData(animalName, outputs, culture, villagers));
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

    public List<VillagerCraftingRecipeData> getHarvestRecipes() {
        return harvestRecipes;
    }

    public List<VillagerSlaughterRecipeData> getSlaughterRecipes() {
        return slaughterRecipes;
    }

    private <T extends Comparable<T>> IBlockState blockStateSetHelper(IBlockState blockState, IProperty<T> property, String value) {
        com.google.common.base.Optional<T> propertyValue = property.parseValue(value);
        if(propertyValue.isPresent()) {
            blockState = blockState.withProperty(property, propertyValue.get());
        }
        return blockState;
    }

    private <T extends Entity> Entity getEntity(Class<T> entityClass) {
        try {
        Constructor<T> constructor = entityClass.getConstructor(World.class);
            return constructor.newInstance(Minecraft.getMinecraft().world);
        } catch (ReflectiveOperationException | RuntimeException ex) {
            return null;
        }
    }
}
