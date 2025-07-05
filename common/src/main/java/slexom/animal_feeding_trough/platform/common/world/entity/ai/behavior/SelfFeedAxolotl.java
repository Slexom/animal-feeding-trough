package slexom.animal_feeding_trough.platform.common.world.entity.ai.behavior;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

import java.util.function.Function;

public class SelfFeedAxolotl extends SelfFeed {
    public SelfFeedAxolotl(Function<LivingEntity, Float> speedModifier) {
        this(speedModifier, livingEntity -> DEFAULT_CLOSE_ENOUGH_DIST);
    }

    public SelfFeedAxolotl(Function<LivingEntity, Float> speedModifier, Function<LivingEntity, Double> closeEnoughDistance) {
        super(speedModifier, closeEnoughDistance);
    }

    protected boolean canStillUse(ServerLevel serverLevel, Animal animal, long l) {
        var feedingTrough = this.getFeedingTrough(animal);
        if (feedingTrough.isEmpty()) {
            return false;
        }

        BlockPos blockPos = feedingTrough.get().getBlockPos();
        boolean isWaterlogged = serverLevel.getBlockState(blockPos).getValue(BlockStateProperties.WATERLOGGED);

        return isWaterlogged
                && animal.canFallInLove()
                && animal.getAge() == 0
                && !animal.getBrain().hasMemoryValue(MemoryModuleType.BREED_TARGET)
                && !animal.getBrain().hasMemoryValue(MemoryModuleType.IS_PANICKING);
    }

}
