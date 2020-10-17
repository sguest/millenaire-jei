package sguest.millenairejei.millenairedata.villagercrafting;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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

    public static GoalLookup getInstance() {
        if(instance == null) {
            instance = new GoalLookup();
        }
        return instance;
    }

    private GoalLookup() {
        craftingGoals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        cookingGoals = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
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
                MillenaireJei.getLogger().error("Failed to load generic crafting goals from folder " + goalsFolder, ex);
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
                MillenaireJei.getLogger().error("Failed to load generic crafting goals from folder " + goalsFolder, ex);
            }
        }
    }

    public CraftingGoalData getCrafingGoal(String key) {
        return craftingGoals.get(key);
    }

    public String getCookingGoalInput(String key) {
        return cookingGoals.get(key);
    }
}
