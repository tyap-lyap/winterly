package winterly.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;

public class CommonFrozenGrassBlock extends SnowLayerBlock {
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	public CommonFrozenGrassBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 1).setValue(PERSISTENT, false));
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(PERSISTENT);
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		int layers = state.getValue(LAYERS);

		if(layers == 8 || context.getItemInHand().is(this.asItem())) {
			return false;
		}
		if(context.getItemInHand().is(Blocks.SNOW.asItem())) {
			return context.getClickedFace() == Direction.UP;
		}

		return layers == 1;
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.getBrightness(LightLayer.BLOCK, pos) > 11) {
			dropResources(state, world, pos);
			world.setBlockAndUpdate(pos, Blocks.SHORT_GRASS.defaultBlockState());
		}
	}

	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		super.destroy(world, pos, state);
		world.setBlock(pos, Blocks.SHORT_GRASS.defaultBlockState(), Block.UPDATE_ALL);
	}
}
