package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.CommonFrozenFlowerBlock;

@Mixin(SnowyDirtBlock.class)
public abstract class SnowyBlockMixin {

	@Inject(method = "isSnowySetting", at = @At("HEAD"), cancellable = true)
	private static void iSnow(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(state.is(ForgeRegistries.BLOCKS.getValue(Winterly.id("frozen_flower"))) && state.getValue(CommonFrozenFlowerBlock.LAYERS) >= 1) {
			cir.setReturnValue(true);
		}
	}
}
