package winterly.block;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ShapeContext;
import net.minecraft.entity.ai.pathing.NavigationType;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.IntProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;
import winterly.data.CachedFlowers;
import winterly.registry.WinterlyBlocks;

import java.util.Objects;

@SuppressWarnings("deprecation")
public class FrozenFlowerBlock extends Block {
	public static final IntProperty LAYERS = IntProperty.of("layers", 0, 8);
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

	protected static final VoxelShape[] LAYERS_TO_SHAPE = new VoxelShape[] {
			Block.createCuboidShape(5.0, 0.0, 5.0, 11.0, 10.0, 11.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 2.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 4.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 6.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 8.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 10.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 12.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
			Block.createCuboidShape(0.0, 0.0, 0.0, 16.0, 16.0, 16.0)
	};

	public FrozenFlowerBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 0).with(PERSISTENT, false));
	}

	@Override
	public BlockSoundGroup getSoundGroup(BlockState state) {
		if(state.get(LAYERS) == 0) {
			return BlockSoundGroup.GRASS;
		}
		else {
			return BlockSoundGroup.SNOW;
		}
	}

	@Override
	public boolean canPathfindThrough(BlockState state, BlockView world, BlockPos pos, NavigationType type) {
		if(type.equals(NavigationType.LAND)) {
			return state.get(LAYERS) < 5;
		}
		else return false;
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return LAYERS_TO_SHAPE[state.get(LAYERS)];
	}

	@Override
	public VoxelShape getCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		if(state.get(LAYERS).equals(0) || state.get(LAYERS).equals(1)) {
			return VoxelShapes.empty();
		}
		else {
			return LAYERS_TO_SHAPE[state.get(LAYERS) - 1];
		}
	}

	@Override
	public VoxelShape getSidesShape(BlockState state, BlockView world, BlockPos pos) {
		return LAYERS_TO_SHAPE[state.get(LAYERS)];
	}

	@Override
	public VoxelShape getCameraCollisionShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return LAYERS_TO_SHAPE[state.get(LAYERS)];
	}

	@Override
	public boolean hasSidedTransparency(BlockState state) {
		return true;
	}

	@Override
	public float getAmbientOcclusionLightLevel(BlockState state, BlockView world, BlockPos pos) {
		return state.get(LAYERS) == 8 ? 0.2F : 1.0F;
	}

	@Override
	public boolean canPlaceAt(BlockState state, WorldView world, BlockPos pos) {
		BlockPos down = pos.down();
		return this.canPlantOnTop(world.getBlockState(down));
	}

	protected boolean canPlantOnTop(BlockState floor) {
		return floor.isIn(BlockTags.DIRT) || floor.isOf(Blocks.FARMLAND);
	}

	@Override
	public BlockState getStateForNeighborUpdate(BlockState state, Direction direction, BlockState neighborState, WorldAccess world, BlockPos pos, BlockPos neighborPos) {
		return !state.canPlaceAt(world, pos) ? Blocks.AIR.getDefaultState() : super.getStateForNeighborUpdate(state, direction, neighborState, world, pos, neighborPos);
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		if (world.getLightLevel(LightType.BLOCK, pos) > 11) {
			dropStacks(state, world, pos);
			if(state.get(LAYERS) != 0) {
				var cachedFlower = CachedFlowers.getFlower(world.getRegistryKey(), pos);
				world.setBlockState(pos, Objects.requireNonNullElse(cachedFlower, this).getDefaultState());
			}
		}
		else if(FabricLoader.getInstance().isModLoaded("seasons")) {
			if(!state.get(PERSISTENT) && world.getLightLevel(LightType.SKY, pos) > 0 && world.getBiome(pos).value().getTemperature(pos) >= 0.15F) {
				dropStacks(state, world, pos);
				if(state.get(LAYERS) != 0) {
					var cachedFlower = CachedFlowers.getFlower(world.getRegistryKey(), pos);
					world.setBlockState(pos, Objects.requireNonNullElse(cachedFlower, this).getDefaultState());
				}
			}
		}
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		super.onBroken(world, pos, state);

		if(state.get(LAYERS) != 0 && world instanceof ServerWorld server) {
			var cachedFlower = CachedFlowers.getFlower(server.getRegistryKey(), pos);
			world.setBlockState(pos, Objects.requireNonNullElse(cachedFlower, this).getDefaultState(), Block.NOTIFY_ALL);
		}
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		int layers = state.get(LAYERS);

		if(layers == 8) {
			return false;
		}
		if(context.getStack().isOf(Blocks.SNOW.asItem())) {
			if(layers == 0) {
				return true;
			}
			else {
				return context.getSide() == Direction.UP;
			}
		}
		return false;
	}

	@Nullable
	@Override
	public BlockState getPlacementState(ItemPlacementContext ctx) {
		BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if(state.isOf(Blocks.SNOW) || state.isOf(WinterlyBlocks.FROZEN_GRASS)) {
			return getDefaultState().with(LAYERS, 1).with(PERSISTENT, true);
		}
		else {
			return super.getPlacementState(ctx);
		}
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		builder.add(LAYERS);
		builder.add(PERSISTENT);
	}
}
