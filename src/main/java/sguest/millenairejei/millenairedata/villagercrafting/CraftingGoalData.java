package sguest.millenairejei.millenairedata.villagercrafting;

import java.util.Map;

public class CraftingGoalData {
    private final Map<String, Integer> inputs;
    private final Map<String, Integer> outputs;

    public CraftingGoalData(Map<String, Integer> inputs, Map<String, Integer> outputs) {
        this.inputs = inputs;
        this.outputs = outputs;
    }

    public Map<String, Integer> getInputs() {
        return inputs;
    }

    public Map<String, Integer> getOutputs() {
        return outputs;
    }
}
