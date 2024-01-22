package winterly.neoforge;

import net.minecraft.client.renderer.entity.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.neoforge.client.ConfigScreenHandler;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.registries.DeferredRegister;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;
import top.theillusivec4.curios.api.client.ICurioRenderer;
import winterly.Winterly;
import winterly.client.WinterlyModelLayers;
import winterly.client.model.SantaHatModel;
import winterly.client.model.ScarfModel;
import winterly.client.render.DecorationFeatureRenderer;
import winterly.client.render.MobDecorationRenderers;
import winterly.config.WinterlyClientConfig;
import winterly.neoforge.data.WinterlyDataAttachments;
import winterly.neoforge.registry.WinterlyBlockEntities;
import winterly.neoforge.registry.WinterlyBlocks;
import winterly.neoforge.registry.WinterlyFeatures;
import winterly.neoforge.registry.WinterlyItems;
import winterly.registry.CommonWinterlyBlocks;
import winterly.registry.CommonWinterlyItems;

import java.util.function.Supplier;

@SuppressWarnings("unused")
@Mod(Winterly.MOD_ID)
public class WinterlyNeoforge {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "winterly");

    public static Supplier<CreativeModeTab> WINTERLY_TAB = CREATIVE_MODE_TABS.register("winterly", () -> CreativeModeTab.builder().icon(CommonWinterlyBlocks.SNOWGUY.asItem()::getDefaultInstance).title(Component.literal("Winterly")).build());

    public WinterlyNeoforge(IEventBus bus) {
        CREATIVE_MODE_TABS.register(bus);

        WinterlyItems.init(bus);
        WinterlyBlocks.init(bus);
        WinterlyBlockEntities.init(bus);
        WinterlyFeatures.init(bus);
        WinterlyDataAttachments.init(bus);

        bus.addListener(this::buildCreativeTab);
		bus.addListener(this::registerModelLayers);
        bus.addListener(this::registerRenderLayers);
		bus.addListener(this::clientSetup);
		bus.addListener(this::commonSetup);

		if(FMLEnvironment.dist.isClient()) {
			ModLoadingContext.get().registerExtensionPoint(ConfigScreenHandler.ConfigScreenFactory.class, () -> new ConfigScreenHandler.ConfigScreenFactory((client, parent) -> WinterlyClientConfig.buildScreen(parent)));
		}
    }

    private void buildCreativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTab() == WINTERLY_TAB.get()) {
            CommonWinterlyItems.ITEMS.forEach((id, item) -> event.accept(item));
            CommonWinterlyBlocks.ITEMS.forEach((id, item) -> event.accept(item));
        }
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
        CommonWinterlyItems.ITEMS.forEach((resourceLocation, item) -> {
            if(item instanceof ICurioRenderer renderer) CuriosRendererRegistry.register(item, () -> renderer);
        });
		MobDecorationRenderers.init();
	}

	private void commonSetup(FMLCommonSetupEvent event) {

    }
}
