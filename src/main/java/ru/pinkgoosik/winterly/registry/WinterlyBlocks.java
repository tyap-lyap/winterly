package ru.pinkgoosik.winterly.registry;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.*;
import ru.pinkgoosik.winterly.block.base.BasePaneBlock;
import ru.pinkgoosik.winterly.block.base.BaseStairsBlock;
import ru.pinkgoosik.winterly.block.base.BaseTransparentBlock;

import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;

public class WinterlyBlocks {
	public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Winterly.MOD_ID);

	public static final RegistryObject<Block> ICICLE = register("icicle", () -> new IcicleBlock(copyOf(ICE).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> ICICLE_BLOCK = register("icicle_block", () -> new BaseTransparentBlock(copyOf(PACKED_ICE).noOcclusion()));
	public static final RegistryObject<Block> PACKED_ICICLE_BLOCK = register("packed_icicle_block", () -> new BaseTransparentBlock(copyOf(PACKED_ICE).noOcclusion()));
	public static final RegistryObject<Block> ICICLE_PANE = register("icicle_pane", () -> new BasePaneBlock(copyOf(PACKED_ICE).noOcclusion()));
	public static final RegistryObject<Block> ICICLE_BARS = register("icicle_bars", () -> new BasePaneBlock(copyOf(ICE).noOcclusion()));
	public static final RegistryObject<Block> CRYOMARBLE_BLOCK = register("cryomarble_block", () -> new Block(copyOf(DIAMOND_BLOCK)));
	public static final RegistryObject<Block> SNOWGUY = register("snowguy", () -> new SnowguyBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> SNOWBALL_WALL = register("snowball_wall", () -> new SnowballWallBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW).noOcclusion()));
	public static final RegistryObject<Block> DENSE_SNOW = register("dense_snow", () -> new Block(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> DENSE_SNOW_STAIRS = register("dense_snow_stairs", () -> new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> DENSE_SNOW_SLAB = register("dense_snow_slab", () -> new SlabBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> SNOW_BRICKS = register("snow_bricks", () -> new Block(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> SNOW_BRICK_STAIRS = register("snow_brick_stairs", () -> new BaseStairsBlock(SNOW_BLOCK.defaultBlockState(), copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> SNOW_BRICK_SLAB = register("snow_brick_slab", () -> new SlabBlock(copyOf(WHITE_WOOL).sound(SoundType.SNOW)));
	public static final RegistryObject<Block> RAW_CRYOMARBLE_SHARD = register("raw_cryomarble_shard", () -> new IcicleBlock(copyOf(WHITE_WOOL).sound(SoundType.GLASS).lightLevel(state -> 12)));
	public static final RegistryObject<Block> RED_GIFT_BOX = register("red_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> ORANGE_GIFT_BOX = register("orange_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> YELLOW_GIFT_BOX = register("yellow_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> GREEN_GIFT_BOX = register("green_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> CYAN_GIFT_BOX = register("cyan_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> BLUE_GIFT_BOX = register("blue_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> PURPLE_GIFT_BOX = register("purple_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> BLACK_GIFT_BOX = register("black_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> WHITE_GIFT_BOX = register("white_gift_box", () -> new GiftBoxBlock(copyOf(RED_WOOL).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> GARLAND_LIGHTS = register("garland_lights", () -> new GarlandLightsBlock(copyOf(GREEN_WOOL).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.CANDLE)));
	public static final RegistryObject<Block> RAINY_GARLAND_LIGHTS = register("rainy_garland_lights", () -> new GarlandLightsBlock(copyOf(WHITE_WOOL).pushReaction(PushReaction.DESTROY).noCollission().sound(SoundType.CANDLE)));

	public static final RegistryObject<Block> FROZEN_GRASS = register("frozen_grass", () -> new CommonFrozenGrassBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.SNOW).isViewBlocking((state, world, pos) -> state.getValue(CommonFrozenGrassBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));
	public static final RegistryObject<Block> FROZEN_FLOWER = register("frozen_flower", () -> new CommonFrozenFlowerBlock(BlockBehaviour.Properties.of().mapColor(MapColor.SNOW).replaceable().forceSolidOff().randomTicks().strength(0.1F).requiresCorrectToolForDrops().sound(SoundType.GRASS).isViewBlocking((state, world, pos) -> state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 8).pushReaction(PushReaction.DESTROY)));

	private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> block) {
		RegistryObject<T> obj = BLOCKS.register(name, block);
		WinterlyItems.registerBlockItem(name, obj);
		return obj;
	}

	static BlockBehaviour.Properties copyOf(Block block) {
		return BlockBehaviour.Properties.copy(block);
	}
}
