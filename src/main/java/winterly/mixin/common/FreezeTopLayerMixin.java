package winterly.mixin.common;

import net.minecraft.block.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.FreezeTopLayerFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import winterly.Winterly;
import winterly.block.FrozenFlowerBlock;
import winterly.registry.WinterlyBlocks;

@Mixin(FreezeTopLayerFeature.class)
public abstract class FreezeTopLayerMixin {

	@Redirect(method = "generate", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"))
	boolean canSetIce(Biome biome, WorldView view, BlockPos pos) {

		if(!biome.doesNotSnow(pos) && (pos.getY() >= view.getBottomY() && pos.getY() < view.getTopY() && view.getLightLevel(LightType.BLOCK, pos) < 10)) {
			BlockState state = view.getBlockState(pos);
			if(view instanceof StructureWorldAccess world) {

				if (Winterly.config.generateFrozenGrass && (state.isOf(Blocks.GRASS) || state.isOf(Blocks.FERN) || state.isOf(Blocks.LARGE_FERN) || state.isOf(Blocks.TALL_GRASS))) {
					world.setBlockState(pos, WinterlyBlocks.FROZEN_GRASS.getDefaultState(), 3);
					BlockState floor = world.getBlockState(pos.down());
					if (floor.contains(SnowyBlock.SNOWY)) {
						world.setBlockState(pos.down(), floor.with(SnowyBlock.SNOWY, Boolean.TRUE), 2);
					}
					if(state.isOf(Blocks.LARGE_FERN) || state.isOf(Blocks.TALL_GRASS)) {
						world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
					}
					return false;
				}
				else if(Winterly.config.generateFrozenFlowers && (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock)) {
					world.setBlockState(pos, WinterlyBlocks.FROZEN_FLOWER.getDefaultState().with(FrozenFlowerBlock.LAYERS, 1), 3);
					BlockState floor = world.getBlockState(pos.down());
					if (floor.contains(SnowyBlock.SNOWY)) {
						world.setBlockState(pos.down(), floor.with(SnowyBlock.SNOWY, Boolean.TRUE), 2);
					}
					if(state.getBlock() instanceof TallFlowerBlock) {
						world.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
					}
					return false;
				}
			}
		}

		return biome.canSetSnow(view, pos);
	}
}
