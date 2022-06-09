package slexom.animal_feeding_trough.platform.common.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
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
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.inventory.BlockEntityInventory;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

public class FeedingTroughBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, BlockEntityInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
        super(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY.get(), pos, state);
        //   super(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY, pos, state);
    }

    public static void tick(World world, BlockPos pos, BlockState state, FeedingTroughBlockEntity blockEntity) {
        if (!world.isClient()) {
            int count = blockEntity.getStack(0).getCount();
            int newLevel = 0;
            if (count > 0) {
                newLevel = MathHelper.floor(blockEntity.getStack(0).getCount() / 16.0F) + 1;
                newLevel = Math.min(newLevel, 4);
            }
            int currentLevel = state.get(FeedingTroughBlock.LEVEL);
            if (currentLevel != newLevel) {
                BlockState blockState = state.with(FeedingTroughBlock.LEVEL, newLevel);
                world.setBlockState(pos, blockState, 3);
            }
        }
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
    }

    @Override
    public void writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory);
    }

}
