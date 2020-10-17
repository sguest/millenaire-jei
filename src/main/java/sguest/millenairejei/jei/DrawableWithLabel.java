package sguest.millenairejei.jei;

import mezz.jei.api.gui.IDrawable;

public class DrawableWithLabel {
    private final String label;
    private final IDrawable icon;

    public DrawableWithLabel(String label, IDrawable icon) {
        this.label = label;
        this.icon = icon;
    }

    public String getLabel() {
        return label;
    }

    public IDrawable getIcon() {
        return icon;
    }
}
