package winterly.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.registry.WinterlyBlocks;

@Mixin(SnowBlock.class)
public abstract class SnowBlockMixin {

	@Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
	void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
		BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if(state.isOf(WinterlyBlocks.FROZEN_GRASS)) {
			int layers = state.get(SnowBlock.LAYERS);
			cir.setReturnValue(state.with(SnowBlock.LAYERS, Math.min(8, layers + 1)));
		}
		else if(state.isOf(Blocks.GRASS)) {
			cir.setReturnValue(WinterlyBlocks.FROZEN_GRASS.getDefaultState());
		}
	}
}
