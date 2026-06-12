package ru.pinkgoosik.winterly.mixin.common;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.feature.SnowAndFreezeFeature;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import ru.pinkgoosik.winterly.util.FrozenPrecipitationUtil;

@Mixin(SnowAndFreezeFeature.class)
public abstract class FreezeTopLayerMixin {

	@Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/biome/Biome;shouldSnow(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z"))
	boolean canSetIce(Biome biome, LevelReader view, BlockPos pos) {
		if (view instanceof WorldGenLevel world && FrozenPrecipitationUtil.tryFreezePlant("worldgen", biome, view, world, pos, world.getSeed())) {
			return false;
		}
		return biome.shouldSnow(view, pos);
	}
}
