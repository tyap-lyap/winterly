package ru.tlmclub.winterly.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import ru.tlmclub.winterly.client.model.WinterlyModels;

public class SantaHatRenderer extends MobDecorationRenderer {

    public SantaHatRenderer(String texture) {
        super(texture);
    }

    @Override
    public void render(BipedEntityModel<?> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        WinterlyModels.SANTA_HAT_MODEL.hat.copyTransform(contextModel.head);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(new Identifier("winterly", "textures/entity/" + texture + ".png")));
        WinterlyModels.SANTA_HAT_MODEL.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
