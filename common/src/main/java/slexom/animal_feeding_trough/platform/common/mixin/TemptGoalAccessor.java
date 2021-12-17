package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.recipe.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(TemptGoal.class)
public interface TemptGoalAccessor {

    @Accessor("mob")
    public PathAwareEntity getMob();

    @Accessor("speed")
    public double getSpeed();

    @Accessor("food")
    public Ingredient getFood();

}
