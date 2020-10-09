package sguest.millenairejei.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;

public class DenierHelper {
    public static void drawPrice(Minecraft minecraft, int price, int x, int y) {
        int color = 0xFFFFAA00;
        if(price >= 64 * 64) {
            color = 0xFFFFFF55;
        }
        else if(price >= 64) {
            color = 0xFFFFFFFF;
        }

        List<String> pieces = new ArrayList<>();

        if(price >= 64 * 64) {
            pieces.add(Math.floorDiv(price, 64 * 64) + "o");
            price %= (64 * 64);
        }

        if(price >= 64) {
            pieces.add(Math.floorDiv(price, 64) + "a");
            price %= 64;
        }

        if(price > 0) {
            pieces.add(price + "d");
        }

        minecraft.fontRenderer.drawString(String.join(" ", pieces), x, y, color);
    }
}
