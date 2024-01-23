package winterly.neoforge.client;

import net.minecraft.client.renderer.entity.DrownedRenderer;
import net.minecraft.client.renderer.entity.SkeletonRenderer;
import net.minecraft.client.renderer.entity.ZombieRenderer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import winterly.client.WinterlyModelLayers;
import winterly.client.model.SantaHatModel;
import winterly.client.model.ScarfModel;
import winterly.client.render.DecorationFeatureRenderer;
import winterly.client.render.MobDecorationRenderers;
import winterly.config.WinterlyClientConfig;
import winterly.item.CommonSantaHatItem;
import winterly.item.CommonScarfItem;
import winterly.neoforge.compat.WinterlyCuriosIntegration;
import winterly.registry.CommonWinterlyItems;

@OnlyIn(Dist.CLIENT)
public class WinterlyNeoforgeClient {

	public void init(IEventBus bus) {
		bus.addListener(this::clientSetup);
		bus.addListener(this::registerModelLayers);
		bus.addListener(this::registerRenderLayers);
	}

	private void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
		event.registerLayerDefinition(WinterlyModelLayers.SANTA_HAT_LAYER, SantaHatModel::getTexturedModelData);
		event.registerLayerDefinition(WinterlyModelLayers.SCARF_LAYER, ScarfModel::getTexturedModelData);
	}

	private void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
		event.getContext().getEntityRenderDispatcher().renderers.forEach((entityType, entityRenderer) -> {
			if(entityRenderer instanceof ZombieRenderer renderer) {
				renderer.addLayer(new DecorationFeatureRenderer<>(renderer));
			}
			if(entityRenderer instanceof DrownedRenderer renderer) {
				renderer.addLayer(new DecorationFeatureRenderer<>(renderer));
			}
			if(entityRenderer instanceof SkeletonRenderer renderer) {
				renderer.addLayer(new DecorationFeatureRenderer<>(renderer));
			}
		});
	}

	private void clientSetup(FMLClientSetupEvent event) {
		if(ModList.get().isLoaded("curios")) {
			CommonWinterlyItems.ITEMS.forEach((resourceLocation, item) -> {
				if(item instanceof CommonScarfItem scarf) WinterlyCuriosIntegration.registerScarfRenderer(scarf);
				if(item instanceof CommonSantaHatItem hat) WinterlyCuriosIntegration.registerSantaHatRenderer(hat);
			});
		}

		MobDecorationRenderers.init();

		ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> WinterlyClientConfig.buildScreen(parent)));
	}
}
