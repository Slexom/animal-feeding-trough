package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.TemptGoal;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.function.Predicate;

@Mixin(TemptGoal.class)
public interface TemptGoalAccessor {

    @Accessor("mob")
    Mob getMob();

    @Accessor("speedModifier")
    double getSpeed();

    @Accessor("items")
    Predicate<ItemStack> getFoodPredicate();

}
