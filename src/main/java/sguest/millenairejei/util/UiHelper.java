package sguest.millenairejei.util;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawable;
import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sguest.millenairejei.jei.DrawableWithLabel;
import sguest.millenairejei.recipes.IconWithLabel;

public class UiHelper {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation("jei", "textures/gui/gui_vanilla.png");

    public static IDrawable staticArrow(IGuiHelper guiHelper) {
        return guiHelper.createDrawable(GUI_TEXTURE, 25, 133, 24, 17);
    }

    public static IDrawable animatedArrow(IGuiHelper guiHelper, int ticks) {
        return guiHelper.drawableBuilder(GUI_TEXTURE, 82, 128, 24, 17)
            .buildAnimated(ticks, IDrawableAnimated.StartDirection.LEFT, false);
    }

    public static DrawableWithLabel toDrawable(IconWithLabel iconWithLabel, IGuiHelper guiHelper) {
        ItemStack itemStack = iconWithLabel.getIcon();
        IDrawable icon;
        if(itemStack == null) {
            icon = guiHelper.createBlankDrawable(16, 16);
        }
        else {
            icon = guiHelper.createDrawableIngredient(itemStack);
        }
        return new DrawableWithLabel(iconWithLabel.getLabel(), icon);
    }

    public static void renderIconWithLabel( Minecraft minecraft, int x, int y, DrawableWithLabel iconWithLabel) {
        iconWithLabel.getIcon().draw(minecraft, x, y);
        minecraft.fontRenderer.drawString(iconWithLabel.getLabel(), x + 20, y + 6, 0xFFFFFFFF);
    }
}
