package sguest.millenairejei.util;

import java.util.ArrayList;
import java.util.List;

public class AnimalHelper {
    public static List<String> getPassiveMobDrops(String animal) {
        List<String> drops = new ArrayList<>();

        switch(animal) {
            case "chicken":
                drops.add("feather");
                drops.add("chickenmeat");
                break;
            case "sheep":
                drops.add("wool_white");
                drops.add("mutton");
                break;
            case "cow":
                drops.add("leather");
                drops.add("beefraw");
                break;
            case "pig":
                drops.add("porkchops");
                break;
            case "squid":
                drops.add("dye_black");
                break;
        }

        return drops;
    }
}
