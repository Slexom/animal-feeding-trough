package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.ActivityData;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.camel.Camel;
import net.minecraft.world.entity.animal.camel.CamelAi;
import net.minecraft.world.entity.schedule.Activity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(CamelAi.class)
public class CamelAiMixin {

	@Inject(
			method = "initIdleActivity",
			at = @At("RETURN"),
			cancellable = true
	)
	private static void animal_feeding_trough$initIdleActivity(
			CallbackInfoReturnable<ActivityData<Camel>> cir
	) {
		ActivityData<Camel> original = cir.getReturnValue();

		ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Camel>>> newBehaviors =
				ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Camel>>>builder()
						.addAll(original.behaviorPriorityPairs())
						.add(Pair.of(2, new SelfFeed(
								_ -> 1.25F,
								livingEntity -> livingEntity.isBaby() ? 1.0 : 2.0
						)))
						.build();

		cir.setReturnValue(ActivityData.create(Activity.IDLE, newBehaviors));
	}
}
