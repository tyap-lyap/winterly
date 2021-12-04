package ru.tlmclub.winterly.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.tlmclub.winterly.data.WinterlyPatterns;

import java.util.Optional;

@SuppressWarnings("deprecation")
public class PresentBlock extends BaseBlock {
    public static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 14, 14);

    public PresentBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

//    @Environment(EnvType.CLIENT)
//    @Override
//    public JsonUnbakedModel getItemModel(Identifier blockId) {
//        return this.getBlockModel(blockId, this.getDefaultState());
//    }

    @Environment(EnvType.CLIENT)
    @Nullable
    @Override
    public JsonUnbakedModel getBlockModel(Identifier blockId, BlockState blockState) {
        Optional<String> pattern = PatternsHelper.createJson(WinterlyPatterns.PRESENT, blockId);
        return ModelsHelper.fromPattern(pattern);
    }
}
