package ru.pinkgoosik.winterly.data;

import it.unimi.dsi.fastutil.longs.Long2IntMap;
import it.unimi.dsi.fastutil.longs.Long2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.ForgeRegistry;
import org.jetbrains.annotations.Nullable;

public class ChunkFlowerCache implements ICapabilitySerializable<CompoundTag> {
	public static final Capability<ChunkFlowerCache> CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

	private static final ForgeRegistry<Block> BLOCK_REGISTRY = (ForgeRegistry<Block>) ForgeRegistries.BLOCKS;

	private final Long2IntOpenHashMap cachedFlowers = new Long2IntOpenHashMap();
	private final LazyOptional<ChunkFlowerCache> instance = LazyOptional.of(() -> this);

	public ChunkFlowerCache() {
		cachedFlowers.defaultReturnValue(-1);
	}

	@Nullable
	public Block getFlower(BlockPos pos) {
		int blockId = cachedFlowers.get(pos.asLong());
		return blockId == -1 ? null : BLOCK_REGISTRY.getValue(blockId);
	}

	public void cacheFlower(BlockPos pos, Block flower) {
		cachedFlowers.put(pos.asLong(), BLOCK_REGISTRY.getID(flower));
	}

	public void removeFlower(BlockPos pos) {
		cachedFlowers.remove(pos.asLong());
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		return CAPABILITY.orEmpty(cap, instance);
	}

	@Override
	public CompoundTag serializeNBT() {
		CompoundTag tag = new CompoundTag();
		long[] positions = new long[cachedFlowers.size()];
		int[] blockIds = new int[cachedFlowers.size()];
		int i = 0;
		for (Long2IntMap.Entry entry : cachedFlowers.long2IntEntrySet()) {
			positions[i] = entry.getLongKey();
			blockIds[i] = entry.getIntValue();
			i++;
		}
		tag.putLongArray("positions", positions);
		tag.putIntArray("block_ids", blockIds);
		return tag;
	}

	@Override
	public void deserializeNBT(CompoundTag nbt) {
		cachedFlowers.clear();
		long[] positions = nbt.getLongArray("positions");
		int[] blockIds = nbt.getIntArray("block_ids");
		int len = Math.min(positions.length, blockIds.length);
		for (int i = 0; i < len; i++) {
			cachedFlowers.put(positions[i], blockIds[i]);
		}
	}
}
