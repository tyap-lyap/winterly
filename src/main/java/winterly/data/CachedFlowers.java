package winterly.data;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CachedFlowers {

	public static Map<RegistryKey<World>, Map<BlockPos, Block>> data = new LinkedHashMap<>();

	@Nullable
	public static Block getFlower(RegistryKey<World> world, BlockPos pos) {
		var map = data.get(world);
		if(map != null) {
			return map.get(pos);
		}
		else {
			var newMap = new LinkedHashMap<BlockPos, Block>();
			data.put(world, newMap);
			return null;
		}
	}

	public static void cacheFlower(RegistryKey<World> world, BlockPos pos, Block flower) {
		var map = data.get(world);
		if(map != null) {
			map.put(pos, flower);
		}
		else {
			var newMap = new LinkedHashMap<BlockPos, Block>();
			newMap.put(pos, flower);
			data.put(world, newMap);
		}
	}
}
