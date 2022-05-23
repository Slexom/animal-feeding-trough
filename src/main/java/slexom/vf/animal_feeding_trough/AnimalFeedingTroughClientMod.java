package slexom.vf.animal_feeding_trough;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import slexom.vf.animal_feeding_trough.screen.FeedingTroughScreen;

@Environment(EnvType.CLIENT)
public class AnimalFeedingTroughClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HandledScreens.register(AnimalFeedingTroughMod.FEEDING_TROUGH_SCREEN_HANDLER, FeedingTroughScreen::new);
    }

}
