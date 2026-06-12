package ru.pinkgoosik.winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.client.model.WinterlyModels;

public class HatAndScarfRenderer extends MobDecorationRenderer {
	public final String scarf;
	public final String hat;

	public HatAndScarfRenderer(String scarf, String hat) {
		this.scarf = scarf;
		this.hat = hat;
	}

	@Override
	public void render(HumanoidModel<?> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
		WinterlyModels.SCARF_MODEL.scarf.copyFrom(contextModel.body);
		VertexConsumer vertexConsumerScarf = vertexConsumers.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + scarf + ".png")));
		WinterlyModels.SCARF_MODEL.renderToBuffer(matrices, vertexConsumerScarf, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);

		WinterlyModels.SANTA_HAT_MODEL.hat.copyFrom(contextModel.head);
		VertexConsumer vertexConsumerHat = vertexConsumers.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + hat + ".png")));
		WinterlyModels.SANTA_HAT_MODEL.renderToBuffer(matrices, vertexConsumerHat, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
	}
}
