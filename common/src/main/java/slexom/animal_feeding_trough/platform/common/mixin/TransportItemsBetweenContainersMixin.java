package slexom.animal_feeding_trough.platform.common.mixin;


import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.behavior.TransportItemsBetweenContainers;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Mixin(TransportItemsBetweenContainers.class)
public abstract class TransportItemsBetweenContainersMixin {

	@Shadow()
	protected abstract AABB getTargetSearchArea(PathfinderMob pathfinderMob);

	@Shadow()
	protected abstract int getHorizontalSearchDistance(PathfinderMob pathfinderMob);

	@Shadow()
	protected abstract TransportItemsBetweenContainers.TransportItemTarget isTargetValidToPick(PathfinderMob pathfinderMob, Level level, BlockEntity blockEntity, Set<GlobalPos> set, Set<GlobalPos> set2, AABB aABB);

	@Inject(method = "getTransportTarget", at = @At("RETURN"), cancellable = true)
	private void animal_feeding_trough$getTransportTarget(ServerLevel serverLevel, PathfinderMob pathfinderMob, CallbackInfoReturnable<Optional<TransportItemsBetweenContainers.TransportItemTarget>> cir) {
		AABB aABB = this.getTargetSearchArea(pathfinderMob);
		Set<GlobalPos> set = TransportItemsBetweenContainersAccessor.callGetVisitedPositions(pathfinderMob);
		Set<GlobalPos> set2 = TransportItemsBetweenContainersAccessor.callGetUnreachablePositions(pathfinderMob);
		List<ChunkPos> list = ChunkPos.rangeClosed(ChunkPos.containing(pathfinderMob.blockPosition()), Math.floorDiv(this.getHorizontalSearchDistance(pathfinderMob), 16) + 1).toList();

		double maxDist = Float.MAX_VALUE;

		for (ChunkPos chunkPos : list) {
			LevelChunk levelChunk = serverLevel.getChunkSource().getChunkNow(chunkPos.x(), chunkPos.z());
			if (levelChunk != null) {
				for (BlockEntity blockEntity : levelChunk.getBlockEntities().values()) {
					if (blockEntity instanceof FeedingTroughBlockEntity feedingTroughBlockEntity) {
						double dist = feedingTroughBlockEntity.getBlockPos().distToCenterSqr(pathfinderMob.position());
						if (dist < maxDist) {
							TransportItemsBetweenContainers.TransportItemTarget transportItemTarget2 = this.isTargetValidToPick(pathfinderMob, serverLevel, feedingTroughBlockEntity, set, set2, aABB);
							if (transportItemTarget2 != null) {
								cir.setReturnValue(Optional.of(transportItemTarget2));
								maxDist = dist;
							}
						}
					}
				}
			}
		}
	}

}