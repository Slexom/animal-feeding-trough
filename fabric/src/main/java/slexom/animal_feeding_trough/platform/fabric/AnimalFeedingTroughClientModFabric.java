package slexom.animal_feeding_trough.platform.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

@Environment(EnvType.CLIENT)
public class AnimalFeedingTroughClientModFabric implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        AnimalFeedingTroughMod.onInitializeClient();
    }

}
