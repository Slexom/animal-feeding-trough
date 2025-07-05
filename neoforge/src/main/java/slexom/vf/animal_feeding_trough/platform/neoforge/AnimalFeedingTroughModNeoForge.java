package slexom.vf.animal_feeding_trough.platform.neoforge;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.client.gui.screens.inventory.FeedingTroughScreen;

@Mod(AnimalFeedingTroughMod.MOD_ID)
public class AnimalFeedingTroughModNeoForge {

    public AnimalFeedingTroughModNeoForge(IEventBus modEventBus) {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            modEventBus.addListener(this::setupClient);
            modEventBus.addListener(this::registerScreens);
        }

        AnimalFeedingTroughMod.onInitialize();
    }

    private void setup(final FMLCommonSetupEvent event) {
    }

    private void setupClient(final FMLClientSetupEvent event) {
        // MenuScreens.register(AnimalFeedingTroughMod.FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
    }

    private void registerScreens(RegisterMenuScreensEvent event) {
        event.register(AnimalFeedingTroughMod.FEEDING_TROUGH_MENU.get(), FeedingTroughScreen::new);
    }
}
