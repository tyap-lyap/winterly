package winterly.fabric.block;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import winterly.block.CommonFrozenGrassBlock;

public class FrozenGrassBlock extends CommonFrozenGrassBlock {

	public FrozenGrassBlock(Properties settings) {
		super(settings);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.getBrightness(LightLayer.BLOCK, pos) > 11) {
			dropResources(state, world, pos);
			world.setBlockAndUpdate(pos, Blocks.SHORT_GRASS.defaultBlockState());
		}
		else if(FabricLoader.getInstance().isModLoaded("seasons")) {
			if(!state.getValue(PERSISTENT) && world.getBrightness(LightLayer.SKY, pos) > 0 && world.getBiome(pos).value().getTemperature(pos) >= 0.15F) {
				dropResources(state, world, pos);
				world.setBlockAndUpdate(pos, Blocks.SHORT_GRASS.defaultBlockState());
			}
		}
	}
}
