package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.animal.axolotl.Axolotl;
import net.minecraft.world.entity.animal.axolotl.AxolotlAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeedAxolotl;

@Mixin(AxolotlAi.class)
public class AxolotlAiMixin {

    @ModifyArg(method = "initIdleActivity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;addActivity(Lnet/minecraft/world/entity/schedule/Activity;Lcom/google/common/collect/ImmutableList;)V"),
            index = 1)
    private static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Axolotl>>> animal_feeding_trough$modifyIdleActivity(
            ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Axolotl>>> originalList) {

        return ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Axolotl>>>builder()
                .addAll(originalList)
                .add(Pair.of(2, new SelfFeedAxolotl(AxolotlAi::getSpeedModifier)))
                .build();
    }
}
