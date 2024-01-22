package winterly.fabric.block;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.state.BlockState;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.data.CachedFlowers;

import java.util.Objects;

public class FrozenFlowerBlock extends CommonFrozenFlowerBlock {

	public FrozenFlowerBlock(Properties settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.getBrightness(LightLayer.BLOCK, pos) > 11) {
			dropResources(state, world, pos);
			if(state.getValue(LAYERS) != 0) {
				var cachedFlower = CachedFlowers.getFlower(world, pos);
				world.setBlockAndUpdate(pos, Objects.requireNonNullElse(cachedFlower, this).defaultBlockState());
			}
		}
		else if(FabricLoader.getInstance().isModLoaded("seasons")) {
			if(!state.getValue(PERSISTENT) && world.getBrightness(LightLayer.SKY, pos) > 0 && world.getBiome(pos).value().getTemperature(pos) >= 0.15F) {
				dropResources(state, world, pos);
				if(state.getValue(LAYERS) != 0) {
					var cachedFlower = CachedFlowers.getFlower(world, pos);
					world.setBlockAndUpdate(pos, Objects.requireNonNullElse(cachedFlower, this).defaultBlockState());
				}
			}
		}
	}
}
