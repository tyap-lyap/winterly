package winterly.client.render;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.entity.LivingEntity;

public abstract class MobDecorationRenderer {
    public final String texture;

    public MobDecorationRenderer(String texture) {
        this.texture = texture;
    }

    abstract public void render(HumanoidModel<?> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch);
}
