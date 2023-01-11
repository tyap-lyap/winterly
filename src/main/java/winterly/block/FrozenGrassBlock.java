package winterly.block;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.WorldAccess;

public class FrozenGrassBlock extends SnowBlock {
	public static final BooleanProperty PERSISTENT = Properties.PERSISTENT;

	public FrozenGrassBlock(Settings settings) {
		super(settings);
		this.setDefaultState(this.stateManager.getDefaultState().with(LAYERS, 1).with(PERSISTENT, false));
	}

	@Override
	protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
		super.appendProperties(builder);
		builder.add(PERSISTENT);
	}

	@Override
	public boolean canReplace(BlockState state, ItemPlacementContext context) {
		int layers = state.get(LAYERS);

		if(layers == 8 || context.getStack().isOf(this.asItem())) {
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
		else if(FabricLoader.getInstance().isModLoaded("seasons")) {
			if(!state.get(PERSISTENT) && world.getLightLevel(LightType.SKY, pos) > 0 && world.getBiome(pos).value().getTemperature(pos) >= 0.15F) {
				dropStacks(state, world, pos);
				world.setBlockState(pos, Blocks.GRASS.getDefaultState());
			}
		}
	}

	@Override
	public void onBroken(WorldAccess world, BlockPos pos, BlockState state) {
		super.onBroken(world, pos, state);
		world.setBlockState(pos, Blocks.GRASS.getDefaultState(), Block.NOTIFY_ALL);
	}
}
