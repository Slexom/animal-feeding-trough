package slexom.animal_feeding_trough.platform.common;

import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.Registries;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.Lazy;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.block.entity.FeedingTroughBlockEntity;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreen;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

public class AnimalFeedingTroughMod {

    public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");
    public static final String MOD_ID = "animal_feeding_trough";

    public static final Lazy<Registries> REGISTRIES = new Lazy<>(() -> Registries.get(MOD_ID));

    public static final Registrar<Block> BLOCK_REGISTRAR = REGISTRIES.get().get(Registry.BLOCK_KEY);
    public static final Registrar<Item> ITEM_REGISTRAR = REGISTRIES.get().get(Registry.ITEM_KEY);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPE_REGISTRAR = REGISTRIES.get().get(Registry.BLOCK_ENTITY_TYPE_KEY);
    public static final Registrar<ScreenHandlerType<?>> SCREEN_HANDLER_TYPE_REGISTRAR = REGISTRIES.get().get(Registry.MENU_KEY);

    public static final Identifier REGISTRY_NAME = new Identifier(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

    public static RegistrySupplier<ScreenHandlerType<FeedingTroughScreenHandler>> FEEDING_TROUGH_SCREEN_HANDLER = SCREEN_HANDLER_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> MenuRegistry.of(FeedingTroughScreenHandler::new));
    public static RegistrySupplier<Block> FEEDING_TROUGH_BLOCK = BLOCK_REGISTRAR.register(REGISTRY_NAME, () -> new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque()));
    public static RegistrySupplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM = ITEM_REGISTRAR.register(REGISTRY_NAME, () -> new BlockItem(FEEDING_TROUGH_BLOCK.get(), new Item.Settings().group(ItemGroup.MISC)));
    public static RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK.get()).build(null));

    public static void onInitialize() {
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
    }

}
