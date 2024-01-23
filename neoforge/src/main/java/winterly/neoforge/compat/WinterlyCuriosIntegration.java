package winterly.neoforge.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;
import winterly.item.CommonSantaHatItem;
import winterly.item.CommonScarfItem;

public class WinterlyCuriosIntegration {

	public static void registerCurio(Item item) {
		CuriosApi.registerCurio(item, new ICurioItem() {});
	}

	public static void registerScarfRenderer(CommonScarfItem scarf) {
		CuriosRendererRegistry.register(scarf, () -> {
			return new ICurioRenderer() {
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrices, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> biped){
						WinterlyModels.SCARF_MODEL.scarf.copyFrom(biped.body);
						VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + scarf.color + "_scarf.png")));
						WinterlyModels.SCARF_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			};
		});
	}

	public static void registerSantaHatRenderer(CommonSantaHatItem hat) {
		CuriosRendererRegistry.register(hat, () -> {
			return new ICurioRenderer() {
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrices, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> biped) {
						WinterlyModels.SANTA_HAT_MODEL.hat.copyFrom(biped.head);
						VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + hat.color + "_santa_hat.png")));
						WinterlyModels.SANTA_HAT_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			};
		});
	}

}
