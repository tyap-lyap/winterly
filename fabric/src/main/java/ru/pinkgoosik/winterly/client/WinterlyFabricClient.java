package ru.pinkgoosik.winterly.client;

import cc.sighs.oelib.platform.Platform;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityRenderLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.ModelLayerRegistry;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.EntityType;
import ru.pinkgoosik.winterly.WinterlyTrinketsIntegration;
import ru.pinkgoosik.winterly.client.model.SantaHatModel;
import ru.pinkgoosik.winterly.client.model.ScarfModel;
import ru.pinkgoosik.winterly.client.render.FabricDecorationFeatureRenderer;
import ru.pinkgoosik.winterly.client.render.MobDecorations;
import ru.pinkgoosik.winterly.item.CommonSantaHatItem;
import ru.pinkgoosik.winterly.item.CommonScarfItem;
import ru.pinkgoosik.winterly.registry.WinterlyItems;

@Environment(EnvType.CLIENT)
public class WinterlyFabricClient implements ClientModInitializer {

	@Override
	public void onInitializeClient() {
		ModelLayerRegistry.registerModelLayer(WinterlyModelLayers.SANTA_HAT_LAYER, SantaHatModel::getTexturedModelData);
		ModelLayerRegistry.registerModelLayer(WinterlyModelLayers.SCARF_LAYER, ScarfModel::getTexturedModelData);

		MobDecorations.init();
		if (Platform.isModLoaded("trinkets")) {
			WinterlyItems.ITEMS.entries().forEach(entry -> {
				if (entry.get() instanceof CommonScarfItem scarf) WinterlyTrinketsIntegration.registerScarfRenderer(scarf);
				if (entry.get() instanceof CommonSantaHatItem hat) WinterlyTrinketsIntegration.registerSantaHatRenderer(hat);
			});
		}
		LivingEntityRenderLayerRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            switch (entityRenderer) {
                case ZombieRenderer zr when entityType == EntityType.ZOMBIE ->
                        registrationHelper.register(new FabricDecorationFeatureRenderer<>(zr));
                case DrownedRenderer dr when entityType == EntityType.DROWNED ->
                        registrationHelper.register(new FabricDecorationFeatureRenderer<>(dr));
                case SkeletonRenderer sr when entityType == EntityType.SKELETON ->
                        registrationHelper.register(new FabricDecorationFeatureRenderer<>(sr));
                default -> {
                }
            }
		});
	}
}
