package slexom.animal_feeding_trough.platform.common.block.entity;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughContent;
import slexom.animal_feeding_trough.platform.common.inventory.BlockEntityInventory;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

import java.util.List;
import java.util.logging.Logger;

public class FeedingTroughBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, BlockEntityInventory {
	private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);
	private final String NBT_STORED_EXP = "StoredExp";
	private int storedExp = 0;

	public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
		super(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY.get(), pos, state);
	}

	public static void tick(World world, BlockPos pos, BlockState state, FeedingTroughBlockEntity blockEntity) {
		if (world.isClient()) {
			return;
		}
		ItemStack itemStack = blockEntity.getStack(0);
		updateContent(world, pos, state, itemStack);
		updateLevel(world, pos, state, itemStack);
	}

	public static void updateContent(World world, BlockPos pos, BlockState state, ItemStack itemStack) {
		FeedingTroughContent currentContent = state.get(FeedingTroughBlock.CONTENT);
		FeedingTroughContent newContent = FeedingTroughContent.WHEAT;

		if (itemStack.isIn(AnimalFeedingTroughMod.FEEDING_TROUGH_CONTENT_WHEAT_TAG)) {
			newContent = FeedingTroughContent.WHEAT;
		} else if (itemStack.isIn(AnimalFeedingTroughMod.FEEDING_TROUGH_CONTENT_CARROT_TAG)) {
			newContent = FeedingTroughContent.CARROT;
		} else if (itemStack.isIn(AnimalFeedingTroughMod.FEEDING_TROUGH_CONTENT_POTATO_TAG)) {
			newContent = FeedingTroughContent.POTATO;
		} else if (itemStack.isIn(AnimalFeedingTroughMod.FEEDING_TROUGH_CONTENT_MEAT_TAG)) {
			newContent = FeedingTroughContent.MEAT;
		}

		if (newContent == currentContent) {
			return;
		}

		BlockState blockState = state.with(FeedingTroughBlock.CONTENT, newContent);
		world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
	}

	public static void updateLevel(World world, BlockPos pos, BlockState state, ItemStack itemStack) {
		int count = itemStack.getCount();
		int newLevel = 0;

		if (count > 0) {
			newLevel = MathHelper.floor(count / 16.0F) + 1;
			newLevel = Math.min(newLevel, 4);
		}

		int currentLevel = state.get(FeedingTroughBlock.LEVEL);
		if (currentLevel == newLevel) {
			return;
		}

		BlockState blockState = state.with(FeedingTroughBlock.LEVEL, newLevel);
		world.setBlockState(pos, blockState, Block.NOTIFY_ALL);
	}


	private boolean playersAround(World world, BlockPos pos) {
		Box lookupArea = new Box(pos.getX() - 5, pos.getY() - 2, pos.getZ() - 5, pos.getX() + 5, pos.getY() + 2, pos.getZ() + 5);
		List<PlayerEntity> playersInArea = world.getEntitiesByClass(PlayerEntity.class, lookupArea, (e) -> true);
		return !playersInArea.isEmpty();
	}

	public void gatherExperienceOrbs(World world, BlockPos pos) {
		if (playersAround(world, pos)) return;

		Box lookupArea = new Box(pos.getX() - 2, pos.getY() - 2, pos.getZ() - 2, pos.getX() + 2, pos.getY() + 2, pos.getZ() + 2);
		List<ExperienceOrbEntity> experienceOrbEntities = world.getEntitiesByClass(ExperienceOrbEntity.class, lookupArea, (e) -> true);

		if (experienceOrbEntities.isEmpty()) return;

		experienceOrbEntities.forEach(orb -> {
			this.storedExp += orb.getExperienceAmount();
			orb.remove(Entity.RemovalReason.DISCARDED);
		});
	}

	public void dropStoredXp(World world, PlayerEntity playerEntity) {
		if (this.storedExp == 0) return;

		ExperienceOrbEntity entity = new ExperienceOrbEntity(world, playerEntity.getX(), playerEntity.getY() + 0.5F, playerEntity.getZ(), this.storedExp);
		world.spawnEntity(entity);
		this.storedExp = 0;
	}


	@Override
	public Text getDisplayName() {
		return Text.translatable(getCachedState().getBlock().getTranslationKey());
	}

	@Nullable
	@Override
	public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
		return new FeedingTroughScreenHandler(syncId, playerInventory, this);
	}

	@Override
	public DefaultedList<ItemStack> getItems() {
		return inventory;
	}

	@Override
	public void readNbt(NbtCompound tag) {
		super.readNbt(tag);
		Inventories.readNbt(tag, this.inventory);
		this.storedExp = tag.getInt(NBT_STORED_EXP);
	}

	@Override
	public void writeNbt(NbtCompound tag) {
		super.writeNbt(tag);
		Inventories.writeNbt(tag, this.inventory);
		tag.putInt(NBT_STORED_EXP, this.storedExp);
	}

}
