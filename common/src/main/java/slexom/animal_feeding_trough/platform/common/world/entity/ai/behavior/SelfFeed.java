package slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior;

import com.google.common.collect.ImmutableMap;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BlockPosTracker;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.memory.WalkTarget;
import net.minecraft.world.entity.animal.Animal;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.Optional;
import java.util.function.Function;

public class SelfFeed extends Behavior<Animal> {
    public static final int TEMPTATION_COOLDOWN = 100;
    public static final double DEFAULT_CLOSE_ENOUGH_DIST = 2.5;
    public static final double BACKED_UP_CLOSE_ENOUGH_DIST = 3.5;
    private final Function<LivingEntity, Float> speedModifier;
    private final Function<LivingEntity, Double> closeEnoughDistance;

    public SelfFeed(Function<LivingEntity, Float> speedModifier) {
        this(speedModifier, livingEntity -> DEFAULT_CLOSE_ENOUGH_DIST);
    }

    public SelfFeed(Function<LivingEntity, Float> speedModifier, Function<LivingEntity, Double> closeEnoughDistance) {
        super(Util.make(() -> {
            ImmutableMap.Builder<MemoryModuleType<?>, MemoryStatus> builder = ImmutableMap.builder();
            builder.put(MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED);
            builder.put(MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED);
            builder.put(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, MemoryStatus.VALUE_ABSENT);
            builder.put(MemoryModuleType.IS_TEMPTED, MemoryStatus.VALUE_ABSENT);
            builder.put(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get(), MemoryStatus.VALUE_PRESENT);
            builder.put(MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT);
            builder.put(MemoryModuleType.IS_PANICKING, MemoryStatus.VALUE_ABSENT);
            return builder.build();
        }));
        this.speedModifier = speedModifier;
        this.closeEnoughDistance = closeEnoughDistance;
    }

    protected float getSpeedModifier(Animal animal) {
        return this.speedModifier.apply(animal);
    }

    protected Optional<FeedingTroughBlockEntity> getFeedingTrough(Animal animal) {
        return animal.getBrain().getMemory(AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE.get());
    }

    @Override
    protected boolean timedOut(long l) {
        return false;
    }

    protected boolean canStillUse(ServerLevel serverLevel, Animal animal, long l) {
        return this.getFeedingTrough(animal).isPresent()
                && animal.canFallInLove()
                && animal.getAge() == 0
                && !animal.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET)
                && !animal.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING);
    }

    protected void start(ServerLevel serverLevel, Animal animal, long l) {
        animal.getBrain().setMemory(MemoryModuleType.IS_TEMPTED, true);
    }

    protected void stop(ServerLevel serverLevel, Animal animal, long l) {
        Brain<?> brain = animal.getBrain();
        brain.setMemory(MemoryModuleType.TEMPTATION_COOLDOWN_TICKS, TEMPTATION_COOLDOWN);
        brain.eraseMemory(MemoryModuleType.IS_TEMPTED);
        brain.eraseMemory(MemoryModuleType.WALK_TARGET);
        brain.eraseMemory(MemoryModuleType.LOOK_TARGET);
    }

    protected void tick(ServerLevel serverLevel, Animal animal, long l) {
        FeedingTroughBlockEntity feedingTroughBlockEntity = this.getFeedingTrough(animal).get();
        Brain<?> brain = animal.getBrain();
        BlockPos blockPos = feedingTroughBlockEntity.getBlockPos();
        BlockPosTracker tracker = new BlockPosTracker(blockPos);

        brain.setMemory(MemoryModuleType.LOOK_TARGET, tracker);
        double d = this.closeEnoughDistance.apply(animal);
        if (animal.distanceToSqr(blockPos.getCenter()) < Mth.square(d) && animal.canFallInLove()) {
            brain.eraseMemory(MemoryModuleType.WALK_TARGET);

            feedingTroughBlockEntity.getItems().get(0).shrink(1);
            animal.setInLove(null);
        } else {
            WalkTarget walkTarget = new WalkTarget(tracker, this.getSpeedModifier(animal), 2);
            brain.setMemory(MemoryModuleType.WALK_TARGET, walkTarget);
        }
    }
}
