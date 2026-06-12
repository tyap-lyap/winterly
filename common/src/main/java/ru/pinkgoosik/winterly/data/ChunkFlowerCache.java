package ru.pinkgoosik.winterly.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;

public class ChunkFlowerCache {

	private static final Codec<Entry> ENTRY_CODEC = RecordCodecBuilder.create(instance -> instance.group(
		Codec.LONG.fieldOf("pos").forGetter(Entry::pos),
		Codec.INT.fieldOf("block").forGetter(Entry::blockId)
	).apply(instance, Entry::new));
	public static final MapCodec<ChunkFlowerCache> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
		ENTRY_CODEC.listOf()
			.fieldOf("flowers")
			.forGetter(ChunkFlowerCache::entries)
	).apply(instance, ChunkFlowerCache::new));

	private final Long2IntOpenHashMap cachedFlowers = new Long2IntOpenHashMap();

	public ChunkFlowerCache() {
		cachedFlowers.defaultReturnValue(-1);
	}

	public ChunkFlowerCache(List<Entry> flowers) {
		this();
		for (Entry entry : flowers) {
			cachedFlowers.put(entry.pos(), entry.blockId());
		}
	}

	public Block getFlower(BlockPos pos) {
		int blockId = cachedFlowers.get(pos.asLong());
		return blockId == -1 ? null : BuiltInRegistries.BLOCK.byId(blockId);
	}

	public void cacheFlower(BlockPos pos, Block flower) {
		cachedFlowers.put(pos.asLong(), BuiltInRegistries.BLOCK.getId(flower));
	}

	public void removeFlower(BlockPos pos) {
		cachedFlowers.remove(pos.asLong());
	}

	private List<Entry> entries() {
		List<Entry> entries = new ArrayList<>(cachedFlowers.size());
		for (Long2IntMap.Entry entry : cachedFlowers.long2IntEntrySet()) {
			entries.add(new Entry(entry.getLongKey(), entry.getIntValue()));
		}
		return entries;
	}

	public record Entry(long pos, int blockId) {
	}
}
