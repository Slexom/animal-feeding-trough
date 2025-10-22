package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.world.entity.animal.coppergolem.CopperGolemAi;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;

import java.util.function.Predicate;

@Mixin(CopperGolemAi.class)
public class CopperGolemAiMixin {

    @Final
    @Shadow
    @Mutable
    private static Predicate<BlockState> TRANSPORT_ITEM_DESTINATION_BLOCK;

    @Inject(method = "<clinit>", at = @At("TAIL"))
    private static void animal_feeding_trough$transportItemDestinationBlock(CallbackInfo ci) {
        Predicate<BlockState> original = TRANSPORT_ITEM_DESTINATION_BLOCK;

        TRANSPORT_ITEM_DESTINATION_BLOCK = blockState -> blockState.is(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK.get()) || original.test(blockState);
    }
}
