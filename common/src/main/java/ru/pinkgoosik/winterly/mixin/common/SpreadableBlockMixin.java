package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SpreadingSnowyBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;

@Mixin(SpreadingSnowyBlock.class)
public abstract class SpreadableBlockMixin {

	@Inject(method = "canStayAlive", at = @At("HEAD"), cancellable = true)
	private static void canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState up = world.getBlockState(pos.above());
		if(up.getBlock() == BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_grass")) || up.getBlock() == BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_flower"))) {
			cir.setReturnValue(true);
		}
	}
}
