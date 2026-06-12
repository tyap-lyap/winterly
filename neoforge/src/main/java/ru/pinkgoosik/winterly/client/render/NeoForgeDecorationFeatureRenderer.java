package ru.pinkgoosik.winterly.client.render;

import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.neoforged.neoforge.client.extensions.IRenderStateExtension;
import ru.pinkgoosik.winterly.data.DecorationData;

public class NeoForgeDecorationFeatureRenderer<S extends LivingEntityRenderState, M extends HumanoidModel<? super S>> extends DecorationFeatureRenderer<S, M> {

	public NeoForgeDecorationFeatureRenderer(RenderLayerParent<S, M> context) {
		super(context);
	}

	@Override
	protected DecorationData getDecorationData(S state) {
		if (state instanceof IRenderStateExtension renderStateExtension) {
			return renderStateExtension.getRenderDataOrDefault(NeoForgeRenderStateKeys.MOB_DECORATION, DecorationData.DEFAULT);
		}
		return DecorationData.DEFAULT;
	}
}
