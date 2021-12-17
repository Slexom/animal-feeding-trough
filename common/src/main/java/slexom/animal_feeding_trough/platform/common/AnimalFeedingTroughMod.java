package slexom.animal_feeding_trough.platform.common;

import dev.architectury.registry.menu.MenuRegistry;
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

    //public static final DeferredRegister<Block> BLOCKS_DEFERRED_REGISTER = DeferredRegister.create(MOD_ID, Registry.BLOCK_KEY);
    //public static final DeferredRegister<Item> ITEMS_DEFERRED_REGISTRIES = DeferredRegister.create(MOD_ID, Registry.ITEM_KEY);
    //public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES_DEFERRED_REGISTRIES = DeferredRegister.create(MOD_ID, Registry.BLOCK_ENTITY_TYPE_KEY);
    //public static final DeferredRegister<ScreenHandlerType<?>> SCREEN_HANDLER_TYPE_DEFERRED_REGISTRIES = DeferredRegister.create(MOD_ID, Registry.MENU_KEY);

    //public static final Identifier REGISTRY_NAME = new Identifier(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

    // public static RegistrySupplier<Block> FEEDING_TROUGH_BLOCK;
    // public static RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY;
    // public static RegistrySupplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM;
    // public static RegistrySupplier<ScreenHandlerType<FeedingTroughScreenHandler>> FEEDING_TROUGH_SCREEN_HANDLER;

    public static Block FEEDING_TROUGH_BLOCK;
    public static BlockEntityType<FeedingTroughBlockEntity> FEEDING_TROUGH_BLOCK_ENTITY;
    public static BlockItem FEEDING_TROUGH_BLOCK_ITEM;
    public static ScreenHandlerType<FeedingTroughScreenHandler> FEEDING_TROUGH_SCREEN_HANDLER;
    public static final Identifier REGISTRY_NAME = new Identifier(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

    public static void onInitialize() {
        // FEEDING_TROUGH_SCREEN_HANDLER = SCREEN_HANDLER_TYPE_DEFERRED_REGISTRIES.register(REGISTRY_NAME, () -> MenuRegistry.of(FeedingTroughScreenHandler::new));
        // FEEDING_TROUGH_BLOCK = BLOCKS_DEFERRED_REGISTER.register(REGISTRY_NAME, () -> new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque()));
        // FEEDING_TROUGH_BLOCK_ITEM = ITEMS_DEFERRED_REGISTRIES.register(REGISTRY_NAME, () -> new BlockItem(FEEDING_TROUGH_BLOCK.get(), new Item.Settings().group(ItemGroup.MISC)));
        // FEEDING_TROUGH_BLOCK_ENTITY = BLOCK_ENTITY_TYPES_DEFERRED_REGISTRIES.register(REGISTRY_NAME, () -> BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK.get()).build(null));


        FEEDING_TROUGH_SCREEN_HANDLER = Registry.register(Registry.SCREEN_HANDLER, REGISTRY_NAME, MenuRegistry.of(FeedingTroughScreenHandler::new));
        FEEDING_TROUGH_BLOCK = Registry.register(Registry.BLOCK, REGISTRY_NAME, new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque()));
        FEEDING_TROUGH_BLOCK_ITEM = Registry.register(Registry.ITEM, REGISTRY_NAME, new BlockItem(FEEDING_TROUGH_BLOCK, new Item.Settings().group(ItemGroup.MISC)));
        FEEDING_TROUGH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, REGISTRY_NAME, BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK).build(null));
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

    public static void onInitializeClient() {
       // MenuRegistry.registerScreenFactory(FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
        MenuRegistry.registerScreenFactory(FEEDING_TROUGH_SCREEN_HANDLER, FeedingTroughScreen::new);
    }

}
