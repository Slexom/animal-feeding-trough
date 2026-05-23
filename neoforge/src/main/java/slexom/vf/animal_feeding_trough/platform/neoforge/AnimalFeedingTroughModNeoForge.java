package slexom.vf.animal_feeding_trough.platform.neoforge;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.Capabilities;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.transfer.item.VanillaContainerWrapper;
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;
import slexom.animal_feeding_trough.platform.common.world.inventory.FeedingTroughMenu;
import slexom.animal_feeding_trough.platform.common.world.level.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.Optional;

@Mod(AnimalFeedingTroughMod.MOD_ID)
public class AnimalFeedingTroughModNeoForge {

	private static final DeferredRegister<Block> BLOCKS =
			DeferredRegister.create(Registries.BLOCK, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredRegister<Item> ITEMS =
			DeferredRegister.create(Registries.ITEM, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
			DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredRegister<MenuType<?>> MENUS =
			DeferredRegister.create(Registries.MENU, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredRegister<MemoryModuleType<?>> MEMORY_MODULES =
			DeferredRegister.create(Registries.MEMORY_MODULE_TYPE, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredRegister<SensorType<?>> SENSORS =
			DeferredRegister.create(Registries.SENSOR_TYPE, AnimalFeedingTroughMod.MOD_ID);

	private static final DeferredHolder<SensorType<?>, SensorType<FeedingTroughSensor>> AXOLOTL_TEMPTATIONS =
			SENSORS.register("axolotl_temptations",
					() -> new SensorType<>(() -> new FeedingTroughSensor(
							stack -> stack.is(Items.TROPICAL_FISH))));

	private static final DeferredHolder<SensorType<?>, SensorType<FeedingTroughSensor>> GOAT_TEMPTATIONS =
			SENSORS.register("goat_temptations",
					() -> new SensorType<>(() -> new FeedingTroughSensor(
							stack -> stack.is(ItemTags.GOAT_FOOD))));

	private static final DeferredHolder<SensorType<?>, SensorType<FeedingTroughSensor>> FROG_TEMPTATIONS =
			SENSORS.register("frog_temptations",
					() -> new SensorType<>(() -> new FeedingTroughSensor(
							stack -> stack.is(ItemTags.FROG_FOOD))));

	private static final DeferredHolder<SensorType<?>, SensorType<FeedingTroughSensor>> CAMEL_TEMPTATIONS =
			SENSORS.register("camel_temptations",
					() -> new SensorType<>(() -> new FeedingTroughSensor(
							stack -> stack.is(ItemTags.CAMEL_FOOD))));

	private static final DeferredHolder<SensorType<?>, SensorType<FeedingTroughSensor>> ARMADILLO_TEMPTATIONS =
			SENSORS.register("armadillo_temptations",
					() -> new SensorType<>(() -> new FeedingTroughSensor(
							stack -> stack.is(ItemTags.ARMADILLO_FOOD))));

	private static final DeferredHolder<MemoryModuleType<?>, MemoryModuleType<FeedingTroughBlockEntity>> FEEDING_TROUGH_MEMORY =
			MEMORY_MODULES.register("feeding_trough",
					() -> new MemoryModuleType<>(Optional.empty()));

	private static final DeferredHolder<Block, FeedingTroughBlock> FEEDING_TROUGH_BLOCK =
			BLOCKS.register("feeding_trough",
					identifier -> new FeedingTroughBlock(
							BlockBehaviour.Properties.of()
									.setId(ResourceKey.create(Registries.BLOCK, identifier))
									.mapColor(MapColor.WOOD)
									.instrument(NoteBlockInstrument.BASS)
									.strength(0.2f)
									.sound(SoundType.WOOD)
									.ignitedByLava()
									.noOcclusion()
					)
			);

	private static final DeferredHolder<Item, BlockItem> FEEDING_TROUGH_BLOCK_ITEM =
			ITEMS.register("feeding_trough",
					identifier -> new BlockItem(
							FEEDING_TROUGH_BLOCK.get(),
							new Item.Properties()
									.setId(ResourceKey.create(Registries.ITEM, identifier))
									.useBlockDescriptionPrefix()
					)
			);

	private static final DeferredHolder<BlockEntityType<?>, BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY =
			BLOCK_ENTITIES.register("feeding_trough", () -> new BlockEntityType<>(FeedingTroughBlockEntity::new, false, FEEDING_TROUGH_BLOCK.get()));


	private static final DeferredHolder<MenuType<?>, MenuType<FeedingTroughMenu>> FEEDING_TROUGH_MENU =
			MENUS.register("feeding_trough", () -> new MenuType<>(FeedingTroughMenu::new, FeatureFlags.VANILLA_SET));


	public AnimalFeedingTroughModNeoForge(IEventBus modEventBus) {
		BLOCKS.register(modEventBus);
		ITEMS.register(modEventBus);
		BLOCK_ENTITIES.register(modEventBus);
		MENUS.register(modEventBus);
		MEMORY_MODULES.register(modEventBus);
		SENSORS.register(modEventBus);

		modEventBus.addListener(this::onBuildCreativeTab);
		modEventBus.addListener(this::registerCapabilities);

		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK = FEEDING_TROUGH_BLOCK;
		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ITEM = FEEDING_TROUGH_BLOCK_ITEM;
		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY = FEEDING_TROUGH_BLOCK_ENTITY;
		AnimalFeedingTroughMod.FEEDING_TROUGH_MENU = FEEDING_TROUGH_MENU;
		AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE = FEEDING_TROUGH_MEMORY;
		AnimalFeedingTroughMod.AXOLOTL_TEMPTATIONS = AXOLOTL_TEMPTATIONS;
		AnimalFeedingTroughMod.GOAT_TEMPTATIONS = GOAT_TEMPTATIONS;
		AnimalFeedingTroughMod.FROG_TEMPTATIONS = FROG_TEMPTATIONS;
		AnimalFeedingTroughMod.CAMEL_TEMPTATIONS = CAMEL_TEMPTATIONS;
		AnimalFeedingTroughMod.ARMADILLO_TEMPTATIONS = ARMADILLO_TEMPTATIONS;

		AnimalFeedingTroughMod.onInitialize();
	}

	private void registerCapabilities(RegisterCapabilitiesEvent event) {
		event.registerBlockEntity(
				Capabilities.Item.BLOCK,
				AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY.get(),
				(blockEntity, side) -> VanillaContainerWrapper.of(blockEntity)
		);
	}

	private void onBuildCreativeTab(BuildCreativeModeTabContentsEvent event) {
		if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
			event.accept(FEEDING_TROUGH_BLOCK_ITEM.get());
		}
	}
}