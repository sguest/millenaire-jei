package sguest.millenairejei.jei;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.millenairedata.BuyingRecipeEntry;
import sguest.millenairejei.millenairedata.BuyingRecipeRegistry;

import javax.annotation.Nonnull;

@JEIPlugin
public class MillenaireJeiPlugin implements IModPlugin {
    public static final String BUYING = MillenaireJei.MODID + ".buying";

    @Override
    public void register(@Nonnull IModRegistry registry) {
        registry.handleRecipes(BuyingRecipeEntry.class, BuyingRecipeWrapper::new, BUYING);
        BuyingRecipeRegistry.getInstance().AddEntry(new BuyingRecipeEntry(new ItemStack(Item.REGISTRY.getObject(new ResourceLocation("minecraft:apple"))), 70));
        registry.addRecipes(BuyingRecipeRegistry.getInstance().getBuyingRecipes(), BUYING);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry) {
        IGuiHelper guiHelper = registry.getJeiHelpers().getGuiHelper();
        registry.addRecipeCategories(new BuyingRecipeCategory(guiHelper));
    }
}