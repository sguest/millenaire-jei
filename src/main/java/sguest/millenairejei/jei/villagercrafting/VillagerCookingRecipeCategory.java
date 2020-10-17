package sguest.millenairejei.jei.villagercrafting;

import mezz.jei.api.IGuiHelper;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.jei.MillenaireJeiPlugin;

public class VillagerCookingRecipeCategory extends BaseVillagerCraftingRecipeCategory {
    public VillagerCookingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, I18n.format("millenairejei.villagercooking.tabtitle"));
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.VILLAGER_COOKING;
    }
}