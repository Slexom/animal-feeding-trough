package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;


@Mixin(Axolotl.class)
public class AxolotlMixin {

	@Inject(method = "makeBrain", at = @At("RETURN"))
	private void animal_feeding_trough$injectSensor(
			Brain.Packed packedBrain,
			CallbackInfoReturnable<Brain<Axolotl>> cir
	) {
		Brain<Axolotl> brain = cir.getReturnValue();

		BrainAccessor<Axolotl> accessor = (BrainAccessor<Axolotl>) brain;

		accessor.animal_feeding_trough$registerMemory(
				AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get()
		);

		accessor.animal_feeding_trough$getSensors().put(
				AnimalFeedingTroughMod.AXOLOTL_TEMPTATIONS.get(),
				new FeedingTroughSensor(stack -> stack.is(Items.TROPICAL_FISH))
		);
	}
}
