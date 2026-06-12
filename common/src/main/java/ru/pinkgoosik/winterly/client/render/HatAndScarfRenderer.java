package ru.pinkgoosik.winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.client.WinterlyModelLayers;

public class HatAndScarfRenderer extends MobDecorationRenderer {
	public final String scarf;
	public final String hat;

	public HatAndScarfRenderer(String scarf, String hat) {
		this.scarf = scarf;
		this.hat = hat;
	}

	@Override
	public void submit(HumanoidModel<?> contextModel, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LivingEntityRenderState state, float yRot, float xRot) {
		var scarfModel = freshScarfModel(WinterlyModelLayers.SCARF_LAYER);
		var hatModel = freshHatModel(WinterlyModelLayers.SANTA_HAT_LAYER);
		poseStack.pushPose();
		contextModel.root().translateAndRotate(poseStack);
		scarfModel.scarf.loadPose(contextModel.body.storePose());
		submitPart(submitNodeCollector, poseStack, scarfModel.scarf, Winterly.id("textures/entity/" + scarf + ".png"), light);
		hatModel.hat.loadPose(contextModel.head.storePose());
		submitPart(submitNodeCollector, poseStack, hatModel.hat, Winterly.id("textures/entity/" + hat + ".png"), light);
		poseStack.popPose();
	}
}
