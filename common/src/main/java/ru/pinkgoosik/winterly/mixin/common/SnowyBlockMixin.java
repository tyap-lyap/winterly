package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.SnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.CommonFrozenFlowerBlock;

@Mixin(SnowyBlock.class)
public abstract class SnowyBlockMixin {

	@Inject(method = "isSnowySetting", at = @At("HEAD"), cancellable = true)
	private static void iSnow(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(state.getBlock() == BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_flower")) && state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 1) {
			cir.setReturnValue(true);
		}
	}
}
