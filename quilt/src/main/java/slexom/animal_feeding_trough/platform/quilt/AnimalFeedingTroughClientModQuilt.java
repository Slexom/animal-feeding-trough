package slexom.animal_feeding_trough.platform.quilt;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

public class AnimalFeedingTroughClientModQuilt implements ClientModInitializer {

    @Override
    @Environment(EnvType.CLIENT)
    public void onInitializeClient(ModContainer mod) {
        AnimalFeedingTroughMod.onInitializeClient();
    }

}

