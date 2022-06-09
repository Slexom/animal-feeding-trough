package slexom.animal_feeding_trough.platform.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

public class AnimalFeedingTroughClientModQuilt implements ClientModInitializer {
    @Override
    public void onInitializeClient(ModContainer mod) {
        AnimalFeedingTroughMod.onInitializeClient();
    }


}

