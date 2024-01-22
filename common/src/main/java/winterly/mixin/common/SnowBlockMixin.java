package winterly.mixin.common;

import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.block.CommonFrozenGrassBlock;
import winterly.registry.CommonWinterlyBlocks;

@Mixin(SnowLayerBlock.class)
public abstract class SnowBlockMixin {

	@Inject(method = "getStateForPlacement", at = @At("HEAD"), cancellable = true)
	void getPlacementState(BlockPlaceContext ctx, CallbackInfoReturnable<BlockState> cir) {
		BlockState state = ctx.getLevel().getBlockState(ctx.getClickedPos());
		if(state.is(CommonWinterlyBlocks.FROZEN_GRASS)) {
			int layers = state.getValue(SnowLayerBlock.LAYERS);
			cir.setReturnValue(state.setValue(SnowLayerBlock.LAYERS, Math.min(8, layers + 1)).setValue(CommonFrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.is(CommonWinterlyBlocks.FROZEN_FLOWER)) {
			int layers = state.getValue(CommonFrozenFlowerBlock.LAYERS);
			cir.setReturnValue(state.setValue(CommonFrozenFlowerBlock.LAYERS, Math.min(8, layers + 1)).setValue(CommonFrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.is(Blocks.SHORT_GRASS)) {
			cir.setReturnValue(CommonWinterlyBlocks.FROZEN_GRASS.defaultBlockState().setValue(CommonFrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.getBlock() instanceof FlowerBlock) {
			cir.setReturnValue(CommonWinterlyBlocks.FROZEN_FLOWER.defaultBlockState().setValue(CommonFrozenFlowerBlock.LAYERS, 1).setValue(CommonFrozenFlowerBlock.PERSISTENT, true));
		}
	}
}
