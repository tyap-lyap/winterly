package winterly.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.SpreadableBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.registry.WinterlyBlocks;

@Mixin(SpreadableBlock.class)
public abstract class SpreadableBlockMixin {

	@Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
	private static void canSurvive(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState up = world.getBlockState(pos.up());
		if(up.isOf(WinterlyBlocks.FROZEN_GRASS)) {
			cir.setReturnValue(true);
		}
	}
}
