package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
 import net.minecraft.world.entity.animal.goat.Goat;
import net.minecraft.world.entity.animal.goat.GoatAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(GoatAi.class)
public class GoatAiMixin {
    @ModifyArg(method = "initIdleActivity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;addActivityWithConditions(Lnet/minecraft/world/entity/schedule/Activity;Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"),
            index = 1)
    private static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Goat>>> animal_feeding_trough$modifyIdleActivity(
            ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Goat>>> originalList) {

        return ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Goat>>>builder()
                .addAll(originalList)
                .add(Pair.of(5, new SelfFeed(livingEntity -> 1.25F)))
                .build();
    }
}
