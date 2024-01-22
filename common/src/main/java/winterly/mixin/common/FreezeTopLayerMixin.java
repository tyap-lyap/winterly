package winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SnowyDirtBlock;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import winterly.Winterly;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.registry.CommonWinterlyBlocks;

@Mixin(SnowAndFreezeFeature.class)
public abstract class FreezeTopLayerMixin {

	@Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	boolean canSetIce(Biome biome, LevelReader view, BlockPos pos) {

		if(!biome.warmEnoughToRain(pos) && (pos.getY() >= view.getMinBuildHeight() && pos.getY() < view.getMaxBuildHeight() && view.getBrightness(LightLayer.BLOCK, pos) < 10)) {
			BlockState state = view.getBlockState(pos);
			if(view instanceof WorldGenLevel world) {

				if (Winterly.config.generateFrozenGrass && (state.is(Blocks.SHORT_GRASS) || state.is(Blocks.FERN) || state.is(Blocks.LARGE_FERN) || state.is(Blocks.TALL_GRASS))) {
					world.setBlock(pos, CommonWinterlyBlocks.FROZEN_GRASS.defaultBlockState(), 3);
					BlockState floor = world.getBlockState(pos.below());
					if (floor.hasProperty(SnowyDirtBlock.SNOWY)) {
						world.setBlock(pos.below(), floor.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
					}
					if(state.is(Blocks.LARGE_FERN) || state.is(Blocks.TALL_GRASS)) {
						world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 2);
					}
					return false;
				}
				else if(Winterly.config.generateFrozenFlowers && (state.getBlock() instanceof FlowerBlock || state.getBlock() instanceof TallFlowerBlock)) {
					world.setBlock(pos, CommonWinterlyBlocks.FROZEN_FLOWER.defaultBlockState().setValue(CommonFrozenFlowerBlock.LAYERS, 1), 3);
					BlockState floor = world.getBlockState(pos.below());
					if (floor.hasProperty(SnowyDirtBlock.SNOWY)) {
						world.setBlock(pos.below(), floor.setValue(SnowyDirtBlock.SNOWY, Boolean.TRUE), 2);
					}
					if(state.getBlock() instanceof TallFlowerBlock) {
						world.setBlock(pos.above(), Blocks.AIR.defaultBlockState(), 2);
					}
					return false;
				}
			}
		}

		return biome.shouldSnow(view, pos);
	}
}
