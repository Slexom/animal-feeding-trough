package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.goal.SelfFeedGoal;

import java.util.Arrays;
import java.util.function.Predicate;

@Mixin(Animal.class)
public class AnimalEntityMixin extends Mob {
    @Unique
    private static final ItemStack[] FORBIDDEN_ITEMS = Arrays.stream(new Item[]{Items.CARROT_ON_A_STICK, Items.WARPED_FUNGUS_ON_A_STICK}).map(ItemStack::new).toArray(ItemStack[]::new);

    protected AnimalEntityMixin(EntityType<? extends Mob> entityType, Level world) {
        super(entityType, world);
    }

    @Inject(method = "<init>", at = @At("TAIL"))
    private void AFTAddSelfFeedingGoal(EntityType<? extends Mob> entityType, Level world, CallbackInfo ci) {
        if (world == null) {
            return;
        }

        if (world.isClientSide) {
            return;
        }

        ((GoalSelectorAccessor) this.goalSelector)
                .getGoals()
                .stream()
                .filter(prioritizedGoal -> prioritizedGoal.getGoal().getClass().equals(TemptGoal.class))
                .toList()
                .forEach(prioritizedGoal -> {
                    TemptGoal goal = (TemptGoal) prioritizedGoal.getGoal();
                    Mob mob = ((TemptGoalAccessor) goal).getMob();
                    double speed = ((TemptGoalAccessor) goal).getSpeed();
                    Predicate<ItemStack> foodPredicate = (((TemptGoalAccessor) goal).getFoodPredicate());
                    boolean hasForbiddenFood = false;
                    for (ItemStack itemStack : FORBIDDEN_ITEMS) {
                        if (foodPredicate.test(itemStack)) {
                            hasForbiddenFood = true;
                            break;
                        }
                    }
                    if (!hasForbiddenFood) {
                        this.goalSelector.addGoal(prioritizedGoal.getPriority() + 1, new SelfFeedGoal((Animal) mob, speed, foodPredicate));
                    }
                });
    }
}
