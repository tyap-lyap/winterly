package ru.tlmclub.winterly.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

public abstract class MobDecorationRenderer {
    public final String texture;

    public MobDecorationRenderer(String texture) {
        this.texture = texture;
    }

    abstract public void render(BipedEntityModel<?> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch);
}
