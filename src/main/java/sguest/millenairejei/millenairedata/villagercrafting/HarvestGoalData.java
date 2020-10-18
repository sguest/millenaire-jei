package sguest.millenairejei.millenairedata.villagercrafting;

import java.util.List;

public class HarvestGoalData {
    private final String harvestBlockState;
    private final String cropType;
    private final List<String> outputs;

    public HarvestGoalData(String harvestBlockState, String cropType, List<String> outputs) {
        this.harvestBlockState = harvestBlockState;
        this.cropType = cropType;
        this.outputs = outputs;
    }

    public String getHarvestBlockState() {
        return harvestBlockState;
    }

    public String getCropType() {
        return cropType;
    }

    public List<String> getOutputs() {
        return outputs;
    }
}
