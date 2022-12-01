package winterly.mixin.common;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.State;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.block.FrozenFlowerBlock;
import winterly.registry.WinterlyBlocks;

@Mixin(AbstractBlock.AbstractBlockState.class)
public abstract class BlockStateMixin extends State<Block, BlockState> {

	protected BlockStateMixin(Block owner, ImmutableMap<Property<?>, Comparable<?>> entries, MapCodec<BlockState> codec) {
		super(owner, entries, codec);
	}

	@Shadow
	public abstract boolean isOf(Block block);

	@Shadow
	@Final
	private float hardness;

	@Inject(method = "getHardness", at = @At("HEAD"), cancellable = true)
	void getHardness(BlockView world, BlockPos pos, CallbackInfoReturnable<Float> cir) {
		if(isOf(WinterlyBlocks.FROZEN_FLOWER)) {
			if(get(FrozenFlowerBlock.LAYERS) == 0) {
				cir.setReturnValue(0.0F);
			}
			else {
				cir.setReturnValue(hardness);
			}
		}
	}

	@Inject(method = "isToolRequired", at = @At("HEAD"), cancellable = true)
	void isToolRequired(CallbackInfoReturnable<Boolean> cir) {
		if(isOf(WinterlyBlocks.FROZEN_FLOWER)) {
			if(get(FrozenFlowerBlock.LAYERS) == 0) {
				cir.setReturnValue(false);
			}
			else {
				cir.setReturnValue(true);
			}
		}
	}
}
