package winterly.client.render;

import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;

public class ScarfRenderer extends MobDecorationRenderer {

    public ScarfRenderer(String texture) {
        super(texture);
    }

    @Override
    public void render(BipedEntityModel<?> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        WinterlyModels.SCARF_MODEL.scarf.copyTransform(contextModel.body);
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(Winterly.id("textures/entity/" + texture + ".png")));
        WinterlyModels.SCARF_MODEL.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
    }
}
