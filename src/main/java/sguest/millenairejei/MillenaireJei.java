package sguest.millenairejei;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import sguest.millenairejei.millenairedata.MillenaireDataRegistry;

import java.nio.file.Path;

import org.apache.logging.log4j.Logger;

@Mod(modid = MillenaireJei.MODID, name = MillenaireJei.NAME, version = MillenaireJei.VERSION, dependencies="required-after:jei;required-after:millenaire")
public class MillenaireJei
{
    public static final String MODID = "millenairejei";
    public static final String NAME = "Millenaire JEI Plugin";
    public static final String VERSION = "0.1";

    private static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        logger = event.getModLog();
        Path configPath = event.getModConfigurationDirectory().toPath();
        Path modsDirectory = configPath.resolve("../mods").toAbsolutePath().normalize();
        MillenaireDataRegistry.getInstance().setModsDirectory(modsDirectory);
    }

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
    }
}
