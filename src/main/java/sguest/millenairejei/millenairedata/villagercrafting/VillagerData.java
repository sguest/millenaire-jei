package sguest.millenairejei.millenairedata.villagercrafting;

import java.util.List;

public class VillagerData {
    private final String icon;
    private final List<String> goals;

    public VillagerData(String icon, List<String> goals) {
        this.icon = icon;
        this.goals = goals;
    }

    public String getIcon() {
        return icon;
    }

    public List<String> getGoals() {
        return goals;
    }
}
