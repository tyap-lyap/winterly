package winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.world.entity.LivingEntity;
import winterly.extension.DecoratedMob;

public class DecorationFeatureRenderer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public DecorationFeatureRenderer(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Override
    public void render(PoseStack matrices, MultiBufferSource vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(entity instanceof DecoratedMob decorated) {
            if(decorated.winterly$decorated()) {
                MobDecorationRenderer renderer = MobDecorationRenderers.getRenderer(decorated.winterly$getIndex());
                renderer.render(this.getParentModel(), matrices, vertexConsumers, light, entity, limbAngle, limbDistance, tickDelta, animationProgress, headYaw, headPitch);
            }
        }
    }
}
