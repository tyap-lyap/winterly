package ru.pinkgoosik.winterly.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.chunk.LevelChunk;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

import java.util.ServiceLoader;

public class WinterlyPlatformHolder {
	private static WinterlyPlatform INSTANCE = null;

	public static void setInstance(WinterlyPlatform instance) {
		INSTANCE = instance;
	}

	public static WinterlyPlatform get() {
		if (INSTANCE == null) {
			var loader = ServiceLoader.load(WinterlyPlatform.class);
			INSTANCE = loader.findFirst().orElse(new WinterlyPlatform() {
				@Override public ChunkFlowerCache getChunkFlowerCache(LevelChunk chunk) { return null; }
				@Override public void setChunkFlowerCache(LevelChunk chunk, ChunkFlowerCache cache) {}
				@Override public DecorationData getDecorationData(Entity entity) { return DecorationData.DEFAULT; }
				@Override public void setDecorationData(Entity entity, DecorationData data) {}
				@Override public void registerCurio(Item item) {}
			});
		}
		return INSTANCE;
	}
}
