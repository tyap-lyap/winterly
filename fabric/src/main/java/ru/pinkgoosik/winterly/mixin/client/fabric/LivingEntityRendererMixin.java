package ru.pinkgoosik.winterly.mixin.client.fabric;

import cc.sighs.oelib.platform.Platform;
import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.winterly.WinterlyTrinketsIntegration;
import ru.pinkgoosik.winterly.client.render.FabricRenderStateKeys;

@Mixin(LivingEntityRenderer.class)
public abstract class LivingEntityRendererMixin {

    @Inject(method = "extractRenderState(Lnet/minecraft/world/entity/LivingEntity;Lnet/minecraft/client/renderer/entity/state/LivingEntityRenderState;F)V", at = @At("RETURN"))
    private void winterly$extractVisibleHat(LivingEntity entity, LivingEntityRenderState state, float partialTicks, CallbackInfo ci) {
        if (state instanceof FabricRenderState fabricRenderState) {
            if (Platform.isModLoaded("trinkets")) {
                boolean visibleHat = entity instanceof Player player && WinterlyTrinketsIntegration.hasVisibleHat(player);
                fabricRenderState.setData(FabricRenderStateKeys.VISIBLE_HAT, visibleHat);
            }
        }
    }
}
