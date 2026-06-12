package ru.pinkgoosik.winterly.mixin.client.fabric;

import net.minecraft.client.renderer.entity.AbstractZombieRenderer;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.monster.zombie.Zombie;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.winterly.client.render.FabricRenderStateKeys;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;

@Mixin(AbstractZombieRenderer.class)
public abstract class AbstractZombieRendererMixin {

	@Inject(method = "extractRenderState(Lnet/minecraft/world/entity/monster/zombie/Zombie;Lnet/minecraft/client/renderer/entity/state/ZombieRenderState;F)V", at = @At("RETURN"))
	private void winterly$extractDecoration(Zombie entity, ZombieRenderState state, float partialTick, CallbackInfo ci) {
		state.setData(
			FabricRenderStateKeys.MOB_DECORATION,
			WinterlyPlatformHolder.get().getDecorationData(entity)
		);
	}
}
