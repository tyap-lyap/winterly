package ru.pinkgoosik.winterly.mixin.client.fabric;

import net.minecraft.client.renderer.entity.AbstractSkeletonRenderer;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.winterly.client.render.FabricRenderStateKeys;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;

@Mixin(AbstractSkeletonRenderer.class)
public abstract class AbstractSkeletonRendererMixin {
	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/skeleton/AbstractSkeleton;Lnet/minecraft/client/renderer/entity/state/SkeletonRenderState;F)V", at = @At("RETURN"))
	private void winterly$extractDecoration(AbstractSkeleton entity, SkeletonRenderState state, float partialTick, CallbackInfo ci) {
		state.setData(
			FabricRenderStateKeys.MOB_DECORATION,
			WinterlyPlatformHolder.get().getDecorationData(entity)
		);
	}
}
