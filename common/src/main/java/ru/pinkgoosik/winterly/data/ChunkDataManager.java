package ru.pinkgoosik.winterly.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;

public class ChunkDataManager {

	@Nullable
	public static Block getFlower(Level world, BlockPos pos) {
		LevelChunk chunk = world.getChunkAt(pos);
		var cache = WinterlyPlatformHolder.get().getChunkFlowerCache(chunk);
		return cache != null ? cache.getFlower(pos) : null;
	}

	public static void cacheFlower(Level world, BlockPos pos, Block flower) {
		LevelChunk chunk = world.getChunkAt(pos);
		var cache = WinterlyPlatformHolder.get().getChunkFlowerCache(chunk);
		if (cache != null) {
			cache.cacheFlower(pos, flower);
			WinterlyPlatformHolder.get().setChunkFlowerCache(chunk, cache);
		}
	}

	public static void removeFlower(Level world, BlockPos pos) {
		LevelChunk chunk = world.getChunkAt(pos);
		var cache = WinterlyPlatformHolder.get().getChunkFlowerCache(chunk);
		if (cache != null) {
			cache.removeFlower(pos);
			WinterlyPlatformHolder.get().setChunkFlowerCache(chunk, cache);
		}
	}
}
