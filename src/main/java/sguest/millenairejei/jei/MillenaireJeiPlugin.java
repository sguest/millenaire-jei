package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.RecipeData;
import sguest.millenairejei.millenairedata.MillenaireDataRegistry;
import sguest.millenairejei.util.ItemHelper;

import javax.annotation.Nonnull;

@JEIPlugin
public class MillenaireJeiPlugin implements IModPlugin {
    public static final String BUYING = MillenaireJei.MODID + ".buying";
    public static final String SELLING = MillenaireJei.MODID + ".selling";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.handleRecipes(RecipeData.class, BuyingRecipeWrapper::new, BUYING);
        registry.handleRecipes(RecipeData.class, SellingRecipeWrapper::new, SELLING);

        registry.addRecipeCatalyst(ItemHelper.getStackFromResource("millenaire:purse"), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource("millenaire:denier"), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource("millenaire:denierargent"), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource("millenaire:denieror"), BUYING);

        MillenaireDataRegistry dataRegistry = MillenaireDataRegistry.getInstance();
        dataRegistry.loadMillenaireData();

        registry.addRecipes(dataRegistry.getBuyingRecipes(), BUYING);
        registry.addRecipes(dataRegistry.getSellingRecipes(), SELLING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BuyingRecipeCategory(guiHelper), new SellingRecipeCategory(guiHelper));
    }
}