package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.entity.ai.behavior.BehaviorControl;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.armadillo.Armadillo;
 import net.minecraft.world.entity.animal.armadillo.ArmadilloAi;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior.SelfFeed;

@Mixin(ArmadilloAi.class)
public class ArmadilloAiMixin {
    @Mutable
    @Shadow
    @Final
    private static ImmutableList<SensorType<? extends Sensor<? super Armadillo>>> SENSOR_TYPES;

    @Mutable
    @Shadow
    @Final
    private static ImmutableList<MemoryModuleType<?>> MEMORY_TYPES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void animal_feeding_trough$addFeedingTroughSensorAndMemory(CallbackInfo ci) {
        SENSOR_TYPES = ImmutableList.<SensorType<? extends Sensor<? super Armadillo>>>builder()
                .addAll(SENSOR_TYPES)
                .add(AnimalFeedingTroughMod.ARMADILLO_TEMPTATIONS.get())
                .build();

        MEMORY_TYPES = ImmutableList.<MemoryModuleType<?>>builder()
                .addAll(MEMORY_TYPES)
                .add(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get())
                .build();
    }

    @ModifyArg(method = "initIdleActivity",
            at = @At(value = "INVOKE",
                    target = "Lnet/minecraft/world/entity/ai/Brain;addActivity(Lnet/minecraft/world/entity/schedule/Activity;Lcom/google/common/collect/ImmutableList;)V"),
            index = 1)
    private static ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Armadillo>>> animal_feeding_trough$modifyIdleActivity(
            ImmutableList<Pair<Integer, ? extends BehaviorControl<? super Armadillo>>> originalList) {

        return ImmutableList.<Pair<Integer, ? extends BehaviorControl<? super Armadillo>>>builder()
                .addAll(originalList)
                .add(Pair.of(2, new SelfFeed(livingEntity -> 1.25F, livingEntity -> livingEntity.isBaby() ? 1.0 : 2.0)))
                .build();
    }
}
