package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.pinkgoosik.winterly.util.FrozenPrecipitationUtil;

@Mixin(ServerLevel.class)
public abstract class ServerWorldMixin {

	@Redirect(method = "tickChunk", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	boolean onShouldSnow(Biome biome, LevelReader view, BlockPos pos) {
		ServerLevel world = (ServerLevel) view;
		if (FrozenPrecipitationUtil.tryFreezePlant("precipitation", biome, view, world, pos, world.getSeed(), world.getGameTime())) {
			return false;
		}
		return biome.shouldSnow(view, pos);
	}
}
