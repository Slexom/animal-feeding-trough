package slexom.vf.animal_feeding_trough.entity.ai.goal;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import slexom.vf.animal_feeding_trough.block.entity.FeedingTroughBlockEntity;

public class SelfFeedGoal extends MoveToTargetPosGoal {

    protected final AnimalEntity mob;
    private final Ingredient food;

    private FeedingTroughBlockEntity feeder;

    public SelfFeedGoal(AnimalEntity mob, double speed, Ingredient food) {
        super(mob, speed, 8);
        this.mob = mob;
        this.food = food;
    }

    @Override
    public boolean canStart() {
        return this.mob.canEat() && this.mob.getBreedingAge() == 0 && super.canStart();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && this.feeder != null && this.mob.canEat() && this.mob.getBreedingAge() == 0;
    }

    @Override
    public double getDesiredDistanceToTarget() {
        return 2.0D;
    }

    private boolean hasCorrectFood(ItemStack itemStack) {
        return this.food.test(itemStack);
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
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
        World world = this.mob.world;
        if (!world.isClient && this.feeder != null && this.mob.canEat()) {
            if (!this.feeder.getItems().get(0).isEmpty()) {
                this.mob.getLookControl().lookAt((double) this.targetPos.getX() + 0.5D, this.targetPos.getY(), (double) this.targetPos.getZ() + 0.5D, 10.0F, (float) this.mob.getMaxLookPitchChange());
                if (this.hasReached()) {
                    this.feeder.getItems().get(0).decrement(1);
                    this.mob.lovePlayer(null);
                }
            }
            this.feeder = null;
        }
        super.tick();
    }

}
