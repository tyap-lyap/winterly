package ru.tlmclub.winterly.registry;


import static net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings.copyOf;
import net.minecraft.block.Block;
import static net.minecraft.block.Blocks.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.bclib.blocks.BaseBlock;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.block.PresentBlock;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyBlocks {
    public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static final Block PAPER_BLOCK = add("paper_block", new BaseBlock(copyOf(WHITE_WOOL)));
    public static final Block PAPER_BRICKS = add("paper_bricks", new BaseBlock(copyOf(WHITE_WOOL)));

    public static final Block WHITE_PRESENT = add("white_present", new PresentBlock(copyOf(WHITE_WOOL)));
    public static final Block RED_PRESENT = add("red_present", new PresentBlock(copyOf(RED_WOOL)));
    public static final Block GREEN_PRESENT = add("green_present", new PresentBlock(copyOf(GREEN_WOOL)));

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
        ITEMS.forEach((identifier, blockItem) -> Registry.register(Registry.ITEM, identifier, blockItem));
        BLOCKS.forEach((identifier, block) -> Registry.register(Registry.BLOCK, identifier, block));
    }

}
