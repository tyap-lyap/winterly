package ru.tlmclub.winterly.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.Vec3f;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.bclib.items.ModelProviderItem;

import java.util.List;

public class SantaHatItem extends ModelProviderItem implements Trinket, TrinketRenderer {

    public SantaHatItem(Settings settings) {
        super(settings);
        TrinketsApi.registerTrinket(this, this);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("misc.winterly.cosmetic").formatted(Formatting.GRAY));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        ItemRenderer itemRenderer = MinecraftClient.getInstance().getItemRenderer();
        matrices.push();

        translateToHead(matrices, entity, headYaw, headPitch);
        matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(180.0F));
        matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(180.0F));
        matrices.translate(0D, 1D, 0D);

        itemRenderer.renderItem(getDefaultStack(), ModelTransformation.Mode.FIXED, false, matrices, vertexConsumers, light, OverlayTexture.DEFAULT_UV,
                itemRenderer.getModels().getModelManager().getModel(new ModelIdentifier("winterly:" + Registry.ITEM.getId(this).getPath() + "_on_head#inventory")));
        matrices.pop();
    }

    static void translateToHead(MatrixStack matrices, LivingEntity entity, float headYaw, float headPitch) {
        if (entity.isInSwimmingPose() || entity.isFallFlying()) {
            matrices.multiply(Vec3f.POSITIVE_Z.getDegreesQuaternion(headPitch));
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(-45.0F));
        } else {
            if (entity.isInSneakingPose() && !entity.hasVehicle()) {
                matrices.translate(0.0F, 0.25F, 0.0F);
            }
            matrices.multiply(Vec3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
            matrices.multiply(Vec3f.POSITIVE_X.getDegreesQuaternion(headPitch));
        }
    }
}
