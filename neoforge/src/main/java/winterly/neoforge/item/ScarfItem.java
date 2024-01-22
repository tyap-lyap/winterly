package winterly.neoforge.item;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.ChatFormatting;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;
import winterly.item.CommonScarfItem;

import java.util.List;

public class ScarfItem extends CommonScarfItem implements ICurioRenderer {

    public ScarfItem(Item.Properties settings, String color) {
        super(settings, color);

        CuriosApi.registerCurio(this, new ICurioItem() {

            @Override
            public boolean canEquip(SlotContext slotContext, ItemStack stack) {
                return true;
            }
        });
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.nullToEmpty(" "));
        tooltip.add(Component.translatable("tag.winterly.cosmetic").withStyle(ChatFormatting.GRAY));
    }

    @Override
    public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrices, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
        if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> biped){
            WinterlyModels.SCARF_MODEL.scarf.copyFrom(biped.body);
            VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + color + "_scarf.png")));
            WinterlyModels.SCARF_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
