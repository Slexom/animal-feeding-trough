package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.goat.GoatAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(GoatAi.class)
public class GoatAiMixin {

	@Inject(
			method = "initIdleActivity",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void animal_feeding_trough$modifyIdleActivity(
			CallbackInfoReturnable<ActivityData<Goat>> cir
	) {
		ActivityData<Goat> original = cir.getReturnValue();

		ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Goat>>> newBehaviors =
				ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Goat>>>builder()
						.addAll(original.behaviorPriorityPairs())
						.add(Pair.of(5, new SelfFeed(_ -> 1.25F)))
						.build();

		cir.setReturnValue(ActivityData.create(Activity.IDLE, newBehaviors));
	}
}
