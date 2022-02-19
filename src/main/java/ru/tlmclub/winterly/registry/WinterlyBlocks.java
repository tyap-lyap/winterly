package ru.tlmclub.winterly.registry;

import net.minecraft.block.Block;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.SlabBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.block.*;
import ru.tlmclub.winterly.block.base.BasePaneBlock;
import ru.tlmclub.winterly.block.base.BaseStairsBlock;

import static net.minecraft.block.Blocks.*;
import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyBlocks {
    public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

//    public static final Block RED_SOCK = add("red_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));
//    public static final Block GREEN_SOCK = add("green_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));
//    public static final Block BLUE_SOCK = add("blue_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));

    public static final Block ICICLE = add("icicle", new IcicleBlock(copyOf(ICE)));
    public static final Block ICICLE_BLOCK = add("icicle_block", new GlassBlock(copyOf(PACKED_ICE).nonOpaque()));
    public static final Block ICICLE_PANE = add("icicle_pane", new BasePaneBlock(copyOf(PACKED_ICE).nonOpaque()));
    public static final Block ICICLE_BARS = add("icicle_bars", new BasePaneBlock(copyOf(ICE).nonOpaque()));
    public static final Block SNOWGUY = add("snowguy", new SnowguyBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOWBALL_WALL = add("snowball_wall", new SnowballWallBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW).nonOpaque()));
    public static final Block DENSE_SNOW = add("dense_snow", new Block(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block DENSE_SNOW_STAIRS = add("dense_snow_stairs", new BaseStairsBlock(SNOW_BLOCK.getDefaultState(), copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block DENSE_SNOW_SLAB = add("dense_snow_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));

    public static final Block SNOW_BRICKS = add("snow_bricks", new Block(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOW_BRICK_STAIRS = add("snow_brick_stairs", new BaseStairsBlock(SNOW_BLOCK.getDefaultState(), copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOW_BRICK_SLAB = add("snow_brick_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));

    public static final Block RAW_CRYOMARBLE_SHARD = add("raw_cryomarble_shard", new IcicleBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.GLASS).luminance(12)));

    public static final Block WHITE_PRESENT = add("white_present", new PresentBlock(copyOf(WHITE_WOOL)));
    public static final Block RED_PRESENT = add("red_present", new PresentBlock(copyOf(RED_WOOL)));
    public static final Block GREEN_PRESENT = add("green_present", new PresentBlock(copyOf(GREEN_WOOL)));
    public static final Block BLUE_PRESENT = add("blue_present", new PresentBlock(copyOf(BLUE_WOOL)));
    public static final Block PURPLE_PRESENT = add("purple_present", new PresentBlock(copyOf(PURPLE_WOOL)));
    public static final Block YELLOW_PRESENT = add("yellow_present", new PresentBlock(copyOf(YELLOW_WOOL)));
    public static final Block BLACK_PRESENT = add("black_present", new PresentBlock(copyOf(BLACK_WOOL)));
    public static final Block LIGHT_BLUE_PRESENT = add("light_blue_present", new PresentBlock(copyOf(LIGHT_BLUE_WOOL)));
    public static final Block LIME_PRESENT = add("lime_present", new PresentBlock(copyOf(LIME_WOOL)));
    public static final Block PINK_PRESENT = add("pink_present", new PresentBlock(copyOf(PINK_WOOL)));
    public static final Block CYAN_PRESENT = add("cyan_present", new PresentBlock(copyOf(CYAN_WOOL)));
    public static final Block LIGHT_GRAY_PRESENT = add("light_gray_present", new PresentBlock(copyOf(LIGHT_GRAY_WOOL)));
    public static final Block GRAY_PRESENT = add("gray_present", new PresentBlock(copyOf(GRAY_WOOL)));
    public static final Block MAGENTA_PRESENT = add("magenta_present", new PresentBlock(copyOf(MAGENTA_WOOL)));
    public static final Block BROWN_PRESENT = add("brown_present", new PresentBlock(copyOf(BROWN_WOOL)));

    public static final Block GARLAND_LIGHTS = add("garland_lights", new GarlandLightsBlock(copyOf(GREEN_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));
    public static final Block RAINY_GARLAND_LIGHTS = add("rainy_garland_lights", new GarlandLightsBlock(copyOf(WHITE_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));

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
    public static final Block BLACK_PAPER_BLOCK = add("black_paper_block", new Block(copyOf(BLACK_WOOL)));
    public static final Block BLACK_PAPER_BRICKS = add("black_paper_bricks", new Block(copyOf(BLACK_WOOL)));
    public static final Block LIGHT_BLUE_PAPER_BLOCK = add("light_blue_paper_block", new Block(copyOf(LIGHT_BLUE_WOOL)));
    public static final Block LIGHT_BLUE_PAPER_BRICKS = add("light_blue_paper_bricks", new Block(copyOf(LIGHT_BLUE_WOOL)));
    public static final Block LIME_PAPER_BLOCK = add("lime_paper_block", new Block(copyOf(LIME_WOOL)));
    public static final Block LIME_PAPER_BRICKS = add("lime_paper_bricks", new Block(copyOf(LIME_WOOL)));
    public static final Block PINK_PAPER_BLOCK = add("pink_paper_block", new Block(copyOf(PINK_WOOL)));
    public static final Block PINK_PAPER_BRICKS = add("pink_paper_bricks", new Block(copyOf(PINK_WOOL)));
    public static final Block CYAN_PAPER_BLOCK = add("cyan_paper_block", new Block(copyOf(CYAN_WOOL)));
    public static final Block CYAN_PAPER_BRICKS = add("cyan_paper_bricks", new Block(copyOf(CYAN_WOOL)));
    public static final Block LIGHT_GRAY_PAPER_BLOCK = add("light_gray_paper_block", new Block(copyOf(LIGHT_GRAY_WOOL)));
    public static final Block LIGHT_GRAY_PAPER_BRICKS = add("light_gray_paper_bricks", new Block(copyOf(LIGHT_GRAY_WOOL)));
    public static final Block GRAY_PAPER_BLOCK = add("gray_paper_block", new Block(copyOf(GRAY_WOOL)));
    public static final Block GRAY_PAPER_BRICKS = add("gray_paper_bricks", new Block(copyOf(GRAY_WOOL)));
    public static final Block MAGENTA_PAPER_BLOCK = add("magenta_paper_block", new Block(copyOf(MAGENTA_WOOL)));
    public static final Block MAGENTA_PAPER_BRICKS = add("magenta_paper_bricks", new Block(copyOf(MAGENTA_WOOL)));
    public static final Block BROWN_PAPER_BLOCK = add("brown_paper_block", new Block(copyOf(BROWN_WOOL)));
    public static final Block BROWN_PAPER_BRICKS = add("brown_paper_bricks", new Block(copyOf(BROWN_WOOL)));

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

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
        BLOCKS.forEach((id, block) -> Registry.register(Registry.BLOCK, id, block));
    }
}
