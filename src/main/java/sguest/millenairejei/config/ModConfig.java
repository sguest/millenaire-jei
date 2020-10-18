package sguest.millenairejei.config;

import net.minecraftforge.common.config.Config;
import sguest.millenairejei.MillenaireJei;

@Config(modid = MillenaireJei.MODID)
@Config.LangKey("millenairejei.config.title")
public class ModConfig {
    @Config.Comment("Show recipes for buying from villagers")
    public static boolean showBuyingRecipes = true;

    @Config.Comment("Show recipes for selling to villagers")
    public static boolean showSellingRecipes = true;

    @Config.Comment("Show recipes for using paint buckets on painted bricks")
    public static boolean showPaintingRecipes = true;

    @Config.Comment("Show recipe for using brick mould")
    public static boolean showBrickMouldRecipes = true;

    @Config.Comment("Show recipe for drying bricks in the sun")
    public static boolean showDryingRecipes = true;

    @Config.Comment("Show recipes for villagers crafting items")
    public static boolean showVillagerCraftingRecipes = true;

    @Config.Comment("Show recipes for villagers cooking items")
    public static boolean showVillagerCookingRecipes = true;

    @Config.Comment("Show recipes for villagers harvesting items")
    public static boolean showVillagerHarvestingRecipes = true;

    @Config.Comment("Show recipes for villagers slaughtering animals")
    public static boolean showVillagerSlaughteringRecipes = true;

    @Config.Comment("Hide 'technical' blocks that players don't interact with from JEI lists")
    public static boolean hideTechnicalItems = true;
}
