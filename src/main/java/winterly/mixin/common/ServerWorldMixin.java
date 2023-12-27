package winterly.mixin.common;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import winterly.Winterly;
import winterly.block.FrozenFlowerBlock;
import winterly.data.CachedFlowers;
import winterly.registry.WinterlyBlocks;

@Mixin(ServerWorld.class)
public abstract class ServerWorldMixin {

	@Redirect(method = "tickIceAndSnow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/biome/Biome;canSetSnow(Lnet/minecraft/world/WorldView;Lnet/minecraft/util/math/BlockPos;)Z"))
	boolean canSetSnow(Biome biome, WorldView view, BlockPos pos) {

		if(!biome.doesNotSnow(pos) && (pos.getY() >= view.getBottomY() && pos.getY() < view.getTopY() && view.getLightLevel(LightType.BLOCK, pos) < 10)) {
			BlockState state = view.getBlockState(pos);
			if(view instanceof World world) {
				if (state.isOf(Blocks.SHORT_GRASS) && Winterly.config.generateFrozenGrass) {
					world.setBlockState(pos, WinterlyBlocks.FROZEN_GRASS.getDefaultState());
					return false;
				}
				else if(state.getBlock() instanceof FlowerBlock && Winterly.config.generateFrozenFlowers) {
					world.setBlockState(pos, WinterlyBlocks.FROZEN_FLOWER.getDefaultState().with(FrozenFlowerBlock.LAYERS, 1));
					CachedFlowers.cacheFlower(((World) view).getRegistryKey(), pos, state.getBlock());
					return false;
				}
			}
		}
		return biome.canSetSnow(view, pos);
	}
}
