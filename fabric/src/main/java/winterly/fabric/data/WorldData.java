package winterly.fabric.data;

import dev.onyxstudios.cca.api.v3.component.ComponentV3;
import java.util.LinkedHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import winterly.data.CachedFlowers;

public class WorldData implements ComponentV3 {

	public Level world;

	public WorldData(Level world) {
		this.world = world;
	}

	@Override
	public void readFromNbt(CompoundTag tag) {
		CachedFlowers.instance.data = new LinkedHashMap<>();
		var cachedFlowerNbt = tag.getCompound("cachedFlower");

		if(!cachedFlowerNbt.isEmpty()) {
			int size = cachedFlowerNbt.getInt("size");

			for(int i = 0; i < size; i++) {
				CompoundTag entry = cachedFlowerNbt.getCompound(String.valueOf(i));
				BlockPos pos = new BlockPos(entry.getInt("x"), entry.getInt("y"), entry.getInt("z"));
				var block = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(entry.getString("block")));
				block.ifPresent(bl -> CachedFlowers.cacheFlower(world, pos, bl));
			}
		}
	}

	@Override
	public void writeToNbt(CompoundTag tag) {
		CompoundTag cachedFlowerNbt = new CompoundTag();
		var map = CachedFlowers.instance.data.get(world.dimension());
		if(map != null) {
			cachedFlowerNbt.putInt("size", map.size());
			int index = -1;

			for(var entry : map.entrySet()) {
				index++;
				CompoundTag entryNbt = new CompoundTag();
				entryNbt.putInt("x", entry.getKey().getX());
				entryNbt.putInt("y", entry.getKey().getY());
				entryNbt.putInt("z", entry.getKey().getZ());
				entryNbt.putString("block", BuiltInRegistries.BLOCK.getKey(entry.getValue()).toString());
				cachedFlowerNbt.put(String.valueOf(index), entryNbt);
			}
			tag.put("cachedFlower", cachedFlowerNbt);
		}

	}
}
