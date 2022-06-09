package slexom.animal_feeding_trough.platform.quilt;

import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.ModInitializer;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

public class AnimalFeedingTroughModQuilt implements ModInitializer {
    @Override
    public void onInitialize(ModContainer mod) {
        AnimalFeedingTroughMod.onInitialize();
    }


}

