package winterly.neoforge.registry;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;
import winterly.Winterly;
import winterly.block.*;
import winterly.block.base.BasePaneBlock;
import winterly.block.base.BaseStairsBlock;
import winterly.neoforge.block.BaseTransparentBlock;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;
import static winterly.registry.CommonWinterlyBlocks.*;
import static winterly.registry.CommonWinterlyItems.ITEMS;

public class WinterlyBlocks {
    public static final DeferredRegister.Blocks BLOCKS_REGISTERER = DeferredRegister.createBlocks("winterly");

    public static void init(IEventBus eventBus) {
        add("icicle", () -> ICICLE = new IcicleBlock(copyOf(ICE).pushReaction(PushReaction.DESTROY)));
        add("icicle_block", () -> ICICLE_BLOCK = new BaseTransparentBlock(copyOf(PACKED_ICE).noOcclusion()));
        add("packed_icicle_block", () -> PACKED_ICICLE_BLOCK = new BaseTransparentBlock(copyOf(PACKED_ICE).noOcclusion()));
        add("icicle_pane", () -> ICICLE_PANE = new BasePaneBlock(copyOf(PACKED_ICE).noOcclusion()));
        add("icicle_bars", () -> ICICLE_BARS = new BasePaneBlock(copyOf(ICE).noOcclusion()));
        add("cryomarble_block", () -> CRYOMARBLE_BLOCK = new Block(copyOf(DIAMOND_BLOCK)));
        add("snowguy", () -> SNOWGUY = new SnowguyBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("snowball_wall", () -> SNOWBALL_WALL = new SnowballWallBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW).noOcclusion()));
        add("dense_snow", () -> DENSE_SNOW = new Block(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("dense_snow_stairs", () -> DENSE_SNOW_STAIRS = new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("dense_snow_slab", () -> DENSE_SNOW_SLAB = new SlabBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));

        add("snow_bricks", () -> SNOW_BRICKS = new Block(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("snow_brick_stairs", () -> SNOW_BRICK_STAIRS = new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("snow_brick_slab", () -> SNOW_BRICK_SLAB = new SlabBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
        add("frozen_grass", () -> FROZEN_GRASS = new CommonFrozenGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((state, world, pos) -> state.getValue(CommonFrozenGrassBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));
        add("frozen_flower", () -> FROZEN_FLOWER = new CommonFrozenFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.GRASS).isViewBlocking((state, world, pos) -> state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));

        add("raw_cryomarble_shard", () -> RAW_CRYOMARBLE_SHARD = new IcicleBlock(copyOf(WHITE_WOOL).sound(SoundType.GLASS).lightLevel(state -> 12)));

        add("red_gift_box", () -> RED_GIFT_BOX = new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
        add("orange_gift_box", () -> ORANGE_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("yellow_gift_box", () -> YELLOW_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("green_gift_box", () -> GREEN_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("cyan_gift_box", () -> CYAN_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("blue_gift_box", () -> BLUE_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("purple_gift_box", () -> PURPLE_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("black_gift_box", () -> BLACK_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));
        add("white_gift_box", () -> WHITE_GIFT_BOX = new GiftBoxBlock(copyOf(RED_GIFT_BOX)));

        add("garland_lights", () -> GARLAND_LIGHTS = new GarlandLightsBlock(copyOf(GREEN_WOOL).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.CANDLE)));
        add("rainy_garland_lights", () -> RAINY_GARLAND_LIGHTS = new GarlandLightsBlock(copyOf(WHITE_WOOL).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.CANDLE)));

//        ITEMS.forEach((id, item) -> Registry.register(BuiltInRegistries.ITEM, id, item));
//        BLOCKS.forEach((id, block) -> Registry.register(BuiltInRegistries.BLOCK, id, block));

        BLOCKS_REGISTERER.register(eventBus);
    }

    public static <T extends Block> T add(String name, Supplier<? extends T> blockSup) {
        DeferredBlock<T> toReturn = BLOCKS_REGISTERER.register(name, () -> {
            var block = blockSup.get();
            BLOCKS.put(Winterly.id(name), block);
            return block;
        });
        WinterlyItems.ITEMS_REGISTERER.register(name, () -> {
            var item = new BlockItem(toReturn.get(), new Item.Properties());
            ITEMS.put(Winterly.id(name), item);
            return item;
        });
        return null;
    }

    public static BlockBehaviour.Properties copyOf(Block block) {
        return BlockBehaviour.Properties.ofFullCopy(block);
    }
}
