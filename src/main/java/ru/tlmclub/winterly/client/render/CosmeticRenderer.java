package ru.tlmclub.winterly.client.render;

import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Items;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public class CosmeticRenderer {

    public static void renderScarf(BipedEntityModel<?> contextModel, ModelIdentifier model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity) {
        if(entity.isInvisible()) return;
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();

        translateToBody(matrices, contextModel, entity);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        matrices.translate(0.0D, 0.1D, 0.0D);

        BakedModel bakedModel = itemRenderer.getModels().getModelManager().getModel(model);

        itemRenderer.renderItem(Items.DIAMOND.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, bakedModel);
        matrices.pop();
    }

    public static void renderHat(BipedEntityModel<?> contextModel, ModelIdentifier model, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float headYaw, float headPitch) {
        if(entity.isInvisible()) return;
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();

        translateToHead(matrices, contextModel, entity, headYaw, headPitch);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        matrices.translate(0D, 1D, 0D);

        BakedModel bakedModel = itemRenderer.getModels().getModelManager().getModel(model);

        itemRenderer.renderItem(Items.DIAMOND.getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV, bakedModel);
        matrices.pop();
    }

    static void translateToHead(MatrixStack matrices, BipedEntityModel<?> model, LivingEntity entity, float headYaw, float headPitch) {
        if (entity.isInSwimmingPose() || entity.isFallFlying()) {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(model.head.roll));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-45.0F));
        } else {
            if (entity.isInSneakingPose() && !model.riding) {
                matrices.translate(0.0F, 0.25F, 0.0F);
            }
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
        }
    }

    static void translateToBody(MatrixStack matrices, BipedEntityModel<?> model, LivingEntity entity) {
        if (entity.isInSneakingPose() && !model.riding && !entity.isSwimming()) {
            matrices.translate(0.0F, 0.2F, 0.0F);
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(model.body.pitch * TrinketRenderer.MAGIC_ROTATION));
        }
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(model.body.yaw * TrinketRenderer.MAGIC_ROTATION));
    }
}
