package winterly.fabric.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import winterly.Winterly;
import winterly.block.*;
import winterly.block.base.BasePaneBlock;
import winterly.block.base.BaseStairsBlock;
import winterly.fabric.block.FrozenFlowerBlock;
import winterly.fabric.block.FrozenGrassBlock;

import static net.minecraft.world.level.block.Blocks.*;
import static winterly.registry.CommonWinterlyBlocks.*;
public class WinterlyBlocks {

    public static void init() {
        ICICLE = add("icicle", new IcicleBlock(copyOf(ICE).pushReaction(PushReaction.DESTROY)));
        ICICLE_BLOCK = add("icicle_block", new TransparentBlock(copyOf(PACKED_ICE).nonOpaque()));
        PACKED_ICICLE_BLOCK = add("packed_icicle_block", new TransparentBlock(copyOf(PACKED_ICE).nonOpaque()));
        ICICLE_PANE = add("icicle_pane", new BasePaneBlock(copyOf(PACKED_ICE).nonOpaque()));
        ICICLE_BARS = add("icicle_bars", new BasePaneBlock(copyOf(ICE).nonOpaque()));
        CRYOMARBLE_BLOCK = add("cryomarble_block", new Block(copyOf(DIAMOND_BLOCK)));
        SNOWGUY = add("snowguy", new SnowguyBlock(copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        SNOWBALL_WALL = add("snowball_wall", new SnowballWallBlock(copyOf(WHITE_WOOL).sounds(SoundType.SNOW).nonOpaque()));
        DENSE_SNOW = add("dense_snow", new Block(copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        DENSE_SNOW_STAIRS = add("dense_snow_stairs", new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        DENSE_SNOW_SLAB = add("dense_snow_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));

        SNOW_BRICKS = add("snow_bricks", new Block(copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        SNOW_BRICK_STAIRS = add("snow_brick_stairs", new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        SNOW_BRICK_SLAB = add("snow_brick_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(SoundType.SNOW)));
        FROZEN_GRASS = add("frozen_grass", new FrozenGrassBlock(FabricBlockSettings.create().mapColor(MapColor.SNOW).replaceable().notSolid().ticksRandomly().strength(0.1F).requiresTool().sounds(SoundType.SNOW).blockVision((state, world, pos) -> state.getValue(CommonFrozenGrassBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));
        FROZEN_FLOWER = add("frozen_flower", new FrozenFlowerBlock(FabricBlockSettings.create().mapColor(MapColor.SNOW).replaceable().notSolid().ticksRandomly().strength(0.1F).requiresTool().sounds(SoundType.GRASS).blockVision((state, world, pos) -> state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));

        RAW_CRYOMARBLE_SHARD = add("raw_cryomarble_shard", new IcicleBlock(copyOf(WHITE_WOOL).sounds(SoundType.GLASS).luminance(state -> 12)));

        RED_GIFT_BOX = add("red_gift_box", new GiftBoxBlock(copyOf(RED_WOOL).pistonBehavior(PushReaction.DESTROY)));
        ORANGE_GIFT_BOX = add("orange_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        YELLOW_GIFT_BOX = add("yellow_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        GREEN_GIFT_BOX = add("green_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        CYAN_GIFT_BOX = add("cyan_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        BLUE_GIFT_BOX = add("blue_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        PURPLE_GIFT_BOX = add("purple_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        BLACK_GIFT_BOX = add("black_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        WHITE_GIFT_BOX = add("white_gift_box", new GiftBoxBlock(copyOf(RED_GIFT_BOX)));

        GARLAND_LIGHTS = add("garland_lights", new GarlandLightsBlock(copyOf(GREEN_WOOL).pistonBehavior(PushReaction.DESTROY).noCollision().sounds(SoundType.CANDLE)));
        RAINY_GARLAND_LIGHTS = add("rainy_garland_lights", new GarlandLightsBlock(copyOf(WHITE_WOOL).pistonBehavior(PushReaction.DESTROY).noCollision().sounds(SoundType.CANDLE)));

        ITEMS.forEach((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));
        BLOCKS.forEach((id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block));
    }

    public static <T extends Block> T add(String name, T block) {
        return addBlockItem(name, block, new BlockItem(block, new Item.Properties()));
    }

    private static <T extends Block> T addBlockItem(String name, T block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.registerBlocks(Item.BY_BLOCK, item);
            ITEMS.put(Winterly.id(name), item);
        }
        return block;
    }

    private static <T extends Block> T addBlock(String name, T block) {
        BLOCKS.put(Winterly.id(name), block);
        return block;
    }

    public static FabricBlockSettings copyOf(Block block) {
        return FabricBlockSettings.copyOf(block);
    }
}
