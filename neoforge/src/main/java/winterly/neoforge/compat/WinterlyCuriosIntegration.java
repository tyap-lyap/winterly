package winterly.neoforge.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;
import winterly.item.CommonSantaHatItem;
import winterly.item.CommonScarfItem;

import java.util.Map;

public class WinterlyCuriosIntegration {

	public static void registerCurio(Item item) {
		CuriosApi.registerCurio(item, new ICurioItem() {});
	}

	public static boolean hasVisibleHat(Player player) {
		var curiosItemHandler = CuriosApi.getCuriosInventory(player);
		if(curiosItemHandler.isPresent()) {
			for (Map.Entry<String, ICurioStacksHandler> entry : curiosItemHandler.get().getCurios().entrySet()) {
				IDynamicStackHandler stackHandler = entry.getValue().getStacks();
				IDynamicStackHandler cosmeticStacksHandler = entry.getValue().getCosmeticStacks();

				for(int i = 0; i < stackHandler.getSlots(); ++i) {
					ItemStack stack = cosmeticStacksHandler.getStackInSlot(i);

					NonNullList<Boolean> renderStates = entry.getValue().getRenders();
					boolean renderable = renderStates.size() > i && renderStates.get(i);

					if (stack.isEmpty() && renderable) {
						stack = stackHandler.getStackInSlot(i);
					}

					if (!stack.isEmpty() && stack.getItem() instanceof CommonSantaHatItem) {
						return true;
					}
				}
			}
		}

		return false;
	}

	public static void registerScarfRenderer(CommonScarfItem scarf) {
		CuriosRendererRegistry.register(scarf, () -> {
			return new ICurioRenderer() {
				@Override
				public <T extends LivingEntity, M extends EntityModel<T>> void render(ItemStack stack, SlotContext slotContext, PoseStack matrices, RenderLayerParent<T, M> renderLayerParent, MultiBufferSource renderTypeBuffer, int light, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
					if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> humanoid) {
						WinterlyModels.SCARF_MODEL.scarf.copyFrom(humanoid.body);
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
					if(renderLayerParent.getModel() instanceof HumanoidModel<? extends LivingEntity> humanoid) {
						WinterlyModels.SANTA_HAT_MODEL.hat.copyFrom(humanoid.head);
						VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + hat.color + "_santa_hat.png")));
						WinterlyModels.SANTA_HAT_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
					}
				}
			};
		});
	}

}
