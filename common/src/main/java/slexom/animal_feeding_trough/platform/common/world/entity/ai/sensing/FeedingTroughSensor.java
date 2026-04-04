package slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing;

import com.google.common.collect.ImmutableSet;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.Set;
import java.util.function.Predicate;


public class FeedingTroughSensor extends Sensor<PathfinderMob> {
	private final Predicate<ItemStack> temptations;

	public FeedingTroughSensor(Predicate<ItemStack> predicate) {
		this.temptations = predicate;
	}

	protected void doTick(ServerLevel level, PathfinderMob pathfinderMob) {
		Brain<?> brain = pathfinderMob.getBrain();
		MemoryModuleType<FeedingTroughBlockEntity> memoryModule = AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get();

		BlockPos center = pathfinderMob.blockPosition();
		int radius = Math.max(10, (int) pathfinderMob.getAttributeValue(Attributes.TEMPT_RANGE));

		FeedingTroughBlockEntity closest = null;
		double closestDistance = Double.MAX_VALUE;

		for (BlockPos pos : BlockPos.withinManhattan(center, radius, radius, radius)) {
			if (!pos.closerThan(center, radius)) {
				continue;
			}

			BlockEntity blockEntity = level.getBlockEntity(pos);
			if (!(blockEntity instanceof FeedingTroughBlockEntity trough)) {
				continue;
			}

			if (trough.getItems().isEmpty()) {
				continue;
			}

			ItemStack stack = trough.getItems().getFirst();

			if (!this.temptations.test(stack)) {
				continue;
			}

			double distance = pathfinderMob.distanceToSqr(
					pos.getX() + 0.5,
					pos.getY() + 0.5,
					pos.getZ() + 0.5
			);

			if (distance < closestDistance) {
				closestDistance = distance;
				closest = trough;
			}
		}

		if (closest != null) {
			brain.setMemory(memoryModule, closest);
		} else {
			brain.eraseMemory(memoryModule);
		}
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get());
	}
}
