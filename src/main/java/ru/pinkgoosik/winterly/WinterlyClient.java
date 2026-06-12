package ru.pinkgoosik.winterly;

import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.ConfigScreenHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import ru.pinkgoosik.winterly.client.WinterlyModelLayers;
import ru.pinkgoosik.winterly.client.model.SantaHatModel;
import ru.pinkgoosik.winterly.client.model.ScarfModel;
import ru.pinkgoosik.winterly.client.render.DecorationFeatureRenderer;
import ru.pinkgoosik.winterly.client.render.MobDecorations;
import ru.pinkgoosik.winterly.compat.WinterlyCuriosIntegration;
import ru.pinkgoosik.winterly.item.CommonSantaHatItem;
import ru.pinkgoosik.winterly.item.CommonScarfItem;
import ru.pinkgoosik.winterly.registry.WinterlyItems;

@OnlyIn(Dist.CLIENT)
public class WinterlyClient {

	public static void init() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();
		bus.addListener(WinterlyClient::clientSetup);
		bus.addListener(WinterlyClient::registerModelLayers);
		bus.addListener(WinterlyClient::registerRenderLayers);
	}

	private static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(WinterlyModelLayers.SANTA_HAT_LAYER, SantaHatModel::getTexturedModelData);
		event.registerLayerDefinition(WinterlyModelLayers.SCARF_LAYER, ScarfModel::getTexturedModelData);
	}

	private static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
		Object zombieRenderer = event.getRenderer(EntityType.ZOMBIE);
		if (zombieRenderer instanceof ZombieRenderer zr) {
			zr.addLayer(new DecorationFeatureRenderer<>(zr));
		}
		Object drownedRenderer = event.getRenderer(EntityType.DROWNED);
		if (drownedRenderer instanceof DrownedRenderer dr) {
			dr.addLayer(new DecorationFeatureRenderer<>(dr));
		}
		Object skeletonRenderer = event.getRenderer(EntityType.SKELETON);
		if (skeletonRenderer instanceof SkeletonRenderer sr) {
			sr.addLayer(new DecorationFeatureRenderer<>(sr));
		}
	}

	private static void clientSetup(FMLClientSetupEvent event) {
		if (ModList.get().isLoaded("curios")) {
			WinterlyItems.ITEMS.getEntries().forEach(entry -> {
				if (entry.get() instanceof CommonScarfItem scarf) WinterlyCuriosIntegration.registerScarfRenderer(scarf);
				if (entry.get() instanceof CommonSantaHatItem hat) WinterlyCuriosIntegration.registerSantaHatRenderer(hat);
			});
		}

		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((mc, screen) -> new ConfigScreen(screen, Winterly.MOD_ID)));

		MobDecorations.init();
	}
}
