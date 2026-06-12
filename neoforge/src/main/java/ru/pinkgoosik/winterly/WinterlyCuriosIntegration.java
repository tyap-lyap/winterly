package ru.pinkgoosik.winterly;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.winterly.client.model.WinterlyModels;
import ru.pinkgoosik.winterly.item.CommonSantaHatItem;
import ru.pinkgoosik.winterly.item.CommonScarfItem;
import top.theillusivec4.curios.api.CuriosApi;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import top.theillusivec4.curios.api.type.capability.ICurioItem;
import top.theillusivec4.curios.api.type.inventory.ICurioStacksHandler;
import top.theillusivec4.curios.api.type.inventory.IDynamicStackHandler;

import java.util.Map;

public class WinterlyCuriosIntegration {

	public static void registerCurio(Item item) {
		CuriosApi.registerCurio(item, new ICurioItem() {});
	}

	public static boolean hasVisibleHat(Player player) {
		var curiosItemHandler = CuriosApi.getCuriosInventory(player);
		if (curiosItemHandler.isPresent()) {
			for (Map.Entry<String, ICurioStacksHandler> entry : curiosItemHandler.get().getCurios().entrySet()) {
				IDynamicStackHandler stackHandler = entry.getValue().getStacks();
				IDynamicStackHandler cosmeticStacksHandler = entry.getValue().getCosmeticStacks();

				for (int i = 0; i < stackHandler.getSlots(); ++i) {
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
		ICurioRenderer.register(scarf, () -> new ICurioRenderer() {
			@Override
			public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
					ItemStack stack, SlotContext slotContext, PoseStack poseStack,
					MultiBufferSource renderTypeBuffer, int packedLight,
					S renderState, RenderLayerParent<S, M> renderLayerParent,
					EntityRendererProvider.Context context, float yRotation, float xRotation) {
				if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoid) {
					WinterlyModels.SCARF_MODEL.scarf.loadPose(humanoid.body.storePose());
					VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderTypes.entityCutout(
							Winterly.id("textures/entity/" + scarf.color + "_scarf.png")));
					WinterlyModels.SCARF_MODEL.root().render(poseStack, vertexConsumer, packedLight,
							OverlayTexture.NO_OVERLAY, -1);
				}
			}
		});
	}

	public static void registerSantaHatRenderer(CommonSantaHatItem hat) {
		ICurioRenderer.register(hat, () -> new ICurioRenderer() {
			@Override
			public <S extends LivingEntityRenderState, M extends EntityModel<? super S>> void render(
					ItemStack stack, SlotContext slotContext, PoseStack poseStack,
					MultiBufferSource renderTypeBuffer, int packedLight,
					S renderState, RenderLayerParent<S, M> renderLayerParent,
					EntityRendererProvider.Context context, float yRotation, float xRotation) {
				if (renderLayerParent.getModel() instanceof HumanoidModel<?> humanoid) {
					WinterlyModels.SANTA_HAT_MODEL.hat.loadPose(humanoid.head.storePose());
					VertexConsumer vertexConsumer = renderTypeBuffer.getBuffer(RenderTypes.entityCutout(
							Winterly.id("textures/entity/" + hat.color + "_santa_hat.png")));
					WinterlyModels.SANTA_HAT_MODEL.root().render(poseStack, vertexConsumer, packedLight,
							OverlayTexture.NO_OVERLAY, -1);
				}
			}
		});
	}
}
