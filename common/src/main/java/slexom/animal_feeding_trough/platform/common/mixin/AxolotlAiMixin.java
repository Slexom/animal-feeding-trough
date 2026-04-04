package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeedAxolotl;

@Mixin(AxolotlAi.class)
public class AxolotlAiMixin {

	@Inject(
			method = "initIdleActivity",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void animal_feeding_trough$initIdleActivity(
			CallbackInfoReturnable<ActivityData<Axolotl>> cir
	) {
		ActivityData<Axolotl> original = cir.getReturnValue();

		ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Axolotl>>> newBehaviors =
				ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Axolotl>>>builder()
						.addAll(original.behaviorPriorityPairs())
						.add(Pair.of(2, new SelfFeedAxolotl(AxolotlAi::getSpeedModifier, livingEntity -> livingEntity.isBaby() ? 1.0 : 2.0)))
						.build();

		cir.setReturnValue(ActivityData.create(Activity.IDLE, newBehaviors));
	}
}
