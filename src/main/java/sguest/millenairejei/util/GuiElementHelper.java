package sguest.millenairejei.util;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.util.ResourceLocation;

public class GuiElementHelper {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

    public static IDrawable staticArrow(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(GUI_TEXTURE, 25, 133, 24, 17);
    }

    public static IDrawable animatedArrow(IGuiHelper guiHelper, int ticks) {
        return guiHelper.drawableBuilder(GUI_TEXTURE, 82, 128, 24, 17)
            .buildAnimated(ticks, IDrawableAnimated.StartDirection.LEFT, false);
    }
}
