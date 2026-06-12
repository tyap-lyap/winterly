package ru.pinkgoosik.winterly.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;

public class ChunkDataManager {

	@Nullable
	public static Block getFlower(Level world, BlockPos pos) {
		LevelChunk chunk = world.getChunkAt(pos);
        return chunk.getCapability(ChunkFlowerCache.CAPABILITY).resolve()
			.map(cache -> cache.getFlower(pos))
			.orElse(null);
	}

	public static void cacheFlower(Level world, BlockPos pos, Block flower) {
		LevelChunk chunk = world.getChunkAt(pos);
        chunk.getCapability(ChunkFlowerCache.CAPABILITY).resolve()
			.ifPresent(cache -> cache.cacheFlower(pos, flower));
	}

	public static void removeFlower(Level world, BlockPos pos) {
		LevelChunk chunk = world.getChunkAt(pos);
        chunk.getCapability(ChunkFlowerCache.CAPABILITY).resolve()
			.ifPresent(cache -> cache.removeFlower(pos));
	}
}
