package winterly.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import winterly.data.CachedFlowers;
import winterly.registry.CommonWinterlyBlocks;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class CommonFrozenFlowerBlock extends Block {
	public static final IntegerProperty LAYERS = IntegerProperty.create("layers", 0, 8);
	public static final BooleanProperty PERSISTENT = BlockStateProperties.PERSISTENT;

	protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[] {
			Block.box(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
			Block.box(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
	};

	public CommonFrozenFlowerBlock(Properties settings) {
		super(settings);
		this.registerDefaultState(this.stateDefinition.any().setValue(LAYERS, 0).setValue(PERSISTENT, false));
	}

	@Override
	public SoundType getSoundType(BlockState state) {
		if(state.getValue(LAYERS) == 0) {
			return SoundType.GRASS;
		}
		else {
			return SoundType.SNOW;
		}
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter world, BlockPos pos, PathComputationType type) {
		if(type.equals(PathComputationType.LAND)) {
			return state.getValue(LAYERS) < 5;
		}
		else return false;
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return LAYERS_TO_SHAPE[state.getValue(LAYERS)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		if(state.getValue(LAYERS).equals(0) || state.getValue(LAYERS).equals(1)) {
			return Shapes.empty();
		}
		else {
			return LAYERS_TO_SHAPE[state.getValue(LAYERS) - 1];
		}
	}

	@Override
	public VoxelShape getBlockSupportShape(BlockState state, BlockGetter world, BlockPos pos) {
		return LAYERS_TO_SHAPE[state.getValue(LAYERS)];
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return LAYERS_TO_SHAPE[state.getValue(LAYERS)];
	}

	@Override
	public boolean useShapeForLightOcclusion(BlockState state) {
		return true;
	}

	@Override
	public float getShadeBrightness(BlockState state, BlockGetter world, BlockPos pos) {
		return state.getValue(LAYERS) == 8 ? 0.2F : 1.0F;
	}

	@Override
	public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
		BlockPos down = pos.below();
		return this.canPlantOnTop(world.getBlockState(down));
	}

	protected boolean canPlantOnTop(BlockState floor) {
		return floor.is(BlockTags.DIRT) || floor.is(Blocks.FARMLAND);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighborState, LevelAccessor world, BlockPos pos, BlockPos neighborPos) {
		return !state.canSurvive(world, pos) ? Blocks.AIR.defaultBlockState() : super.updateShape(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
		if (world.getBrightness(LightLayer.BLOCK, pos) > 11) {
			dropResources(state, world, pos);
			if(state.getValue(LAYERS) != 0) {
				var cachedFlower = CachedFlowers.getFlower(world, pos);
				world.setBlockAndUpdate(pos, Objects.requireNonNullElse(cachedFlower, this).defaultBlockState());
			}
		}
	}

	@Override
	public void destroy(LevelAccessor world, BlockPos pos, BlockState state) {
		super.destroy(world, pos, state);

		if(state.getValue(LAYERS) != 0 && world instanceof ServerLevel server) {
			var cachedFlower = CachedFlowers.getFlower(server, pos);
			world.setBlock(pos, Objects.requireNonNullElse(cachedFlower, this).defaultBlockState(), Block.UPDATE_ALL);
		}
	}

	@Override
	public boolean canBeReplaced(BlockState state, BlockPlaceContext context) {
		int layers = state.getValue(LAYERS);

		if(layers == 8) {
			return false;
		}
		if(context.getItemInHand().is(Blocks.SNOW.asItem())) {
			if(layers == 0) {
				return true;
			}
			else {
				return context.getClickedFace() == Direction.UP;
			}
		}
		return false;
	}

	@Nullable
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext ctx) {
		BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
		if(state.is(Blocks.SNOW) || state.is(CommonWinterlyBlocks.FROZEN_GRASS)) {
			return defaultBlockState().setValue(LAYERS, 1).setValue(PERSISTENT, true);
		}
		else {
			return super.getStateForPlacement(ctx);
		}
	}

	@Override
	protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
		builder.add(LAYERS);
		builder.add(PERSISTENT);
	}
}
