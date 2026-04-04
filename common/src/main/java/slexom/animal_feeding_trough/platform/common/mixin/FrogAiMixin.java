package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(FrogAi.class)
public class FrogAiMixin {

	@Inject(
			method = "initSwimActivity",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void animal_feeding_trough$initSwimActivity(
			CallbackInfoReturnable<ActivityData<Frog>> cir
	) {
		ActivityData<Frog> original = cir.getReturnValue();

		ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> newBehaviors =
				ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Frog>>>builder()
						.addAll(original.behaviorPriorityPairs())
						.add(Pair.of(1, new SelfFeed(_ -> 1.25F)))
						.build();

		cir.setReturnValue(ActivityData.create(Activity.SWIM, newBehaviors));
	}

	@Inject(
			method = "initIdleActivity",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void animal_feeding_trough$initIdleActivity(
			CallbackInfoReturnable<ActivityData<Frog>> cir
	) {
		ActivityData<Frog> original = cir.getReturnValue();

		ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> newBehaviors =
				ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Frog>>>builder()
						.addAll(original.behaviorPriorityPairs())
						.add(Pair.of(1, new SelfFeed(_ -> 1.25F)))
						.build();

		cir.setReturnValue(ActivityData.create(Activity.IDLE, newBehaviors));
	}
}