package winterly.fabric.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;
import winterly.item.CommonScarfItem;

public class ScarfItem extends CommonScarfItem implements Trinket, TrinketRenderer {

    public ScarfItem(Item.Properties settings, String color) {
        super(settings, color);
        TrinketsApi.registerTrinket(this, this);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(contextModel instanceof HumanoidModel<? extends LivingEntity> biped){
            WinterlyModels.SCARF_MODEL.scarf.copyFrom(biped.body);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + color + "_scarf.png")));
            WinterlyModels.SCARF_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
