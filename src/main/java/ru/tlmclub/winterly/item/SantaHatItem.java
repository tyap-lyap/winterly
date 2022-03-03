package ru.tlmclub.winterly.item;

import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.client.model.WinterlyModels;

import java.util.List;

public class SantaHatItem extends Item implements Trinket, TrinketRenderer {
    private final String color;

    public SantaHatItem(Settings settings, String color) {
        super(settings);
        this.color = color;
        TrinketsApi.registerTrinket(this, this);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(new TranslatableText("tag.winterly.cosmetic").formatted(Formatting.GRAY));
        tooltip.add(new LiteralText(" "));
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(contextModel instanceof BipedEntityModel<? extends LivingEntity> biped) {
            WinterlyModels.SANTA_HAT_MODEL.hat.copyTransform(biped.head);
            VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntityCutout(WinterlyMod.locate("textures/entity/" + color + "_santa_hat.png")));
            WinterlyModels.SANTA_HAT_MODEL.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
