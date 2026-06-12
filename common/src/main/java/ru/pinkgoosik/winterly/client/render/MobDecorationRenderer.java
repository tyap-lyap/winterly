package ru.pinkgoosik.winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import ru.pinkgoosik.winterly.client.model.SantaHatModel;
import ru.pinkgoosik.winterly.client.model.ScarfModel;

public abstract class MobDecorationRenderer {
	abstract public void submit(HumanoidModel<?> contextModel, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int light, LivingEntityRenderState state, float yRot, float xRot);

	protected final ScarfModel freshScarfModel(ModelLayerLocation layer) {
		return new ScarfModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
	}

	protected final SantaHatModel freshHatModel(ModelLayerLocation layer) {
		return new SantaHatModel(Minecraft.getInstance().getEntityModels().bakeLayer(layer));
	}

	protected final void submitPart(SubmitNodeCollector submitNodeCollector, PoseStack poseStack, ModelPart part, Identifier texture, int light) {
		submitNodeCollector.order(0).submitModelPart(
			part,
			poseStack,
			RenderTypes.entityCutout(texture),
			light,
			OverlayTexture.NO_OVERLAY,
			null
		);
	}
}
