package winterly.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.block.SnowBlock;
import net.minecraft.item.ItemPlacementContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.FrozenFlowerBlock;
import winterly.block.FrozenGrassBlock;
import winterly.registry.WinterlyBlocks;

@Mixin(SnowBlock.class)
public abstract class SnowBlockMixin {

	@Inject(method = "getPlacementState", at = @At("HEAD"), cancellable = true)
	void getPlacementState(ItemPlacementContext ctx, CallbackInfoReturnable<BlockState> cir) {
		BlockState state = ctx.getWorld().getBlockState(ctx.getBlockPos());
		if(state.isOf(WinterlyBlocks.FROZEN_GRASS)) {
			int layers = state.get(SnowBlock.LAYERS);
			cir.setReturnValue(state.with(SnowBlock.LAYERS, Math.min(8, layers + 1)).with(FrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.isOf(WinterlyBlocks.FROZEN_FLOWER)) {
			int layers = state.get(FrozenFlowerBlock.LAYERS);
			cir.setReturnValue(state.with(FrozenFlowerBlock.LAYERS, Math.min(8, layers + 1)).with(FrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.isOf(Blocks.GRASS)) {
			cir.setReturnValue(WinterlyBlocks.FROZEN_GRASS.getDefaultState().with(FrozenGrassBlock.PERSISTENT, true));
		}
		else if(state.getBlock() instanceof FlowerBlock) {
			cir.setReturnValue(WinterlyBlocks.FROZEN_FLOWER.getDefaultState().with(FrozenFlowerBlock.LAYERS, 1).with(FrozenFlowerBlock.PERSISTENT, true));
		}
	}
}
