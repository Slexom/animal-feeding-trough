package slexom.animal_feeding_trough.platform.common;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;
import slexom.animal_feeding_trough.platform.common.world.inventory.FeedingTroughMenu;
import slexom.animal_feeding_trough.platform.common.world.level.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.function.Supplier;

public class AnimalFeedingTroughMod {

	public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");
	public static final String MOD_ID = "animal_feeding_trough";


	public static Supplier<FeedingTroughBlock> FEEDING_TROUGH_BLOCK;
	public static Supplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM;

	public static Supplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY;

	public static Supplier<MenuType<FeedingTroughMenu>> FEEDING_TROUGH_MENU;

	public static Supplier<MemoryModuleType<FeedingTroughBlockEntity>> FEEDING_TROUGH_MEMORY_MODULE;

	public static Supplier<SensorType<FeedingTroughSensor>> AXOLOTL_TEMPTATIONS;
	public static Supplier<SensorType<FeedingTroughSensor>> GOAT_TEMPTATIONS;
	public static Supplier<SensorType<FeedingTroughSensor>> FROG_TEMPTATIONS;
	public static Supplier<SensorType<FeedingTroughSensor>> CAMEL_TEMPTATIONS;
	public static Supplier<SensorType<FeedingTroughSensor>> ARMADILLO_TEMPTATIONS;

	public static void onInitialize() {
		LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
	}

}
