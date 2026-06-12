package ru.pinkgoosik.winterly.mixin.client.neoforge;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.HumanoidRenderState;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.client.extensions.IRenderStateExtension;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.pinkgoosik.winterly.client.render.NeoForgeRenderStateKeys;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorRendererMixin<S extends HumanoidRenderState, M extends HumanoidModel<S>, A extends HumanoidModel<S>> extends RenderLayer<S, M> {

	protected ArmorRendererMixin(RenderLayerParent<S, M> context) {
		super(context);
	}

	@Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
	private void winterly$hideHelmetWhenHatVisible(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, ItemStack itemStack, EquipmentSlot slot, int lightCoords, S state, CallbackInfo ci) {
		if (slot == EquipmentSlot.HEAD
			&& state instanceof IRenderStateExtension renderStateExtension
			&& renderStateExtension.getRenderDataOrDefault(NeoForgeRenderStateKeys.VISIBLE_HAT, false)) {
			ci.cancel();
		}
	}
}
