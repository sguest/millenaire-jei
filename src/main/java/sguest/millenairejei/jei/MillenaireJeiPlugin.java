package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.jei.mudbrick.BrickMouldRecipeCategory;
import sguest.millenairejei.jei.mudbrick.BrickMouldRecipeWrapper;
import sguest.millenairejei.jei.mudbrick.DryingRecipeCategory;
import sguest.millenairejei.jei.mudbrick.DryingRecipeWrapper;
import sguest.millenairejei.jei.painting.PaintingRecipeCategory;
import sguest.millenairejei.jei.painting.PaintingRecipeWrapper;
import sguest.millenairejei.jei.trading.BuyingRecipeCategory;
import sguest.millenairejei.jei.trading.BuyingRecipeWrapper;
import sguest.millenairejei.jei.trading.SellingRecipeCategory;
import sguest.millenairejei.jei.trading.SellingRecipeWrapper;
import sguest.millenairejei.jei.villagercrafting.VillagerCookingRecipeCategory;
import sguest.millenairejei.jei.villagercrafting.VillagerCrafingRecipeWrapper;
import sguest.millenairejei.jei.villagercrafting.VillagerCraftingRecipeCategory;
import sguest.millenairejei.jei.villagercrafting.VillagerHarvestingRecipeCategory;
import sguest.millenairejei.jei.villagercrafting.VillagerSlaughteringRecipeCategory;
import sguest.millenairejei.jei.villagercrafting.VillagerSlaughteringRecipeWrapper;
import sguest.millenairejei.recipes.EmptyRecipeData;
import sguest.millenairejei.recipes.JeiBlacklist;
import sguest.millenairejei.recipes.painting.PaintingRecipeData;
import sguest.millenairejei.recipes.painting.PaintingRecipeLookup;
import sguest.millenairejei.recipes.trading.TradingRecipeData;
import sguest.millenairejei.recipes.trading.TradingRecipeLookup;
import sguest.millenairejei.recipes.villagercrafting.VillagerCraftingRecipeData;
import sguest.millenairejei.recipes.villagercrafting.VillagerCraftingRecipeLookup;
import sguest.millenairejei.recipes.villagercrafting.VillagerSlaughterRecipeData;
import sguest.millenairejei.millenairedata.MillenaireDataRegistry;
import sguest.millenairejei.util.Constants;
import sguest.millenairejei.util.ItemHelper;

import java.util.Collections;

import javax.annotation.Nonnull;

@JEIPlugin
public class MillenaireJeiPlugin implements IModPlugin {
    public static final String BUYING = MillenaireJei.MODID + ".buying";
    public static final String SELLING = MillenaireJei.MODID + ".selling";
    public static final String PAINTING = MillenaireJei.MODID + ".painting";
    public static final String BRICK_MOULD = MillenaireJei.MODID + ".brickmould";
    public static final String DRYING = MillenaireJei.MODID + ".drying";
    public static final String VILLAGER_CRAFTING = MillenaireJei.MODID + ".villagercrafting";
    public static final String VILLAGER_COOKING = MillenaireJei.MODID + ".villagercooking";
    public static final String VILLAGER_HARVESTING = MillenaireJei.MODID + ".villagerharvesting";
    public static final String VILLAGER_SLAUGHTERING = MillenaireJei.MODID + ".villagerslaughtering";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.handleRecipes(TradingRecipeData.class, data -> new BuyingRecipeWrapper(data, guiHelper), BUYING);
        registry.handleRecipes(TradingRecipeData.class, data -> new SellingRecipeWrapper(data, guiHelper), SELLING);
        registry.handleRecipes(PaintingRecipeData.class, PaintingRecipeWrapper::new, PAINTING);
        registry.handleRecipes(EmptyRecipeData.class, o -> new BrickMouldRecipeWrapper(), BRICK_MOULD);
        registry.handleRecipes(EmptyRecipeData.class, o -> new DryingRecipeWrapper(), DRYING);
        registry.handleRecipes(VillagerCraftingRecipeData.class, data -> new VillagerCrafingRecipeWrapper(data, guiHelper), VILLAGER_CRAFTING);
        registry.handleRecipes(VillagerCraftingRecipeData.class, data -> new VillagerCrafingRecipeWrapper(data, guiHelper), VILLAGER_COOKING);
        registry.handleRecipes(VillagerCraftingRecipeData.class, data -> new VillagerCrafingRecipeWrapper(data, guiHelper), VILLAGER_HARVESTING);
        registry.handleRecipes(VillagerSlaughterRecipeData.class, data -> new VillagerSlaughteringRecipeWrapper(data, guiHelper), VILLAGER_SLAUGHTERING);

        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_POUCH), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_ARGENT), BUYING);
        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.DENIER_OR), BUYING);

        registry.addRecipeCatalyst(ItemHelper.getStackFromResource(Constants.BRICK_MOULD), BRICK_MOULD);

        MillenaireDataRegistry.getInstance().loadMillenaireData();
        TradingRecipeLookup tradingLookup = new TradingRecipeLookup();
        tradingLookup.buildRecipes();

        registry.addRecipes(tradingLookup.getBuyingRecipes(), BUYING);
        registry.addRecipes(tradingLookup.getSellingRecipes(), SELLING);

        PaintingRecipeLookup paintingLookup = new PaintingRecipeLookup();
        paintingLookup.buildRecipes();

        registry.addRecipes(paintingLookup.getRecipes(), PAINTING);

        registry.addRecipes(Collections.singletonList(new EmptyRecipeData()), BRICK_MOULD);
        registry.addRecipes(Collections.singletonList(new EmptyRecipeData()), DRYING);

        VillagerCraftingRecipeLookup villagerCraftingLookup = new VillagerCraftingRecipeLookup();
        villagerCraftingLookup.BuildRecipes();

        registry.addRecipes(villagerCraftingLookup.getCraftingRecipes(), VILLAGER_CRAFTING);
        registry.addRecipes(villagerCraftingLookup.getCookingRecipes(), VILLAGER_COOKING);
        registry.addRecipes(villagerCraftingLookup.getHarvestRecipes(), VILLAGER_HARVESTING);
        registry.addRecipes(villagerCraftingLookup.getSlaughterRecipes(), VILLAGER_SLAUGHTERING);

        JeiBlacklist.blacklistItems(registry.getJeiHelpers().getIngredientBlacklist());
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(
            new BuyingRecipeCategory(guiHelper),
            new SellingRecipeCategory(guiHelper),
            new PaintingRecipeCategory(guiHelper),
            new BrickMouldRecipeCategory(guiHelper),
            new DryingRecipeCategory(guiHelper),
            new VillagerCraftingRecipeCategory(guiHelper),
            new VillagerCookingRecipeCategory(guiHelper),
            new VillagerHarvestingRecipeCategory(guiHelper),
            new VillagerSlaughteringRecipeCategory(guiHelper)
        );
    }
}