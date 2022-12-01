package winterly.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowyBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.FrozenFlowerBlock;
import winterly.registry.WinterlyBlocks;

@Mixin(SnowyBlock.class)
public abstract class SnowyBlockMixin {

	@Inject(method = "isSnow", at = @At("HEAD"), cancellable = true)
	private static void iSnow(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(state.isOf(WinterlyBlocks.FROZEN_FLOWER) && state.get(FrozenFlowerBlock.LAYERS) >= 1) {
			cir.setReturnValue(true);
		}
	}
}
