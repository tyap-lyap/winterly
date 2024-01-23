package winterly.fabric.compat;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import winterly.Winterly;
import winterly.client.model.WinterlyModels;
import winterly.fabric.item.SantaHatItem;
import winterly.item.CommonSantaHatItem;
import winterly.item.CommonScarfItem;

import java.util.Optional;

public class WinterlyTrinketsIntegration {


	public static void registerTrinket(Item item) {
		TrinketsApi.registerTrinket(item, new Trinket() {});
	}

	public static boolean hasHatOn(Player player) {
		Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
		if(component.isPresent()) {
			for(Tuple<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
				if(pair.getB().getItem() instanceof SantaHatItem) return true;
			}
		}
		return false;
	}

	public static void registerScarfRenderer(CommonScarfItem scarf) {
		TrinketRendererRegistry.registerRenderer(scarf, new TrinketRenderer() {
			@Override
			public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				if(contextModel instanceof HumanoidModel<? extends LivingEntity> biped){
					WinterlyModels.SCARF_MODEL.scarf.copyFrom(biped.body);
					VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + scarf.color + "_scarf.png")));
					WinterlyModels.SCARF_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		});
	}

	public static void registerSantaHatRenderer(CommonSantaHatItem hat) {
		TrinketRendererRegistry.registerRenderer(hat, new TrinketRenderer() {
			@Override
			public void render(ItemStack stack, SlotReference slotReference, EntityModel<? extends LivingEntity> contextModel, PoseStack matrices, MultiBufferSource vertexConsumers, int light, LivingEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
				if(contextModel instanceof HumanoidModel<? extends LivingEntity> biped) {
					WinterlyModels.SANTA_HAT_MODEL.hat.copyFrom(biped.head);
					VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderType.entityCutout(Winterly.id("textures/entity/" + hat.color + "_santa_hat.png")));
					WinterlyModels.SANTA_HAT_MODEL.renderToBuffer(matrices, vertexConsumer, light, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
				}
			}
		});
	}
}
