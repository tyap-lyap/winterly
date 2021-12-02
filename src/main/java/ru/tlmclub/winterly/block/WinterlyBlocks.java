package ru.tlmclub.winterly.block;


import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;

import java.util.LinkedHashMap;
import java.util.Map;

public class WinterlyBlocks {

    public static final Map<Identifier, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<Identifier, Block> BLOCKS = new LinkedHashMap<>();

    public static Block add(String name, Block block) {
        Item.Settings settings = new Item.Settings();
        return addBlockItem(name, block, new BlockItem(block, settings));
    }

    public static Block addBlockItem(String name, Block block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(WinterlyMod.newId(name), item);
        }
        return block;
    }

    public static Block addBlock(String name, Block block) {
        BLOCKS.put(WinterlyMod.newId(name), block);
        return block;
    }
    public static void register(){
        ITEMS.forEach((identifier, blockItem) -> Registry.register(Registry.ITEM, identifier, blockItem));
        BLOCKS.forEach((identifier, block) -> Registry.register(Registry.BLOCK, identifier, block));
    }

}
