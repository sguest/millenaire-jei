package sguest.millenairejei.millenairedata.villagercrafting;

import java.util.List;

public class SlaughterGoalData {
    private final String animal;
    private final List<String> bonusItems;

    public SlaughterGoalData(String animal, List<String> bonusItems) {
        this.animal = animal;
        this.bonusItems = bonusItems;
    }

    public String getAnimal() {
        return animal;
    }

    public List<String> getBonusItems() {
        return bonusItems;
    }
}
