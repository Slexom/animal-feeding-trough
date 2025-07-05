package slexom.animal_feeding_trough.platform.common.world.entity.ai.goal;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntity;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.function.Predicate;

public class SelfFeedGoal extends MoveToBlockGoal {

    protected final Animal mob;
    private final Predicate<ItemStack> foodPredicate;

    private FeedingTroughBlockEntity feeder;

    public SelfFeedGoal(Animal mob, double speed, Predicate<ItemStack> foodPredicate) {
        super(mob, speed, 8);
        this.mob = mob;
        this.foodPredicate = foodPredicate;
    }

    @Override
    public boolean canUse() {
        return this.mob.canFallInLove() && this.mob.getAge() == 0 && super.canUse();
    }

    @Override
    public boolean canContinueToUse() {
        return super.canContinueToUse() && this.feeder != null && this.mob.canFallInLove() && this.mob.getAge() == 0;
    }

    @Override
    public double acceptedDistance() {
        return 2.0D;
    }

    private boolean hasCorrectFood(ItemStack itemStack) {
        return this.foodPredicate.test(itemStack);
    }

    @Override
    protected boolean isValidTarget(LevelReader world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FeedingTroughBlockEntity feedingTroughBlockEntity) {
            ItemStack itemStack = feedingTroughBlockEntity.getItems().get(0);
            if (!itemStack.isEmpty() && hasCorrectFood(itemStack)) {
                this.feeder = feedingTroughBlockEntity;
                return true;
            }
        }
        return false;
    }

    @Override
    public void tick() {
        Level world = this.mob.level();
        if (!world.isClientSide && this.feeder != null && this.mob.canFallInLove()) {
            if (!this.feeder.getItems().get(0).isEmpty()) {
                this.mob.getLookControl().setLookAt((double) this.blockPos.getX() + 0.5D, this.blockPos.getY(), (double) this.blockPos.getZ() + 0.5D, 10.0F, (float) this.mob.getMaxHeadXRot());
                if (this.isReachedTarget()) {
                    this.feeder.getItems().get(0).shrink(1);
                    this.mob.setInLove(null);
                }
            }
            this.feeder = null;
        }
        super.tick();
    }

}