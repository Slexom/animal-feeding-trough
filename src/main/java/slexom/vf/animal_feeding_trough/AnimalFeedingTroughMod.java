package slexom.vf.animal_feeding_trough;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.screenhandler.v1.ScreenHandlerRegistry;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Material;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import slexom.vf.animal_feeding_trough.block.FeedingTroughBlock;
import slexom.vf.animal_feeding_trough.block.entity.FeedingTroughBlockEntity;
import slexom.vf.animal_feeding_trough.screen.FeedingTroughScreenHandler;

public class AnimalFeedingTroughMod implements ModInitializer {

    public static final String MOD_ID = "animal_feeding_trough";
    public static final Logger LOGGER = LogManager.getLogger("Animal Feeding Trough");

    public final Identifier REGISTRY_NAME = new Identifier(AnimalFeedingTroughMod.MOD_ID, "feeding_trough");

    public static Block FEEDING_TROUGH_BLOCK;
    public static BlockEntityType<FeedingTroughBlockEntity> FEEDING_TROUGH_BLOCK_ENTITY;
    public static BlockItem FEEDING_TROUGH_BLOCK_ITEM;
    public static ScreenHandlerType<FeedingTroughScreenHandler> FEEDING_TROUGH_SCREEN_HANDLER;

    @Override
    public void onInitialize() {
        FEEDING_TROUGH_SCREEN_HANDLER = ScreenHandlerRegistry.registerSimple(REGISTRY_NAME, FeedingTroughScreenHandler::new);
        FEEDING_TROUGH_BLOCK = Registry.register(Registry.BLOCK, REGISTRY_NAME, new FeedingTroughBlock(AbstractBlock.Settings.of(Material.WOOD).sounds(BlockSoundGroup.WOOD).strength(0.2F).nonOpaque()));
        FEEDING_TROUGH_BLOCK_ITEM = Registry.register(Registry.ITEM, REGISTRY_NAME, new BlockItem(FEEDING_TROUGH_BLOCK, new FabricItemSettings().group(ItemGroup.MISC)));
        FEEDING_TROUGH_BLOCK_ENTITY = Registry.register(Registry.BLOCK_ENTITY_TYPE, REGISTRY_NAME, BlockEntityType.Builder.create(FeedingTroughBlockEntity::new, FEEDING_TROUGH_BLOCK).build(null));
        LOGGER.info("[Animal Feeding Trough] Load Complete! Enjoy :D");
    }

}
