package ru.tlmclub.winterly.block;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;
import ru.bclib.blocks.BaseBlock;
import ru.bclib.client.models.ModelsHelper;
import ru.bclib.client.models.PatternsHelper;
import ru.bclib.client.render.BCLRenderLayer;
import ru.bclib.interfaces.RenderLayerProvider;
import ru.tlmclub.winterly.data.WinterlyPatterns;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("deprecation")
public class PresentBlock extends BaseBlock implements RenderLayerProvider {
    public static final VoxelShape SHAPE = Block.createCuboidShape(2, 0, 2, 14, 14, 14);

    public PresentBlock(Settings settings) {
        super(settings);
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return SHAPE;
    }

    @Environment(EnvType.CLIENT)
    public JsonUnbakedModel getItemModel(Identifier id) {
        return ModelsHelper.createItemModel(id);
    }

    @Environment(EnvType.CLIENT)
    @Nullable
    @Override
    public JsonUnbakedModel getBlockModel(Identifier blockId, BlockState state) {
        Optional<String> pattern = PatternsHelper.createJson(WinterlyPatterns.PRESENT, blockId);
        return ModelsHelper.fromPattern(pattern);
    }

    @Override
    public BCLRenderLayer getRenderLayer() {
        return BCLRenderLayer.CUTOUT;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        tooltip.add(new TranslatableText("misc.winterly.placeable"));
        super.appendTooltip(stack, world, tooltip, options);
    }
}
