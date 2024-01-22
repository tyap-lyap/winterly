package winterly.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public abstract class CommonWinterlyBlocks {
    public static final Map<ResourceLocation, BlockItem> ITEMS = new LinkedHashMap<>();
    public static final Map<ResourceLocation, Block> BLOCKS = new LinkedHashMap<>();

//    public static final Block RED_SOCK = add("red_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));
//    public static final Block GREEN_SOCK = add("green_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));
//    public static final Block BLUE_SOCK = add("blue_sock", new SockBlock(copyOf(CANDLE).sounds(BlockSoundGroup.WOOL)));

    public static Block ICICLE;
    public static Block ICICLE_BLOCK;
	public static Block PACKED_ICICLE_BLOCK;
    public static Block ICICLE_PANE;
    public static Block ICICLE_BARS;
	public static Block CRYOMARBLE_BLOCK;
    public static Block SNOWGUY;
    public static Block SNOWBALL_WALL;
    public static Block DENSE_SNOW;
    public static Block DENSE_SNOW_STAIRS;
    public static Block DENSE_SNOW_SLAB;

    public static Block SNOW_BRICKS;
    public static Block SNOW_BRICK_STAIRS;
    public static Block SNOW_BRICK_SLAB;
	public static Block FROZEN_GRASS;
	public static Block FROZEN_FLOWER;

    public static Block RAW_CRYOMARBLE_SHARD;

	public static Block RED_GIFT_BOX;
	public static Block ORANGE_GIFT_BOX;
	public static Block YELLOW_GIFT_BOX;
	public static Block GREEN_GIFT_BOX;
	public static Block CYAN_GIFT_BOX;
	public static Block BLUE_GIFT_BOX;
	public static Block PURPLE_GIFT_BOX;
	public static Block BLACK_GIFT_BOX;
	public static Block WHITE_GIFT_BOX;

    public static Block GARLAND_LIGHTS;
    public static Block RAINY_GARLAND_LIGHTS;

}
