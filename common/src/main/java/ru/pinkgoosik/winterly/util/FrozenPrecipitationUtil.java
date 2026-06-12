package ru.pinkgoosik.winterly.util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.CommonFrozenFlowerBlock;
import ru.pinkgoosik.winterly.data.CachedFlowers;

public final class FrozenPrecipitationUtil {

	public static boolean tryFreezePlant(String source, Biome biome, LevelReader view, LevelAccessor level, BlockPos snowPos, long seed) {
		if (biome.warmEnoughToRain(snowPos, view.getSeaLevel())
			|| snowPos.getY() < view.getMinY()
			|| snowPos.getY() >= view.getMaxY()
			|| view.getBrightness(LightLayer.BLOCK, snowPos) >= 10) {
			return false;
		}

		BlockState snowState = view.getBlockState(snowPos);
		BlockState belowState = view.getBlockState(snowPos.below());
		boolean topCandidate = isPotentialFreezablePlant(snowState);
		boolean belowCandidate = isPotentialFreezablePlant(belowState);
		BlockPos plantPos = topCandidate ? resolvePlantPos(view, snowPos) : resolvePlantPos(view, snowPos.below());
		BlockState plantState = view.getBlockState(plantPos);
		boolean flowerTarget = isFreezableFlower(plantState);
		boolean precipitation = "precipitation".equals(source);
		if (!topCandidate && !belowCandidate && !isPotentialFreezablePlant(plantState)) {
			return false;
		}
		double frostSample = precipitation
			? FrostNoise.sampleForPrecipitation(seed, plantPos.getX(), plantPos.getZ(), level.getGameTime())
			: FrostNoise.sample(seed, plantPos.getX(), plantPos.getZ());
		double frostThreshold = FrostNoise.threshold(flowerTarget, precipitation);
		if (frostSample < frostThreshold) {
			return false;
		}

		if (Winterly.config().generateFrozenGrass() && isFreezableGrass(plantState)) {
			level.setBlock(plantPos, BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_grass")).defaultBlockState(), 3);
			setSnowyFloor(level, plantPos);
			clearUpperHalf(level, plantPos, plantState);
			return true;
		}

		if (Winterly.config().generateFrozenFlowers() && isFreezableFlower(plantState)) {
			if (level instanceof ServerLevel serverLevel) {
				CachedFlowers.cacheFlower(serverLevel.getLevel(), plantPos, plantState.getBlock());
			}
			level.setBlock(
				plantPos,
				BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_flower")).defaultBlockState().setValue(CommonFrozenFlowerBlock.LAYERS, 1),
				3
			);
			setSnowyFloor(level, plantPos);
			clearUpperHalf(level, plantPos, plantState);
			return true;
		}

		return false;
	}

	private static BlockPos resolvePlantPos(LevelReader view, BlockPos plantPos) {
		BlockState plantState = view.getBlockState(plantPos);
		if (plantState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)
			&& plantState.getValue(BlockStateProperties.DOUBLE_BLOCK_HALF) == DoubleBlockHalf.UPPER) {
			return plantPos.below();
		}
		return plantPos;
	}

	private static boolean isFreezableGrass(BlockState state) {
		return state.is(Blocks.SHORT_GRASS) || state.is(Blocks.FERN) || state.is(Blocks.LARGE_FERN) || state.is(Blocks.TALL_GRASS);
	}

	private static boolean isFreezableFlower(BlockState state) {
		return state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock;
	}

	private static boolean isPotentialFreezablePlant(BlockState state) {
		return isFreezableGrass(state) || isFreezableFlower(state);
	}

	private static void setSnowyFloor(LevelAccessor level, BlockPos plantPos) {
		BlockPos floorPos = plantPos.below();
		BlockState floor = level.getBlockState(floorPos);
		if (floor.hasProperty(SnowyBlock.SNOWY)) {
			level.setBlock(floorPos, floor.setValue(SnowyBlock.SNOWY, true), 2);
		}
	}

	private static void clearUpperHalf(LevelAccessor level, BlockPos plantPos, BlockState plantState) {
		if (plantState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) {
			level.setBlock(plantPos.above(), Blocks.AIR.defaultBlockState(), 2);
		}
	}
}
