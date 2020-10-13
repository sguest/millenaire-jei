package sguest.millenairejei.millenairedata;

public class BuildingData {
    private String buildingKey;
    private String icon;

    public BuildingData(String buildingKey, String icon) {
        this.buildingKey = buildingKey;
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getBuildingKey() {
        return buildingKey;
    }
}
