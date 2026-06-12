package ru.pinkgoosik.winterly;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.chunk.LevelChunk;
import ru.pinkgoosik.winterly.compat.WinterlyPlatform;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

public class FabricWinterlyPlatform implements WinterlyPlatform {

	@Override
	public ChunkFlowerCache getChunkFlowerCache(LevelChunk chunk) {
		return chunk.getAttached(FabricAttachments.FLOWER_CACHE);
	}

	@Override
	public void setChunkFlowerCache(LevelChunk chunk, ChunkFlowerCache cache) {
		chunk.setAttached(FabricAttachments.FLOWER_CACHE, cache);
	}

	@Override
	public DecorationData getDecorationData(Entity entity) {
		return entity.getAttachedOrElse(FabricAttachments.MOB_DECORATION, DecorationData.DEFAULT);
	}

	@Override
	public void setDecorationData(Entity entity, DecorationData data) {
		entity.setAttached(FabricAttachments.MOB_DECORATION, data);
	}

	@Override
	public void registerCurio(Item item) {
		WinterlyTrinketsIntegration.registerCurio(item);
	}
}
