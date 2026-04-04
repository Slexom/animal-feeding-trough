package slexom.animal_feeding_trough.platform.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screens.MenuScreens;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.client.gui.screens.inventory.FeedingTroughScreen;

@Environment(EnvType.CLIENT)
public class AnimalFeedingTroughClientModFabric implements ClientModInitializer {

	@Override
	public void onInitializeClient() {

		MenuScreens.register(
				AnimalFeedingTroughMod.FEEDING_TROUGH_MENU.get(),
				FeedingTroughScreen::new
		);

	}
}
