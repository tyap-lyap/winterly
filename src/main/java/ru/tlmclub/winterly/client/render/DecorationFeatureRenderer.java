package ru.tlmclub.winterly.client.render;

import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import ru.tlmclub.winterly.client.WinterlyModels;
import ru.tlmclub.winterly.extension.DecoratedMob;

public class DecorationFeatureRenderer<T extends LivingEntity, M extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    public DecorationFeatureRenderer(FeatureRendererContext<T, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity instanceof DecoratedMob decorated){
            if(decorated.winterly$decorated()){
                ModelIdentifier model = WinterlyModels.MODELS.get(decorated.winterly$getIndex());
                if (model.toString().contains("hat")) CosmeticRenderer.renderHat(this.getContextModel(), model, matrices, vertexConsumers, light, entity, headYaw, headPitch);
                else if(model.toString().contains("scarf")) CosmeticRenderer.renderScarf(this.getContextModel(), model, matrices, vertexConsumers, light, entity);
            }
        }
    }
}
