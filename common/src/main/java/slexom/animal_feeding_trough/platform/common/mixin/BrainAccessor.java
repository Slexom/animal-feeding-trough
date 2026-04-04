package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Map;

@Mixin(Brain.class)
public interface BrainAccessor<E> {

	@Accessor("sensors")
	Map<SensorType<? extends Sensor<? super E>>, Sensor<? super E>> animal_feeding_trough$getSensors();

	@Accessor("memories")
	Map<MemoryModuleType<?>, ?> animal_feeding_trough$getMemories();

	@Invoker("registerMemory")
	void animal_feeding_trough$registerMemory(MemoryModuleType<?> type);
}