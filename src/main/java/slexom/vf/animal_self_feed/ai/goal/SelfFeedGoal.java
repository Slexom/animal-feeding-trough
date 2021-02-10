package slexom.vf.animal_self_feed.ai.goal;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.MoveToTargetPosGoal;
import net.minecraft.entity.ai.pathing.BirdNavigation;
import net.minecraft.entity.ai.pathing.MobNavigation;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Ingredient;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import slexom.vf.animal_self_feed.block.entity.FoodContainerBlockEntity;

import java.util.EnumSet;

public class SelfFeedGoal extends MoveToTargetPosGoal {

    protected final AnimalEntity mob;
    private final Ingredient food;

    private FoodContainerBlockEntity feeder;

    public SelfFeedGoal(AnimalEntity mob, double speed, Ingredient food) {
        super(mob, speed, 8);
        this.mob = mob;
        this.food = food;
        this.setControls(EnumSet.of(Goal.Control.MOVE, Goal.Control.LOOK));
        if (!(mob.getNavigation() instanceof MobNavigation) && !(mob.getNavigation() instanceof BirdNavigation)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    @Override
    public boolean canStart() {
        return super.canStart() && this.mob.canEat() && !this.mob.isBaby();
    }

    @Override
    public boolean shouldContinue() {
        return super.shouldContinue() && this.feeder != null && this.mob.canEat();
    }

    public double getDesiredSquaredDistanceToTarget() {
        return 2.0D;
    }

    @Override
    protected boolean isTargetPos(WorldView world, BlockPos pos) {
        BlockEntity blockEntity = world.getBlockEntity(pos);
        if (blockEntity instanceof FoodContainerBlockEntity) {
            FoodContainerBlockEntity foodContainerBlockEntity = (FoodContainerBlockEntity) blockEntity;
            ItemStack itemStack = foodContainerBlockEntity.getItems().get(0);
            boolean hasCorrectFood = this.food.test(itemStack);
            if (hasCorrectFood) {
                this.feeder = foodContainerBlockEntity;
                return true;
            }
        }
        return false;
    }

    public void tick() {
        World world = this.mob.world;
        int age = this.mob.getBreedingAge();
        if (this.hasReached()) {
            if (this.feeder != null) {
                if (!world.isClient && age == 0 && this.mob.canEat()) {
                    this.feeder.getItems().get(0).decrement(1);
                    this.mob.lovePlayer(null);
                    this.feeder = null;
                }
            }
        }
        super.tick();
    }

}
