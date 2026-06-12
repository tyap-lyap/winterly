package ru.pinkgoosik.winterly.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class CachedFlowers {

	@Nullable
	public static Block getFlower(Level world, BlockPos pos) {
		return ChunkDataManager.getFlower(world, pos);
	}

	public static void cacheFlower(Level world, BlockPos pos, Block flower) {
		ChunkDataManager.cacheFlower(world, pos, flower);
	}

	public static void removeFlower(Level world, BlockPos pos) {
		ChunkDataManager.removeFlower(world, pos);
	}
}
