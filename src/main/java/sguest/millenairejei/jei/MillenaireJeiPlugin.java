package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.BuyingRecipeEntry;
import sguest.millenairejei.millenairedata.MillenaireDataRegistry;

import javax.annotation.Nonnull;

@JEIPlugin
public class MillenaireJeiPlugin implements IModPlugin {
    public static final String BUYING = MillenaireJei.MODID + ".buying";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.handleRecipes(BuyingRecipeEntry.class, BuyingRecipeWrapper::new, BUYING);
        MillenaireDataRegistry dataRegistry = MillenaireDataRegistry.getInstance();
        dataRegistry.loadMillenaireData();
        //BuyingRecipeRegistry.getInstance().AddEntry(new BuyingRecipeEntry(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minecraft:apple"))), 70));
        registry.addRecipes(dataRegistry.getBuyingRecipes(), BUYING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BuyingRecipeCategory(guiHelper));
    }
}