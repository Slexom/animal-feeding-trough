package slexom.vf.animal_feeding_trough.neoforge;

import dev.architectury.platform.forge.EventBuses;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreen;

@Mod(AnimalFeedingTroughMod.MOD_ID)
public class AnimalFeedingTroughModNeoForge {

	public AnimalFeedingTroughModNeoForge() {
		IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
		EventBuses.registerModEventBus(AnimalFeedingTroughMod.MOD_ID, eventBus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> (DistExecutor.SafeRunnable) () -> eventBus.addListener(this::setupClient));

		AnimalFeedingTroughMod.onInitialize();
	}

	private void setup(final FMLCommonSetupEvent event) {
	}

	@OnlyIn(Dist.CLIENT)
	private void setupClient(final FMLClientSetupEvent event) {
		HandledScreens.register(AnimalFeedingTroughMod.FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
	}

}
