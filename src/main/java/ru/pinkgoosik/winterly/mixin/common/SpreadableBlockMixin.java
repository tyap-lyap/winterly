package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.SpreadingSnowyDirtBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;

@Mixin(SpreadingSnowyDirtBlock.class)
public abstract class SpreadableBlockMixin {

	@Inject(method = "canBeGrass", at = @At("HEAD"), cancellable = true)
	private static void canSurvive(BlockState state, LevelReader world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState up = world.getBlockState(pos.above());
		if(up.is(ForgeRegistries.BLOCKS.getValue(Winterly.id("frozen_grass"))) || up.is(ForgeRegistries.BLOCKS.getValue(Winterly.id("frozen_flower")))) {
			cir.setReturnValue(true);
		}
	}
}
