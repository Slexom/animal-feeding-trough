package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;

@Mixin(Armadillo.class)
public class ArmadilloMixin {

	@Inject(method = "makeBrain", at = @At("RETURN"))
	private void animal_feeding_trough$injectSensor(
			Brain.Packed packedBrain,
			CallbackInfoReturnable<Brain<Armadillo>> cir
	) {
		Brain<Armadillo> brain = cir.getReturnValue();

		BrainAccessor<Armadillo> accessor = (BrainAccessor<Armadillo>) brain;

		accessor.animal_feeding_trough$registerMemory(
				AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get()
		);

		accessor.animal_feeding_trough$getSensors().put(
				AnimalFeedingTroughMod.ARMADILLO_TEMPTATIONS.get(),
				new FeedingTroughSensor(stack -> stack.is(ItemTags.ARMADILLO_FOOD))
		);
	}
}
