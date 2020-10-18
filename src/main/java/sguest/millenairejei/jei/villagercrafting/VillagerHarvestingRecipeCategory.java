package sguest.millenairejei.jei.villagercrafting;

import mezz.jei.api.IGuiHelper;
import net.minecraft.client.resources.I18n;
import sguest.millenairejei.jei.MillenaireJeiPlugin;

public class VillagerHarvestingRecipeCategory extends BaseVillagerCraftingRecipeCategory {

    public VillagerHarvestingRecipeCategory(IGuiHelper guiHelper) {
        super(guiHelper, I18n.format("millenairejei.villagerharvesting.tabtitle"));
    }

    @Override
    public String getUid() {
        return MillenaireJeiPlugin.VILLAGER_HARVESTING;
    }
}