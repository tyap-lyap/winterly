package winterly.data;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

public class CachedFlowers {
	public static CachedFlowers instance = new CachedFlowers();

	public Map<ResourceKey<Level>, Map<BlockPos, Block>> data = new LinkedHashMap<>();

	public static Block getFlower(Level world, BlockPos pos) {
		return instance.getFlowerImpl(world, pos);
	}

	@Nullable
	public Block getFlowerImpl(Level world, BlockPos pos) {
		var map = data.get(world.dimension());
		if(map != null) {
			return map.get(pos);
		}
		else {
			var newMap = new LinkedHashMap<BlockPos, Block>();
			data.put(world.dimension(), newMap);
			return null;
		}
	}

	public static void cacheFlower(Level world, BlockPos pos, Block flower) {
		instance.cacheFlowerImpl(world, pos, flower);
	}

	public void cacheFlowerImpl(Level world, BlockPos pos, Block flower) {
		var map = data.get(world.dimension());
		if(map != null) {
			map.put(pos, flower);
		}
		else {
			var newMap = new LinkedHashMap<BlockPos, Block>();
			newMap.put(pos, flower);
			data.put(world.dimension(), newMap);
		}
	}
}
