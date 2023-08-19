package slexom.animal_feeding_trough.platform.common;

import com.google.common.base.Suppliers;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.Registrar;
import dev.architectury.registry.registries.RegistrarManager;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.MapColor;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.enums.Instrument;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.animal_feeding_trough.platform.common.block.FeedingTroughBlock;
import slexom.animal_feeding_trough.platform.common.block.entity.FeedingTroughBlockEntity;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreen;
import slexom.animal_feeding_trough.platform.common.screen.FeedingTroughScreenHandler;

import java.util.function.Supplier;

public class AnimalFeedingTroughMod {

    public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");
    public static final String MOD_ID = "animal_feeding_trough";

    public static final Supplier<RegistrarManager> REGISTRIES = Suppliers.memoize(() -> RegistrarManager.get(MOD_ID));

    public static final Registrar<Block> BLOCK_REGISTRAR = REGISTRIES.get().get(RegistryKeys.BLOCK);
    public static final Registrar<Item> ITEM_REGISTRAR = REGISTRIES.get().get(RegistryKeys.ITEM);
    public static final Registrar<BlockEntityType<?>> BLOCK_ENTITY_TYPE_REGISTRAR = REGISTRIES.get().get(RegistryKeys.BLOCK_ENTITY_TYPE);
    public static final Registrar<ScreenHandlerType<?>> SCREEN_HANDLER_TYPE_REGISTRAR = REGISTRIES.get().get(RegistryKeys.SCREEN_HANDLER);

    public static final Identifier REGISTRY_NAME = new Identifier(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");
    public static RegistrySupplier<Block> FEEDING_TROUGH_BLOCK = BLOCK_REGISTRAR.register(REGISTRY_NAME, () -> new FeedingTroughBlock(AbstractBlock.Settings.create().mapColor(MapColor.OAK_TAN).instrument(Instrument.BASS).strength(0.2f).sounds(BlockSoundGroup.WOOD).burnable().nonOpaque()));
    public static RegistrySupplier<BlockItem> FEEDING_TROUGH_BLOCK_ITEM = ITEM_REGISTRAR.register(REGISTRY_NAME, () -> new BlockItem(FEEDING_TROUGH_BLOCK.get(), new Item.Settings().arch$tab(ItemGroups.TOOLS)));



    public static final TagKey<Item> FEEDING_TROUGH_CONTENT_WHEAT_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "feeding_trough_content/wheat"));
    public static final TagKey<Item> FEEDING_TROUGH_CONTENT_CARROT_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "feeding_trough_content/carrot"));
    public static final TagKey<Item> FEEDING_TROUGH_CONTENT_POTATO_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "feeding_trough_content/potato"));
    public static final TagKey<Item> FEEDING_TROUGH_CONTENT_MEAT_TAG = TagKey.of(RegistryKeys.ITEM, new Identifier(MOD_ID, "feeding_trough_content/meat"));



    public static void onInitialize() {
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

    public static void onInitializeClient() {
        MenuRegistry.registerScreenFactory(FEEDING_TROUGH_SCREEN_HANDLER.get(), FeedingTroughScreen::new);
    }

    public static RegistrySupplier<ScreenHandlerType<FeedingTroughScreenHandler>> FEEDING_TROUGH_SCREEN_HANDLER = SCREEN_HANDLER_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> new ScreenHandlerType<FeedingTroughScreenHandler>(FeedingTroughScreenHandler::new, FeatureFlags.VANILLA_FEATURES));


    public static RegistrySupplier<BlockEntityType<FeedingTroughBlockEntity>> FEEDING_TROUGH_BLOCK_ENTITY = BLOCK_ENTITY_TYPE_REGISTRAR.register(REGISTRY_NAME, () -> BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK.get()).build(null));


}
