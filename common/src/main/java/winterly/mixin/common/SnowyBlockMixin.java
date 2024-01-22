package winterly.mixin.common;

import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.registry.CommonWinterlyBlocks;

@Mixin(SnowyDirtBlock.class)
public abstract class SnowyBlockMixin {

	@Inject(method = "isSnowySetting", at = @At("HEAD"), cancellable = true)
	private static void iSnow(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(state.is(CommonWinterlyBlocks.FROZEN_FLOWER) && state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 1) {
			cir.setReturnValue(true);
		}
	}
}
