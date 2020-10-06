package sguest.millenairejei.util;

import net.minecraft.client.Minecraft;

public class DenierHelper {
    public static void drawPrice(Minecraft minecraft, int price, int x, int y) {
        String suffix = "d";
        int color = 0xFFFFAA00;

        if(price > 64) {
            price = Math.floorDiv(price, 64);
            suffix = "a";
            color = 0xFFFFFFFF;
        }

        if(price > 64) {
            price = Math.floorDiv(price, 64);
            suffix = "o";
            color = 0xFFFFFF55;
        }

        minecraft.fontRenderer.drawString(Integer.toString(price) + suffix, x, y, color);
    }
}
