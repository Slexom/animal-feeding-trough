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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class FeedingTroughSensor extends Sensor<PathfinderMob> {
    private final Predicate<ItemStack> temptations;

    public FeedingTroughSensor(Predicate<ItemStack> predicate) {
        this.temptations = predicate;
    }

    protected void doTick(ServerLevel serverLevel, PathfinderMob pathfinderMob) {
        Brain<?> brain = pathfinderMob.getBrain();
        MemoryModuleType<FeedingTroughBlockEntity> memoryModule = AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get();

        BlockPos mobPos = pathfinderMob.getOnPos();

        List<FeedingTroughBlockEntity> foundEntities = new ArrayList<>();

        int radius = (int) pathfinderMob.getAttributeValue(Attributes.TEMPT_RANGE);
        BlockPos minPos = mobPos.offset(-radius, -radius, -radius);
        BlockPos maxPos = mobPos.offset(radius, radius, radius);

        for (BlockPos pos : BlockPos.betweenClosed(minPos, maxPos)) {
            if (pos.distSqr(pos) <= radius * radius) {
                BlockEntity blockEntity = serverLevel.getBlockEntity(pos);

                if (blockEntity instanceof FeedingTroughBlockEntity) {
                    foundEntities.add((FeedingTroughBlockEntity) blockEntity);
                }
            }
        }

        List<FeedingTroughBlockEntity> list = foundEntities
                .stream()
                .filter(entity -> isTemptation(entity.getItems().get(0)))
                .sorted(Comparator.comparingDouble(value -> pathfinderMob.distanceToSqr(value.getBlockPos().getX(), value.getBlockPos().getY(), value.getBlockPos().getZ())))
                .toList();

        if (!list.isEmpty()) {
            FeedingTroughBlockEntity feedingTroughBlockEntity = list.get(0);
            brain.setMemory(memoryModule, feedingTroughBlockEntity);
        } else {
            brain.eraseMemory(memoryModule);
        }
    }


    private boolean isTemptation(ItemStack itemStack) {
        return this.temptations.test(itemStack);
    }

    @Override
    public Set<MemoryModuleType<?>> requires() {
        return ImmutableSet.of(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get());
    }

}
