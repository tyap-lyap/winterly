package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.*;
import ru.pinkgoosik.winterly.block.base.BasePaneBlock;
import ru.pinkgoosik.winterly.block.base.BaseStairsBlock;
import ru.pinkgoosik.winterly.block.base.BaseTransparentBlock;

import java.util.function.BiFunction;
import java.util.function.Supplier;

import static net.minecraft.world.level.block.Blocks.*;

public class WinterlyBlocks {

	public static final DeferredRegister<Block> BLOCKS =
			DeferredRegister.create(Registries.BLOCK, Winterly.MOD_ID);

	public static final RegisterSupplier<Block> ICICLE =
			register(
					"icicle",
					(_, props) -> new IcicleBlock(props),
					() -> copyOf(ICE).pushReaction(PushReaction.DESTROY)
			);

	public static final RegisterSupplier<Block> ICICLE_BLOCK =
			register(
					"icicle_block",
					(_, props) -> new BaseTransparentBlock(props),
					() -> copyOf(PACKED_ICE).noOcclusion()
			);

	public static final RegisterSupplier<Block> PACKED_ICICLE_BLOCK =
			register(
					"packed_icicle_block",
					(_, props) -> new BaseTransparentBlock(props),
					() -> copyOf(PACKED_ICE).noOcclusion()
			);

	public static final RegisterSupplier<Block> ICICLE_PANE =
			register(
					"icicle_pane",
					(_, props) -> new BasePaneBlock(props),
					() -> copyOf(PACKED_ICE).noOcclusion()
			);

	public static final RegisterSupplier<Block> ICICLE_BARS =
			register(
					"icicle_bars",
					(_, props) -> new BasePaneBlock(props),
					() -> copyOf(ICE).noOcclusion()
			);

	public static final RegisterSupplier<Block> CRYOMARBLE_BLOCK =
			register(
					"cryomarble_block",
					(_, props) -> new Block(props),
					() -> copyOf(DIAMOND_BLOCK)
			);

	public static final RegisterSupplier<Block> SNOWGUY =
			register(
					"snowguy",
					(_, props) -> new SnowguyBlock(props),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> SNOWBALL_WALL =
			register(
					"snowball_wall",
					(_, props) -> new SnowballWallBlock(props),
					() -> copyOf(WHITE_WOOL)
							.sound(SoundType.SNOW)
							.noOcclusion()
			);

	public static final RegisterSupplier<Block> DENSE_SNOW =
			register(
					"dense_snow",
					(_, props) -> new Block(props),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> DENSE_SNOW_STAIRS =
			register(
					"dense_snow_stairs",
					(_, props) -> new BaseStairsBlock(
							SNOW_BLOCK.defaultBlockState(),
							props
					),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> DENSE_SNOW_SLAB =
			register(
					"dense_snow_slab",
					(_, props) -> new SlabBlock(props),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> SNOW_BRICKS =
			register(
					"snow_bricks",
					(_, props) -> new Block(props),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> SNOW_BRICK_STAIRS =
			register(
					"snow_brick_stairs",
					(_, props) -> new BaseStairsBlock(
							SNOW_BLOCK.defaultBlockState(),
							props
					),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> SNOW_BRICK_SLAB =
			register(
					"snow_brick_slab",
					(_, props) -> new SlabBlock(props),
					() -> copyOf(WHITE_WOOL).sound(SoundType.SNOW)
			);

	public static final RegisterSupplier<Block> RAW_CRYOMARBLE_SHARD =
			register(
					"raw_cryomarble_shard",
					(_, props) -> new IcicleBlock(props),
					() -> copyOf(WHITE_WOOL)
							.sound(SoundType.GLASS)
							.lightLevel(_ -> 12)
			);

	public static final RegisterSupplier<Block> GARLAND_LIGHTS =
			register(
					"garland_lights",
					(_, props) -> new GarlandLightsBlock(props),
					() -> copyOf(GREEN_WOOL)
							.pushReaction(PushReaction.DESTROY)
							.noCollision()
							.sound(SoundType.CANDLE)
			);

	public static final RegisterSupplier<Block> RAINY_GARLAND_LIGHTS =
			register(
					"rainy_garland_lights",
					(_, props) -> new GarlandLightsBlock(props),
					() -> copyOf(WHITE_WOOL)
							.pushReaction(PushReaction.DESTROY)
							.noCollision()
							.sound(SoundType.CANDLE)
			);

	public static final RegisterSupplier<Block> FROZEN_GRASS =
			register(
					"frozen_grass",
					(_, props) -> new CommonFrozenGrassBlock(props),
					() -> BlockBehaviour.Properties.of()
							.mapColor(MapColor.SNOW)
							.replaceable()
							.forceSolidOff()
							.randomTicks()
							.strength(0.1F)
							.requiresCorrectToolForDrops()
							.sound(SoundType.SNOW)
							.isViewBlocking((state, _, _) ->
									state.getValue(CommonFrozenGrassBlock.LAYERS) >= 8)
							.pushReaction(PushReaction.DESTROY)
			);

	public static final RegisterSupplier<Block> FROZEN_FLOWER =
			register(
					"frozen_flower",
					(_, props) -> new CommonFrozenFlowerBlock(props),
					() -> BlockBehaviour.Properties.of()
							.mapColor(MapColor.SNOW)
							.replaceable()
							.forceSolidOff()
							.randomTicks()
							.strength(0.1F)
							.requiresCorrectToolForDrops()
							.sound(SoundType.GRASS)
							.isViewBlocking((state, _, _) ->
									state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 8)
							.pushReaction(PushReaction.DESTROY)
			);

	private static <T extends Block> RegisterSupplier<T> register(
			String name,
			BiFunction<String, BlockBehaviour.Properties, T> factory,
			Supplier<BlockBehaviour.Properties> propertiesSupplier
	) {
		RegisterSupplier<T> obj = BLOCKS.register(name, () -> {

			ResourceKey<Block> key = ResourceKey.create(
					Registries.BLOCK,
					Identifier.fromNamespaceAndPath(
							Winterly.MOD_ID,
							name
					)
			);

			BlockBehaviour.Properties properties =
					propertiesSupplier.get().setId(key);

			return factory.apply(name, properties);
		});

		WinterlyItems.registerBlockItem(name, obj);

		return obj;
	}

	static BlockBehaviour.Properties copyOf(Block block) {
		return BlockBehaviour.Properties.ofFullCopy(block);
	}
}