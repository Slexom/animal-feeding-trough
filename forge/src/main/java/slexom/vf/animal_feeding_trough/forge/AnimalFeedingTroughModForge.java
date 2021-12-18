package slexom.vf.animal_feeding_trough.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.IExtensionPoint;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreen;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

@Mod(AnimalFeedingTroughMod.MOD_ID)
public class AnimalFeedingTroughModForge {

    public AnimalFeedingTroughModForge() {
        EventBuses.registerModEventBus(AnimalFeedingTroughMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AnimalFeedingTroughMod.onInitialize();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event)    {
        AnimalFeedingTroughMod.onInitializeClient();
    }

}
