package sguest.millenairejei.millenairedata.villagercrafting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.io.FilenameUtils;

import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.DataFileHelper;

public class GoalLookup {
    private static GoalLookup instance;

    private final Map<String, CraftingGoalData> craftingGoals;
    private final Map<String, String> cookingGoals;
    private final Map<String, HarvestGoalData> harvestGoals;

    public static GoalLookup getInstance() {
        if(instance == null) {
            instance = new GoalLookup();
        }
        return instance;
    }

    private GoalLookup() {
        craftingGoals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        cookingGoals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        harvestGoals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
    }

    public void loadGoals(Path dataFolder) {
        Path goalsFolder = dataFolder.resolve("goals");

        Path craftingFolder = goalsFolder.resolve("genericcrafting");
        if(craftingFolder.toFile().exists()) {
            try {
                Files.walk(craftingFolder).filter(Files::isRegularFile).forEach(file -> {
                    String key = FilenameUtils.getBaseName(file.toFile().getName());
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        Map<String, Integer> inputs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                        if(fileData.containsKey("input")) {
                            for(String input : fileData.get("input")) {
                                String[] parts = input.split(",", 2);
                                if(parts.length == 2) {
                                    inputs.put(parts[0], Integer.parseInt(parts[1]));
                                }
                            }
                        }

                        Map<String, Integer> outputs = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
                        if(fileData.containsKey("output")) {
                            for(String output : fileData.get("output")) {
                                String[] parts = output.split(",", 2);
                                if(parts.length == 2) {
                                    outputs.put(parts[0], Integer.parseInt(parts[1]));
                                }
                            }
                        }

                        craftingGoals.put(key, new CraftingGoalData(inputs, outputs));
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load crafting goals from folder " + goalsFolder, ex);
            }
        }

        Path cookingFolder = goalsFolder.resolve("genericcooking");
        if(cookingFolder.toFile().exists()) {
            try {
                Files.walk(cookingFolder).filter(Files::isRegularFile).forEach(file -> {
                    String key = FilenameUtils.getBaseName(file.toFile().getName());
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        List<String> toCook = fileData.get("itemtocook");
                        if(toCook != null) {
                            cookingGoals.put(key, toCook.get(0));
                        }
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load cooking goals from folder " + goalsFolder, ex);
            }
        }

        loadHarvestGoals(goalsFolder.resolve("genericgatherblocks"));
        loadHarvestGoals(goalsFolder.resolve("genericharvesting"));

        Path miningFolder = goalsFolder.resolve("genericmining");
        if(miningFolder.toFile().exists()) {
            try {
                Files.walk(miningFolder).filter(Files::isRegularFile).forEach(file -> {
                    String key = FilenameUtils.getBaseName(file.toFile().getName());
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        List<String> loot = fileData.get("loot");
                        if(loot != null) {
                            String[] parts = loot.get(0).split(",", 2);
                            List<String> outputs = new ArrayList<>();
                            outputs.add(parts[0]);
                            harvestGoals.put(key, new HarvestGoalData(null, null, outputs));
                        }
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load cooking goals from folder " + goalsFolder, ex);
            }
        }
    }

    public CraftingGoalData getCrafingGoal(String key) {
        return craftingGoals.get(key);
    }

    public String getCookingGoalInput(String key) {
        return cookingGoals.get(key);
    }

    public HarvestGoalData getHarvestGoal(String key) {
        return harvestGoals.get(key);
    }

    private void loadHarvestGoals(Path loadingFolder) {
        if(loadingFolder.toFile().exists()) {
            try {
                Files.walk(loadingFolder).filter(Files::isRegularFile).forEach(file -> {
                    String key = FilenameUtils.getBaseName(file.toFile().getName());
                    Map<String, List<String>> fileData = DataFileHelper.loadDataFile(file.toFile());
                    if(fileData != null) {
                        if(fileData.get("leasure") == null || fileData.get("leasure").get(0) != "true") {
                            String gatherBlockState = null;
                            if(fileData.containsKey("gatherBlockState")) {
                                gatherBlockState = fileData.get("gatherBlockState").get(0);
                            }
                            else if(fileData.containsKey("harvestBlockState")) {
                                gatherBlockState = fileData.get("harvestBlockState").get(0);
                            }
                            String cropType = null;
                            if(fileData.containsKey("croptype")) {
                                cropType = fileData.get("croptype").get(0);
                            }
                            List<String> outputs = new ArrayList<>();
                            if(fileData.containsKey("harvestitem")) {
                                for(String harvestItem : fileData.get("harvestitem")) {
                                    String[] parts = harvestItem.split(",", 2);
                                    if(parts.length == 2 && Integer.parseInt(parts[1]) > 0 && !outputs.contains(parts[0])) {
                                        outputs.add(parts[0]);
                                    }
                                }
                            }
                            if(outputs.size() > 0) {
                                harvestGoals.put(key, new HarvestGoalData(gatherBlockState, cropType, outputs));
                            }
                        }
                        List<String> toCook = fileData.get("itemtocook");
                        if(toCook != null) {
                            cookingGoals.put(key, toCook.get(0));
                        }
                    }
                });
            } catch (IOException ex) {
                MillenaireJei.getLogger().error("Failed to load harvest goals from folder " + loadingFolder, ex);
            }
        }
    }
}
