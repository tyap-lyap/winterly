package winterly.data;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.LinkedHashMap;

public class WorldData implements ComponentV3 {

	public World world;

	public WorldData(World world) {
		this.world = world;
	}

	@Override
	public void readFromNbt(NbtCompound tag) {
		CachedFlowers.data = new LinkedHashMap<>();
		var cachedFlowerNbt = tag.getCompound("cachedFlower");

		if(!cachedFlowerNbt.isEmpty()) {
			int size = cachedFlowerNbt.getInt("size");

			for(int i = 0; i < size; i++) {
				NbtCompound entry = cachedFlowerNbt.getCompound(String.valueOf(i));
				BlockPos pos = new BlockPos(entry.getInt("x"), entry.getInt("y"), entry.getInt("z"));
				var block = Registry.BLOCK.getOrEmpty(new Identifier(entry.getString("block")));
				block.ifPresent(bl -> CachedFlowers.cacheFlower(world.getRegistryKey(), pos, bl));
			}
		}
	}

	@Override
	public void writeToNbt(NbtCompound tag) {
		NbtCompound cachedFlowerNbt = new NbtCompound();
		var map = CachedFlowers.data.get(world.getRegistryKey());
		if(map != null) {
			cachedFlowerNbt.putInt("size", map.size());
			int index = -1;

			for(var entry : map.entrySet()) {
				index++;
				NbtCompound entryNbt = new NbtCompound();
				entryNbt.putInt("x", entry.getKey().getX());
				entryNbt.putInt("y", entry.getKey().getY());
				entryNbt.putInt("z", entry.getKey().getZ());
				entryNbt.putString("block", Registry.BLOCK.getId(entry.getValue()).toString());
				cachedFlowerNbt.put(String.valueOf(index), entryNbt);
			}
			tag.put("cachedFlower", cachedFlowerNbt);
		}

	}
}
