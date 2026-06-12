package ru.pinkgoosik.winterly.compat;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.chunk.LevelChunk;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.data.DecorationData;

public interface WinterlyPlatform {

	@Nullable
	ChunkFlowerCache getChunkFlowerCache(LevelChunk chunk);
	void setChunkFlowerCache(LevelChunk chunk, ChunkFlowerCache cache);
	DecorationData getDecorationData(Entity entity);
	void setDecorationData(Entity entity, DecorationData data);

	void registerCurio(Item item);
}
