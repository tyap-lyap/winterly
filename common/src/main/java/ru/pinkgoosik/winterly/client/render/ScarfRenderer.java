package ru.pinkgoosik.winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.client.WinterlyModelLayers;

public class ScarfRenderer extends MobDecorationRenderer {
	public final String texture;

	public ScarfRenderer(String texture) {
		this.texture = texture;
	}

	@Override
	public void submit(HumanoidModel<?> contextModel, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LivingEntityRenderState state, float yRot, float xRot) {
		var scarfModel = freshScarfModel(WinterlyModelLayers.SCARF_LAYER);
		scarfModel.scarf.loadPose(contextModel.body.storePose());
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		submitPart(submitNodeCollector, poseStack, scarfModel.scarf, Winterly.id("textures/entity/" + texture + ".png"), light);
		poseStack.popPose();
	}
}
