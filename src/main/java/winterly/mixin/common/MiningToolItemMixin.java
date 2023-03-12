package winterly.mixin.common;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MiningToolItem;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.registry.tag.TagKey;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.FrozenFlowerBlock;
import winterly.registry.WinterlyBlocks;

@Mixin(MiningToolItem.class)
public abstract class MiningToolItemMixin {

	@Shadow
	@Final
	private TagKey<Block> effectiveBlocks;

	@Shadow
	@Final
	protected float miningSpeed;

	@Inject(method = "isSuitableFor", at = @At("HEAD"), cancellable = true)
	void isSuitableFor(BlockState state, CallbackInfoReturnable<Boolean> cir) {
		if(effectiveBlocks.equals(BlockTags.SHOVEL_MINEABLE)) {
			if(state.isOf(WinterlyBlocks.FROZEN_FLOWER)) {
				if(state.get(FrozenFlowerBlock.LAYERS) != 0) {
					cir.setReturnValue(true);
				}
				else {
					cir.setReturnValue(false);
				}
			}
		}
	}

	@Inject(method = "getMiningSpeedMultiplier", at = @At("HEAD"), cancellable = true)
	void getMiningSpeedMultiplier(ItemStack stack, BlockState state, CallbackInfoReturnable<Float> cir) {
		if(effectiveBlocks.equals(BlockTags.SHOVEL_MINEABLE)) {
			if(state.isOf(WinterlyBlocks.FROZEN_FLOWER)) {
				if(state.get(FrozenFlowerBlock.LAYERS) != 0) {
					cir.setReturnValue(miningSpeed);
				}
				else {
					cir.setReturnValue(1.0F);
				}
			}
		}
	}
}
