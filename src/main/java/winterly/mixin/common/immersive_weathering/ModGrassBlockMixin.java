package winterly.mixin.common.immersive_weathering;

import com.ordana.immersive_weathering.blocks.soil.ModGrassBlock;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import winterly.registry.WinterlyBlocks;

@Mixin(ModGrassBlock.class)
public abstract class ModGrassBlockMixin {

	@Inject(method = "canBeGrass", at = @At("HEAD"), cancellable = true)
	private static void canBeGrass(BlockState state, WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
		BlockState up = world.getBlockState(pos.up());
		if(up.isOf(WinterlyBlocks.FROZEN_GRASS) || up.isOf(WinterlyBlocks.FROZEN_FLOWER)) {
			cir.setReturnValue(true);
		}
	}
}
