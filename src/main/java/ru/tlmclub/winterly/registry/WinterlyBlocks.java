package ru.tlmclub.winterly.registry;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.block.GarlandLightsBlock;
import ru.tlmclub.winterly.block.PresentBlock;
import static net.minecraft.block.Blocks.*;
import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyBlocks {
    public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Block PAPER_BLOCK = add("paper_block", new Block(copyOf(WHITE_WOOL)));
    public static final Block PAPER_BRICKS = add("paper_bricks", new Block(copyOf(WHITE_WOOL)));
    public static final Block RED_PAPER_BLOCK = add("red_paper_block", new Block(copyOf(RED_WOOL)));
    public static final Block RED_PAPER_BRICKS = add("red_paper_bricks", new Block(copyOf(RED_WOOL)));
    public static final Block GREEN_PAPER_BLOCK = add("green_paper_block", new Block(copyOf(GREEN_WOOL)));
    public static final Block GREEN_PAPER_BRICKS = add("green_paper_bricks", new Block(copyOf(GREEN_WOOL)));
    public static final Block BLUE_PAPER_BLOCK = add("blue_paper_block", new Block(copyOf(BLUE_WOOL)));
    public static final Block BLUE_PAPER_BRICKS = add("blue_paper_bricks", new Block(copyOf(BLUE_WOOL)));
    public static final Block PURPLE_PAPER_BLOCK = add("purple_paper_block", new Block(copyOf(PURPLE_WOOL)));
    public static final Block PURPLE_PAPER_BRICKS = add("purple_paper_bricks", new Block(copyOf(PURPLE_WOOL)));
    public static final Block YELLOW_PAPER_BLOCK = add("yellow_paper_block", new Block(copyOf(YELLOW_WOOL)));
    public static final Block YELLOW_PAPER_BRICKS = add("yellow_paper_bricks", new Block(copyOf(YELLOW_WOOL)));

    public static final Block WHITE_PRESENT = add("white_present", new PresentBlock(copyOf(WHITE_WOOL)));
    public static final Block RED_PRESENT = add("red_present", new PresentBlock(copyOf(RED_WOOL)));
    public static final Block GREEN_PRESENT = add("green_present", new PresentBlock(copyOf(GREEN_WOOL)));
    public static final Block BLUE_PRESENT = add("blue_present", new PresentBlock(copyOf(BLUE_WOOL)));
    public static final Block PURPLE_PRESENT = add("purple_present", new PresentBlock(copyOf(PURPLE_WOOL)));
    public static final Block YELLOW_PRESENT = add("yellow_present", new PresentBlock(copyOf(YELLOW_WOOL)));

    public static final Block GARLAND_LIGHTS = add("garland_lights", new GarlandLightsBlock(copyOf(GREEN_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));
    public static final Block RAINY_GARLAND_LIGHTS = add("rainy_garland_lights", new GarlandLightsBlock(copyOf(WHITE_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));

    private static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        settings.group(WinterlyMod.ITEM_GROUP);
        return addBlockItem(name, block, new BlockItem(block, settings));
    }

    private static Block addBlockItem(String name, Block block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(WinterlyMod.newId(name), item);
        }
        return block;
    }

    private static Block addBlock(String name, Block block) {
        BLOCKS.put(WinterlyMod.newId(name), block);
        return block;
    }
    public static void register(){
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, block));
    }
}
