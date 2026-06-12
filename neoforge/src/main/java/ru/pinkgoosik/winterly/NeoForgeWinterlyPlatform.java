package ru.pinkgoosik.winterly;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.chunk.LevelChunk;
import ru.pinkgoosik.winterly.compat.WinterlyPlatform;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

public class NeoForgeWinterlyPlatform implements WinterlyPlatform {

	@Override
	public ChunkFlowerCache getChunkFlowerCache(LevelChunk chunk) {
		return chunk.getData(NeoForgeAttachments.CHUNK_FLOWER_CACHE);
	}

	@Override
	public void setChunkFlowerCache(LevelChunk chunk, ChunkFlowerCache cache) {
		chunk.setData(NeoForgeAttachments.CHUNK_FLOWER_CACHE, cache);
	}

	@Override
	public DecorationData getDecorationData(Entity entity) {
		DecorationData data = entity.getExistingDataOrNull(NeoForgeAttachments.MOB_DECORATION);
		return data == null ? DecorationData.DEFAULT : data;
	}

	@Override
	public void setDecorationData(Entity entity, DecorationData data) {
		entity.setData(NeoForgeAttachments.MOB_DECORATION, data);
	}

	@Override
	public void registerCurio(Item item) {
		WinterlyCuriosIntegration.registerCurio(item);
	}
}
