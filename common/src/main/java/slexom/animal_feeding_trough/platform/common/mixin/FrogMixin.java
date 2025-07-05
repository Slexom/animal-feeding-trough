package slexom.animal_feeding_trough.platform.common.mixin;

import com.google.common.collect.ImmutableList;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.frog.Frog;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

@Mixin(Frog.class)
public class FrogMixin {

    @Mutable
    @Shadow
    @Final
    protected static ImmutableList<SensorType<? extends Sensor<? super Frog>>> SENSOR_TYPES;

    @Mutable
    @Shadow
    @Final
    protected static ImmutableList<MemoryModuleType<?>> MEMORY_TYPES;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void animal_feeding_trough$addFeedingTroughSensorAndMemory(CallbackInfo ci) {
        SENSOR_TYPES = ImmutableList.<SensorType<? extends Sensor<? super Frog>>>builder()
                .addAll(SENSOR_TYPES)
                .add(AnimalFeedingTroughMod.FROG_TEMPTATIONS.get())
                .build();

        MEMORY_TYPES = ImmutableList.<MemoryModuleType<?>>builder()
                .addAll(MEMORY_TYPES)
                .add(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get())
                .build();
    }
}