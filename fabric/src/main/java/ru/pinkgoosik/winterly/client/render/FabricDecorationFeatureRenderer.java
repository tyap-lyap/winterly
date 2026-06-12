package ru.pinkgoosik.winterly.client.render;

import net.fabricmc.fabric.api.client.rendering.v1.FabricRenderState;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import ru.pinkgoosik.winterly.data.DecorationData;

public class FabricDecorationFeatureRenderer<S extends LivingEntityRenderState, M extends HumanoidModel<? super S>> extends DecorationFeatureRenderer<S, M> {

	public FabricDecorationFeatureRenderer(RenderLayerParent<S, M> context) {
		super(context);
	}

	@Override
	protected DecorationData getDecorationData(S state) {
		if (state instanceof FabricRenderState fabricRenderState) {
			return fabricRenderState.getDataOrDefault(FabricRenderStateKeys.MOB_DECORATION, DecorationData.DEFAULT);
		}
		return DecorationData.DEFAULT;
	}
}
