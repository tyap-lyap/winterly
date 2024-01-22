package winterly.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

@SuppressWarnings("deprecation")
public class GarlandLightsBlock extends Block {
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    private static final VoxelShape EAST_SHAPE = box(0, 3, 0, 1, 13, 16);
    private static final VoxelShape WEST_SHAPE = box(15, 3, 0, 16, 13, 16);
    private static final VoxelShape SOUTH_SHAPE = box(0, 3, 0, 16, 13, 1);
    private static final VoxelShape NORTH_SHAPE = box(0, 3, 15, 16, 13, 16);

    public GarlandLightsBlock(Properties settings) {
        super(settings);
        this.registerDefaultState(defaultBlockState().setValue(FACING, Direction.NORTH));
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return switch (state.getValue(FACING)) {
            case NORTH -> NORTH_SHAPE;
            case SOUTH -> SOUTH_SHAPE;
            case WEST -> WEST_SHAPE;
            default -> EAST_SHAPE;
        };
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        Direction direction = state.getValue(FACING);
        return this.canPlaceOn(world, pos.relative(direction.getOpposite()));
    }

    private boolean canPlaceOn(BlockGetter world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);
        return !state.isAir();
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext ctx) {
        if (!ctx.replacingClickedOnBlock()) {
            BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos().relative(ctx.getClickedFace().getOpposite()));
            if (state.is(this) && state.getValue(FACING) == ctx.getClickedFace()) {
                return null;
            }
        }

        BlockState state = this.defaultBlockState();
        LevelReader world = ctx.getLevel();
        BlockPos pos = ctx.getClickedPos();

        for(Direction direction : ctx.getNearestLookingDirections()) {
            if (direction.getAxis().isHorizontal()) {
                state = state.setValue(FACING, direction.getOpposite());
                if (state.canSurvive(world, pos)) {
                    return state;
                }
            }
        }
        return null;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
