package winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import winterly.Winterly;
import winterly.block.CommonFrozenFlowerBlock;
import winterly.data.CachedFlowers;
import winterly.registry.CommonWinterlyBlocks;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {

	@Redirect(method = "tickPrecipitation", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	boolean canSetSnow(Biome biome, LevelReader view, BlockPos pos) {

		if(!biome.warmEnoughToRain(pos) && (pos.getY() >= view.getMinBuildHeight() && pos.getY() < view.getMaxBuildHeight() && view.getBrightness(LightLayer.BLOCK, pos) < 10)) {
			BlockState state = view.getBlockState(pos);
			if(view instanceof Level world) {
				if (state.is(Blocks.SHORT_GRASS) && Winterly.config.generateFrozenGrass) {
					world.setBlockAndUpdate(pos, CommonWinterlyBlocks.FROZEN_GRASS.defaultBlockState());
					return false;
				}
				else if(state.getBlock() instanceof FlowerBlock && Winterly.config.generateFrozenFlowers) {
					world.setBlockAndUpdate(pos, CommonWinterlyBlocks.FROZEN_FLOWER.defaultBlockState().setValue(CommonFrozenFlowerBlock.LAYERS, 1));
					CachedFlowers.cacheFlower(((Level) view), pos, state.getBlock());
					return false;
				}
			}
		}
		return biome.shouldSnow(view, pos);
	}
}
