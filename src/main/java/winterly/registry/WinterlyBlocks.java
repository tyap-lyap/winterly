package winterly.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import winterly.Winterly;
import winterly.block.*;
import winterly.block.base.BasePaneBlock;
import winterly.block.base.BaseStairsBlock;

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
	public static final Block PACKED_ICICLE_BLOCK = add("packed_icicle_block", new GlassBlock(copyOf(PACKED_ICE).nonOpaque()));
    public static final Block ICICLE_PANE = add("icicle_pane", new BasePaneBlock(copyOf(PACKED_ICE).nonOpaque()));
    public static final Block ICICLE_BARS = add("icicle_bars", new BasePaneBlock(copyOf(ICE).nonOpaque()));
	public static final Block CRYOMARBLE_BLOCK = add("cryomarble_block", new Block(copyOf(DIAMOND_BLOCK)));
    public static final Block SNOWGUY = add("snowguy", new SnowguyBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOWBALL_WALL = add("snowball_wall", new SnowballWallBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW).nonOpaque()));
    public static final Block DENSE_SNOW = add("dense_snow", new Block(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block DENSE_SNOW_STAIRS = add("dense_snow_stairs", new BaseStairsBlock(SNOW_BLOCK.getDefaultState(), copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block DENSE_SNOW_SLAB = add("dense_snow_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));

    public static final Block SNOW_BRICKS = add("snow_bricks", new Block(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOW_BRICK_STAIRS = add("snow_brick_stairs", new BaseStairsBlock(SNOW_BLOCK.getDefaultState(), copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
    public static final Block SNOW_BRICK_SLAB = add("snow_brick_slab", new SlabBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.SNOW)));
	public static final Block FROZEN_GRASS = add("frozen_grass", new FrozenGrassBlock(copyOf(AbstractBlock.Settings.of(Material.SNOW_LAYER).ticksRandomly().strength(0.1F).requiresTool().sounds(BlockSoundGroup.SNOW).blockVision((state, world, pos) -> state.get(SnowBlock.LAYERS) >= 8))));
	public static final Block FROZEN_FLOWER = add("frozen_flower", new FrozenFlowerBlock(copyOf(AbstractBlock.Settings.of(Material.SNOW_LAYER).ticksRandomly().strength(0.1F).requiresTool().sounds(BlockSoundGroup.GRASS).blockVision((state, world, pos) -> state.get(FrozenFlowerBlock.LAYERS) >= 8))));

    public static final Block RAW_CRYOMARBLE_SHARD = add("raw_cryomarble_shard", new IcicleBlock(copyOf(WHITE_WOOL).sounds(BlockSoundGroup.GLASS).luminance(12)));

	public static final Block RED_GIFT_BOX = add("red_gift_box", new GiftBoxBlock(copyOf(RED_WOOL)));
	public static final Block ORANGE_GIFT_BOX = add("orange_gift_box", new GiftBoxBlock(copyOf(ORANGE_WOOL)));
	public static final Block YELLOW_GIFT_BOX = add("yellow_gift_box", new GiftBoxBlock(copyOf(YELLOW_WOOL)));
	public static final Block GREEN_GIFT_BOX = add("green_gift_box", new GiftBoxBlock(copyOf(GREEN_WOOL)));
	public static final Block CYAN_GIFT_BOX = add("cyan_gift_box", new GiftBoxBlock(copyOf(CYAN_WOOL)));
	public static final Block BLUE_GIFT_BOX = add("blue_gift_box", new GiftBoxBlock(copyOf(BLUE_WOOL)));
	public static final Block PURPLE_GIFT_BOX = add("purple_gift_box", new GiftBoxBlock(copyOf(PURPLE_WOOL)));
	public static final Block BLACK_GIFT_BOX = add("black_gift_box", new GiftBoxBlock(copyOf(BLACK_WOOL)));
	public static final Block WHITE_GIFT_BOX = add("white_gift_box", new GiftBoxBlock(copyOf(WHITE_WOOL)));

    public static final Block GARLAND_LIGHTS = add("garland_lights", new GarlandLightsBlock(copyOf(GREEN_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));
    public static final Block RAINY_GARLAND_LIGHTS = add("rainy_garland_lights", new GarlandLightsBlock(copyOf(WHITE_WOOL).noCollision().sounds(BlockSoundGroup.CANDLE)));

    private static <T extends Block> T add(String name, T block) {
        return addBlockItem(name, block, new BlockItem(block, new FabricItemSettings()));
    }

    private static <T extends Block> T addBlockItem(String name, T block, BlockItem item) {
        addBlock(name, block);
        if (item != null) {
            item.appendBlocks(Item.BLOCK_ITEMS, item);
            ITEMS.put(Winterly.id(name), item);
        }
        return block;
    }

    private static <T extends Block> T addBlock(String name, T block) {
        BLOCKS.put(Winterly.id(name), block);
        return block;
    }

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registries.ITEM, id, item));
        BLOCKS.forEach((id, block) -> Registry.register(Registries.BLOCK, id, block));
    }
}
