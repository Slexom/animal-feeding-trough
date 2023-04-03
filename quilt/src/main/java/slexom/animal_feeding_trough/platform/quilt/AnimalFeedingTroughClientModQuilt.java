package slexom.animal_feeding_trough.platform.quilt;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.loader.api.minecraft.ClientOnly;
import org.quiltmc.qsl.base.api.entrypoint.client.ClientModInitializer;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

public class AnimalFeedingTroughClientModQuilt implements ClientModInitializer {

    @Override
    @ClientOnly
    public void onInitializeClient(ModContainer mod) {
        AnimalFeedingTroughMod.onInitializeClient();
    }

}

