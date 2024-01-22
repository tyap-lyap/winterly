package winterly.fabric.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import dev.emi.trinkets.api.SlotReference;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import winterly.fabric.item.SantaHatItem;
import java.util.Optional;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

@Mixin(HumanoidArmorLayer.class)
public abstract class ArmorRendererMixin <T extends LivingEntity, M extends HumanoidModel<T>, A extends HumanoidModel<T>> extends RenderLayer<T, M> {

    public ArmorRendererMixin(RenderLayerParent<T, M> context) {
        super(context);
    }

    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    void render(PoseStack matrices, MultiBufferSource vertexConsumers, T entity, EquipmentSlot armorSlot, int light, A model, CallbackInfo ci) {
        if(entity instanceof Player player) {
            if(armorSlot.equals(EquipmentSlot.HEAD)) {
                if(winterly$hasHatOn(player)) ci.cancel();
            }
        }
    }

    boolean winterly$hasHatOn(Player player) {
        Optional<TrinketComponent> component = TrinketsApi.getTrinketComponent(player);
        if(component.isPresent()) {
            for(Tuple<SlotReference, ItemStack> pair : component.get().getAllEquipped()) {
                if(pair.getB().getItem() instanceof SantaHatItem) return true;
            }
        }
        return false;
    }
}
