package slexom.vf.animal_feeding_trough.forge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

@Mod("animal_feeding_trough")
public class AnimalFeedingTroughModForge {

    public AnimalFeedingTroughModForge() {
        EventBuses.registerModEventBus(AnimalFeedingTroughMod.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        AnimalFeedingTroughMod.onInitialize();
        AnimalFeedingTroughMod.onInitializeClient();
    }


}
