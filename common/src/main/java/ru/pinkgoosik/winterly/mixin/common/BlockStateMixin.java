package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateHolder;
import net.minecraft.world.level.block.state.properties.Property;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.CommonFrozenFlowerBlock;

@Mixin(BlockBehaviour.BlockStateBase.class)
public abstract class BlockStateMixin extends StateHolder<Block, BlockState> {

	@Shadow
	@Final
	private float destroySpeed;

	protected BlockStateMixin(Block owner, Property<?>[] properties, Comparable<?>[] values) {
		super(owner, properties, values);
	}

	@Shadow
	public abstract Block getBlock();

	@Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
	void getHardness(BlockGetter world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		if(getBlock() == BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_flower"))) {
			if(getValue(CommonFrozenFlowerBlock.LAYERS) == 0) {
				cir.setReturnValue(0.0F);
			}
			else {
				cir.setReturnValue(destroySpeed);
			}
		}
	}

	@Inject(method = "requiresCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
	void isToolRequired(CallbackInfoReturnable<Boolean> cir) {
		if(getBlock() == BuiltInRegistries.BLOCK.getValue(Winterly.id("frozen_flower"))) {
			if(getValue(CommonFrozenFlowerBlock.LAYERS) == 0) {
				cir.setReturnValue(false);
			}
			else {
				cir.setReturnValue(true);
			}
		}
	}
}
