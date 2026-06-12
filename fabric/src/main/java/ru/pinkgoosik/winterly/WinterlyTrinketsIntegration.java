package ru.pinkgoosik.winterly;

import eu.pb4.trinkets.api.TrinketsApi;
import eu.pb4.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import ru.pinkgoosik.winterly.client.model.WinterlyModels;
import ru.pinkgoosik.winterly.item.CommonSantaHatItem;
import ru.pinkgoosik.winterly.item.CommonScarfItem;

public class WinterlyTrinketsIntegration {

	public static void registerCurio(Item item) {
	}

	public static boolean hasVisibleHat(Player player) {
		if (player == null) {
			return false;
		}

		var attachment = TrinketsApi.getAttachment(player);
		if (attachment == null) return false;

		for (var inv : attachment.getInventories().values()) {
			for (int i = 0; i < inv.getContainerSize(); i++) {
				ItemStack stack = inv.getItem(i);
				if (!stack.isEmpty() && stack.getItem() instanceof CommonSantaHatItem) {
					return true;
				}
			}
		}
		return false;
	}

	public static void registerScarfRenderer(CommonScarfItem scarf) {
		TrinketRendererRegistry.registerRenderer(scarf, (_, _, contextModel, poseStack, submit, light, _, _, _) -> {
            if (contextModel instanceof HumanoidModel<?> humanoid) {
                WinterlyModels.SCARF_MODEL.scarf.loadPose(humanoid.body.storePose());
                submit.order(0).submitModelPart(WinterlyModels.SCARF_MODEL.scarf, poseStack,
                    RenderTypes.entityCutout(Winterly.id("textures/entity/" + scarf.color + "_scarf.png")),
                    light, OverlayTexture.NO_OVERLAY, null);
            }
        });
	}

	public static void registerSantaHatRenderer(CommonSantaHatItem hat) {
		TrinketRendererRegistry.registerRenderer(hat, (_, _, contextModel, poseStack, submit, light, _, _, _) -> {
            if (contextModel instanceof HumanoidModel<?> humanoid) {
                WinterlyModels.SANTA_HAT_MODEL.hat.loadPose(humanoid.head.storePose());
                submit.order(0).submitModelPart(WinterlyModels.SANTA_HAT_MODEL.hat, poseStack,
                    RenderTypes.entityCutout(Winterly.id("textures/entity/" + hat.color + "_santa_hat.png")),
                    light, OverlayTexture.NO_OVERLAY, null);
            }
        });
	}
}
