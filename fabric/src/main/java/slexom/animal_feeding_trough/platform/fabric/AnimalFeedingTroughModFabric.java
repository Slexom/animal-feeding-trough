package slexom.animal_feeding_trough.platform.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.creativetab.v1.CreativeModeTabEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.fabricmc.fabric.api.transfer.v1.item.ContainerStorage;
import net.fabricmc.fabric.api.transfer.v1.item.ItemStorage;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
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
import slexom.animal_feeding_trough.platform.common.AnimalFeedingTroughMod;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;
import slexom.animal_feeding_trough.platform.common.world.inventory.FeedingTroughMenu;
import slexom.animal_feeding_trough.platform.common.world.level.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;

import java.util.Optional;

public class AnimalFeedingTroughModFabric implements ModInitializer {

	private static final Identifier ID =
			Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

	@Override
	public void onInitialize() {
		registerSensors();
		registerMemory();
		registerBlocks();
		registerBlockEntities();
		registerMenus();
		AnimalFeedingTroughMod.onInitialize();
	}

	private void registerSensors() {
		var axolotl = new SensorType<>(() -> new FeedingTroughSensor(stack -> stack.is(Items.TROPICAL_FISH)));
		Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "axolotl_temptations"), axolotl);
		AnimalFeedingTroughMod.AXOLOTL_TEMPTATIONS = () -> axolotl;

		var goat = new SensorType<>(() -> new FeedingTroughSensor(stack -> stack.is(ItemTags.GOAT_FOOD)));
		Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "goat_temptations"), goat);
		AnimalFeedingTroughMod.GOAT_TEMPTATIONS = () -> goat;

		var frog = new SensorType<>(() -> new FeedingTroughSensor(stack -> stack.is(ItemTags.FROG_FOOD)));
		Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "frog_temptations"), frog);
		AnimalFeedingTroughMod.FROG_TEMPTATIONS = () -> frog;

		var camel = new SensorType<>(() -> new FeedingTroughSensor(stack -> stack.is(ItemTags.CAMEL_FOOD)));
		Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "camel_temptations"), camel);
		AnimalFeedingTroughMod.CAMEL_TEMPTATIONS = () -> camel;

		var armadillo = new SensorType<>(() -> new FeedingTroughSensor(stack -> stack.is(ItemTags.ARMADILLO_FOOD)));
		Registry.register(BuiltInRegistries.SENSOR_TYPE, Identifier.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "armadillo_temptations"), armadillo);
		AnimalFeedingTroughMod.ARMADILLO_TEMPTATIONS = () -> armadillo;
	}

	private void registerMemory() {
		var memory = new MemoryModuleType<FeedingTroughBlockEntity>(Optional.empty());
		Registry.register(BuiltInRegistries.MEMORY_MODULE_TYPE, ID, memory);
		AnimalFeedingTroughMod.FEEDING_TROUGH_MEMORY_MODULE = () -> memory;
	}

	private void registerBlocks() {
		ResourceKey<Block> blockKey = ResourceKey.create(Registries.BLOCK, ID);
		ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, ID);

		FeedingTroughBlock block = new FeedingTroughBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(0.2f).sound(SoundType.WOOD).ignitedByLava().noOcclusion().setId(blockKey));
		Registry.register(BuiltInRegistries.BLOCK, ID, block);
		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK = () -> block;

		BlockItem blockItem = new BlockItem(block, new Item.Properties().setId(itemKey).useBlockDescriptionPrefix());
		Registry.register(BuiltInRegistries.ITEM, ID, blockItem);
		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ITEM = () -> blockItem;

		CreativeModeTabEvents.modifyOutputEvent(CreativeModeTabs.TOOLS_AND_UTILITIES).register(creativeTab -> creativeTab.accept(blockItem));
	}

	private void registerBlockEntities() {
		BlockEntityType<FeedingTroughBlockEntity> type = FabricBlockEntityTypeBuilder.create(FeedingTroughBlockEntity::new, AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK.get()).build();
		Registry.register(BuiltInRegistries.BLOCK_ENTITY_TYPE, ID, type);
		AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY = () -> type;

		ItemStorage.SIDED.registerForBlockEntity(
				ContainerStorage::of,
				AnimalFeedingTroughMod.FEEDING_TROUGH_BLOCK_ENTITY.get()
		);
	}

	private void registerMenus() {
		MenuType<FeedingTroughMenu> menu = new MenuType<>(FeedingTroughMenu::new, FeatureFlags.VANILLA_SET);
		Registry.register(BuiltInRegistries.MENU, ID, menu);
		AnimalFeedingTroughMod.FEEDING_TROUGH_MENU = () -> menu;
	}
}
