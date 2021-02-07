package slexom.vf.animal_self_feed;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.screenhandler.v1.ScreenRegistry;
import slexom.vf.animal_self_feed.screen.FoodContainerScreen;

@Environment(EnvType.CLIENT)
public class AnimalSelfFeedClientMod implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ScreenRegistry.register(AnimalSelfFeedMod.FOOD_CONTAINER_SCREEN_HANDLER, FoodContainerScreen::new);
    }

}
