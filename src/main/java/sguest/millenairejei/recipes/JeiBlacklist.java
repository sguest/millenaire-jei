package sguest.millenairejei.recipes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import mezz.jei.api.ingredients.IIngredientBlacklist;
import net.minecraftforge.oredict.OreDictionary;
import sguest.millenairejei.MillenaireJei;
import sguest.millenairejei.util.ItemHelper;

public class JeiBlacklist {
    public static void blacklistItems(IIngredientBlacklist blacklist) {
        InputStream inputStream = MillenaireJei.class.getResourceAsStream("/assets/" + MillenaireJei.MODID + "/jeiblacklist.txt");

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while((line = reader.readLine()) != null) {
                blacklist.addIngredientToBlacklist(ItemHelper.getStackFromResourceAndMeta(line, OreDictionary.WILDCARD_VALUE));
            }
        } catch(IOException ex) {
            MillenaireJei.getLogger().error("Failed to load blacklist file ", ex);
        }
    }
}
