package ru.pinkgoosik.winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import ru.pinkgoosik.winterly.data.DecorationData;

public abstract class DecorationFeatureRenderer<S extends LivingEntityRenderState, M extends HumanoidModel<? super S>> extends RenderLayer<S, M> {

	public DecorationFeatureRenderer(RenderLayerParent<S, M> context) {
		super(context);
	}

	protected abstract DecorationData getDecorationData(S state);

	@Override
	public void submit(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, S state, float yRot, float xRot) {
		DecorationData decoration = getDecorationData(state);
		if (decoration.decorated()) {
			MobDecorationRenderer renderer = MobDecorations.getRenderer(decoration.index());
			renderer.submit(this.getParentModel(), poseStack, submitNodeCollector, light, state, yRot, xRot);
		}
	}
}
