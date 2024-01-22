package winterly.mixin.common;

import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.registry.CommonWinterlyBlocks;

@Mixin(DiggerItem.class)
public abstract class MiningToolItemMixin {

	@Shadow
	@Final
	private TagKey<Block> blocks;

	@Shadow
	@Final
	protected float speed;

	@Inject(method = "isCorrectToolForDrops", at = @At("HEAD"), cancellable = true)
	void isSuitableFor(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(blocks.equals(BlockTags.MINEABLE_WITH_SHOVEL)) {
			if(state.is(CommonWinterlyBlocks.FROZEN_FLOWER)) {
				if(state.getValue(CommonFrozenFlowerBlock.LAYERS) != 0) {
					cir.setReturnValue(true);
				}
				else {
					cir.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
	void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if(blocks.equals(BlockTags.MINEABLE_WITH_SHOVEL)) {
			if(state.is(CommonWinterlyBlocks.FROZEN_FLOWER)) {
				if(state.getValue(CommonFrozenFlowerBlock.LAYERS) != 0) {
					cir.setReturnValue(speed);
				}
				else {
					cir.setReturnValue(1.0F);
				}
			}
		}
	}
}
