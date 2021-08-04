package slexom.vf.animal_feeding_trough.block.entity;

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
import net.minecraft.text.TranslatableText;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import slexom.vf.animal_feeding_trough.AnimalFeedingTroughMod;
import slexom.vf.animal_feeding_trough.block.FeedingTroughBlock;
import slexom.vf.animal_feeding_trough.inventory.BlockEntityInventory;
import slexom.vf.animal_feeding_trough.screen.FeedingTroughScreenHandler;

import java.util.List;

public class FeedingTroughBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, BlockEntityInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(1, ItemStack.EMPTY);

    private final String TAG_XP_STORAGE = "xpStorage";
    private int xpStorage = 0;

    public FeedingTroughBlockEntity(BlockPos pos, BlockState state) {
        super(AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY, pos, state);
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

    public void lookupExperienceOrbs(World world, BlockPos pos) {
        Box area = new Box(pos.getX() - 5, pos.getY(), pos.getZ() - 5, pos.getX() + 5, pos.getY() + 2, pos.getZ() + 5);
        if(world.getEntitiesByClass(PlayerEntity.class, area, (e) -> true).isEmpty()){
            List<ExperienceOrbEntity> orbsInArea = world.getEntitiesByClass(ExperienceOrbEntity.class, area, (e) -> true);
            if (!orbsInArea.isEmpty()) {
                orbsInArea.forEach(orb -> {
                    this.xpStorage += orb.getExperienceAmount();
                    orb.remove(Entity.RemovalReason.KILLED);
                });
            }
        }
    }

    public void dropStoredXp(World world, PlayerEntity playerEntity) {
        if (this.xpStorage != 0) {
            ExperienceOrbEntity entity = new ExperienceOrbEntity(world, playerEntity.getX(), playerEntity.getY() + .5f, +playerEntity.getZ(), this.xpStorage);
            world.spawnEntity(entity);
            this.xpStorage = 0;
        }
    }

    @Override
    public Text getDisplayName() {
        return new TranslatableText(getCachedState().getBlock().getTranslationKey());
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
        this.xpStorage = tag.getInt(TAG_XP_STORAGE);
    }

    @Override
    public NbtCompound writeNbt(NbtCompound tag) {
        super.writeNbt(tag);
        Inventories.writeNbt(tag, this.inventory);
        tag.putInt(TAG_XP_STORAGE, this.xpStorage);
        return tag;
    }

}
