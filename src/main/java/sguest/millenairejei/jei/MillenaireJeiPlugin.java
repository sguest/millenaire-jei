package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.recipes.JeiBlacklist;
import sguest.millenairejei.recipes.RecipeData;
import sguest.millenairejei.recipes.RecipeLookup;
import sguest.millenairejei.millenairedata.MillenaireDataRegistry;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.ItemHelper;

import javax.annotation.Nonnull;

@JEIPlugin
public class MillenaireJeiPlugin implements IModPlugin {
    public static final String BUYING = MillenaireJei.MODID + ".buying";
    public static final String SELLING = MillenaireJei.MODID + ".selling";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.handleRecipes(RecipeData.class, data -> new BuyingRecipeWrapper(data, guiHelper), BUYING);
        registry.handleRecipes(RecipeData.class, data -> new SellingRecipeWrapper(data, guiHelper), SELLING);

        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_POUCH), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_ARGENT), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_OR), BUYING);

        MillenaireDataRegistry.getInstance().loadMillenaireData();
        RecipeLookup recipeLookup = new RecipeLookup();
        recipeLookup.buildRecipes();

        registry.addRecipes(recipeLookup.getBuyingRecipes(), BUYING);
        registry.addRecipes(recipeLookup.getSellingRecipes(), SELLING);

        JeiBlacklist.blacklistItems(registry.getJeiHelpers().getIngredientBlacklist());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BuyingRecipeCategory(guiHelper), new SellingRecipeCategory(guiHelper));
    }
}