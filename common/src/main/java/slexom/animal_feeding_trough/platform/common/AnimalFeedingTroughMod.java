package slexom.animal_feeding_trough.platform.common;

import com.google.common.base.Suppliers;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.SensorType;
import net.minecraft.world.entity.animal.armadillo.ArmadilloAi;
import net.minecraft.world.entity.animal.camel.CamelAi;
import net.minecraft.world.entity.animal.frog.FrogAi;
import net.minecraft.world.entity.animal.goat.GoatAi;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.animal_feeding_trough.platform.common.world.level.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.world.level.block.entity.FeedingTroughBlockEntity;
import slexom.animal_feeding_trough.platform.common.client.gui.screens.inventory.FeedingTroughScreen;
import slexom.animal_feeding_trough.platform.common.world.inventory.FeedingTroughMenu;
import slexom.animal_feeding_trough.platform.common.world.entity.ai.sensing.FeedingTroughSensor;

import java.util.Optional;
import java.util.Set;
import java.util.function.Supplier;

public class AnimalFeedingTroughMod {

    public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");
    public static final String MOD_ID = "animal_feeding_trough";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Registrar<Block> BLOCK_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK);
    public static final Registrar<Item> ITEM_REGISTRAR = REGISTRIES.get().get(Registries.ITEM);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.BLOCK_ENTITY_TYPE);
    public static final Registrar<MenuType<?>> MENU_TYPE_REGISTRAR = REGISTRIES.get().get(Registries.MENU);
    public static final Registrar<MemoryModuleType<?>> MEMORY_MODULE_REGISTRAR = REGISTRIES.get().get(Registries.MEMORY_MODULE_TYPE);
    public static final Registrar<SensorType<?>> SENSOR_REGISTRAR = REGISTRIES.get().get(Registries.SENSOR_TYPE);

    public static final ResourceLocation REGISTRY_NAME = ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

    public static RegistrySupplier<Block> FEEDING_TROUGH_BLOCK = BLOCK_REGISTRAR.register(REGISTRY_NAME, () -> new FeedingTroughBlock(BlockBehaviour.Properties.of().mapColor(MapColor.WOOD).instrument(NoteBlockInstrument.BASS).strength(0.2f).sound(SoundType.WOOD).ignitedByLava().noOcclusion().setId(ResourceKey.create(Registries.BLOCK, REGISTRY_NAME))));
    public static RegistrySupplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM = ITEM_REGISTRAR.register(REGISTRY_NAME, () -> new BlockItem(FEEDING_TROUGH_BLOCK.get(), new Item.Properties().arch$tab(CreativeModeTabs.TOOLS_AND_UTILITIES).useBlockDescriptionPrefix().setId(ResourceKey.create(Registries.ITEM, REGISTRY_NAME))));

    public static RegistrySupplier<MemoryModuleType<FeedingTroughBlockEntity>> FEEDING_TROUGH_MEMORY_MODULE = MEMORY_MODULE_REGISTRAR.register(REGISTRY_NAME, () -> new MemoryModuleType<>(Optional.empty()));

    public static final RegistrySupplier<SensorType<FeedingTroughSensor>> AXOLOTL_TEMPTATIONS = SENSOR_REGISTRAR.register(
            ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "axolotl_temptations"),
            () -> new SensorType<>(()-> new FeedingTroughSensor(itemStack -> itemStack.is(Items.TROPICAL_FISH)))
    );
    public static final RegistrySupplier<SensorType<FeedingTroughSensor>> GOAT_TEMPTATIONS = SENSOR_REGISTRAR.register(
            ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "goat_temptations"),
            () -> new SensorType<>(()-> new FeedingTroughSensor(GoatAi.getTemptations()))
    );
    public static final RegistrySupplier<SensorType<FeedingTroughSensor>> FROG_TEMPTATIONS = SENSOR_REGISTRAR.register(
            ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "frog_temptations"),
            () -> new SensorType<>(()-> new FeedingTroughSensor(FrogAi.getTemptations()))
    ) ;
    public static final RegistrySupplier<SensorType<FeedingTroughSensor>> CAMEL_TEMPTATIONS = SENSOR_REGISTRAR.register(
            ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "camel_temptations"),
            () -> new SensorType<>(()-> new FeedingTroughSensor(CamelAi.getTemptations()))
    );
    public static final RegistrySupplier<SensorType<FeedingTroughSensor>> ARMADILLO_TEMPTATIONS = SENSOR_REGISTRAR.register(
            ResourceLocation.fromNamespaceAndPath(AnimalFeedingTroughMod.MOD_ID, "armadillo_temptations"),
            () -> new SensorType<>(()-> new FeedingTroughSensor(ArmadilloAi.getTemptations()))
    );

    public static void onInitialize() {
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

    public static RegistrySupplier<MenuType<FeedingTroughMenu>> FEEDING_TROUGH_MENU = MENU_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new MenuType<>(FeedingTroughMenu::new, FeatureFlags.VANILLA_SET));
    public static RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new BlockEntityType<>(FeedingTroughBlockEntity::new, Set.of(FEEDING_TROUGH_BLOCK.get())));

    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FEEDING_TROUGH_MENU.get(), FeedingTroughScreen::new);
    }

}
