package slexom.vf.animal_feeding_trough.platform.neoforge;


import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.client.gui.screens.inventory.FeedingTroughScreen;


@EventBusSubscriber(
		value = Dist.CLIENT,
		modid = AnimalFeedingTroughMod.MOD_ID
)
public class AnimalFeedingTroughModNeoForgeClient {

	@SubscribeEvent
	public static void onRegisterScreens(RegisterMenuScreensEvent event) {
		event.register(
				AnimalFeedingTroughMod.FEEDING_TROUGH_MENU.get(),
				FeedingTroughScreen::new
		);
	}

}