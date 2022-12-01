package winterly.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldAccess;

public class FrozenGrassBlock extends SnowBlock {

	public FrozenGrassBlock(Settings settings) {
		super(settings);
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		int layers = state.get(LAYERS);

		if(layers == 8) {
			return false;
		}
		if(context.getStack().isOf(Blocks.SNOW.asItem())) {
			return context.getSide() == Direction.UP;
		}

		return layers == 1;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getLightLevel(LightType.BLOCK, pos) > 11) {
			dropStacks(state, world, pos);
			world.setBlockState(pos, Blocks.GRASS.getDefaultState());
		}
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		super.onBroken(world, pos, state);
		world.setBlockState(pos, Blocks.GRASS.getDefaultState(), Block.NOTIFY_ALL);
	}
}
