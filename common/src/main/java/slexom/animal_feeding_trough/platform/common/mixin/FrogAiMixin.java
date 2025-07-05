package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.animal.frog.FrogAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(FrogAi.class)
public class FrogAiMixin {

    @ModifyArg(method = "initSwimActivity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;addActivityWithConditions(Lnet/minecraft/world/entity/schedule/Activity;Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"),
            index = 1)
    private static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> animal_feeding_trough$modifySwimActivity(
            ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> originalList) {

        return ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Frog>>>builder()
                .addAll(originalList)
                .add(Pair.of(1, new SelfFeed(livingEntity -> 1.25F)))
                .build();
    }

    @ModifyArg(method = "initIdleActivity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;addActivityWithConditions(Lnet/minecraft/world/entity/schedule/Activity;Lcom/google/common/collect/ImmutableList;Ljava/util/Set;)V"),
            index = 1)
    private static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> animal_feeding_trough$modifyIdleActivity(
            ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Frog>>> originalList) {

        return ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Frog>>>builder()
                .addAll(originalList)
                .add(Pair.of(1, new SelfFeed(livingEntity -> 1.25F)))
                .build();
    }
}