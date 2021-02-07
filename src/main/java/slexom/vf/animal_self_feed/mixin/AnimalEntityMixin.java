package slexom.vf.animal_self_feed.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.recipe.Ingredient;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slexom.vf.animal_self_feed.ai.goal.SelfFeedGoal;

@Mixin(MobEntity.class)
public class AnimalEntityMixin {

    @Shadow
    @Final
    protected GoalSelector goalSelector;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void ASFAddSelfFeedingGoal(EntityType<? extends MobEntity> entityType, World world, CallbackInfo ci) {
        if (world != null && !world.isClient) {
            ((GoalSelectorAccessor) this.goalSelector).getGoals().stream().filter(prioritizedGoal -> prioritizedGoal.getGoal().getClass().equals(TemptGoal.class)).findFirst().ifPresent(prioritizedGoal -> {
                TemptGoal goal = (TemptGoal) prioritizedGoal.getGoal();
                PathAwareEntity mob = ((TemptGoalAccessor) goal).getMob();
                double speed = ((TemptGoalAccessor) goal).getSpeed();
                Ingredient food = ((TemptGoalAccessor) goal).getFood();
                this.goalSelector.add(prioritizedGoal.getPriority() + 1, new SelfFeedGoal((AnimalEntity) mob, speed, food));
            });
        }
    }
}
