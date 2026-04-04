package slexom.animal_feeding_trough.platform.common.mixin;

import net.minecraft.core.GlobalPos;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.Set;

@Mixin(TransportItemsBetweenContainers.class)
public interface TransportItemsBetweenContainersAccessor {
	@Invoker
	static Set<GlobalPos> callGetVisitedPositions(PathfinderMob pathfinderMob) {
		throw new UnsupportedOperationException();
	}

	@Invoker
	static Set<GlobalPos> callGetUnreachablePositions(PathfinderMob pathfinderMob) {
		throw new UnsupportedOperationException();
	}
}
