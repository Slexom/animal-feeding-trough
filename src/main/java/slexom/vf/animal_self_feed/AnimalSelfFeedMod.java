package slexom.vf.animal_self_feed;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
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
import slexom.vf.animal_self_feed.block.FoodContainerBlock;
import slexom.vf.animal_self_feed.block.entity.FoodContainerBlockEntity;
import slexom.vf.animal_self_feed.screen.FoodContainerScreenHandler;

public class AnimalSelfFeedMod implements ModInitializer {

    public static final String MOD_ID = "animal_self_feed";
    public static final Logger LOGGER = LogManager.getLogger("Animal Self Feed");

    public final Identifier REGISTRY_NAME = new Identifier(AnimalSelfFeedMod.MOD_ID, "food_container");

    public static Block FOOD_CONTAINER_BLOCK;
    public static BlockEntityType<FoodContainerBlockEntity> FOOD_CONTAINER_BLOCK_ENTITY;
    public static BlockItem FOOD_CONTAINER_BLOCK_ITEM;
    public static ScreenHandlerType<FoodContainerScreenHandler> FOOD_CONTAINER_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        FOOD_CONTAINER_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(REGISTRY_NAME, FoodContainerScreenHandler::new);
        FOOD_CONTAINER_BLOCK = Registry.register(Registry.BLOCK, REGISTRY_NAME, new FoodContainerBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque()));
        FOOD_CONTAINER_BLOCK_ITEM = Registry.register(Registry.ITEM, REGISTRY_NAME, new BlockItem(FOOD_CONTAINER_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        FOOD_CONTAINER_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, REGISTRY_NAME, BlockEntityType.Builder.create(FoodContainerBlockEntity::new, FOOD_CONTAINER_BLOCK).build(null));
        LOGGER.info("[Animal Self Feed] Load Complete! Enjoy :D");
    }

}
