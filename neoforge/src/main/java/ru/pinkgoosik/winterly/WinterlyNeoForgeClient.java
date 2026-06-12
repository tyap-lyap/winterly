package ru.pinkgoosik.winterly;

import cc.sighs.oelib.config.ui.screen.ConfigScreen;
import cc.sighs.oelib.platform.Platform;
import com.google.common.reflect.TypeToken;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.client.renderer.entity.state.SkeletonRenderState;
import net.minecraft.client.renderer.entity.state.ZombieRenderState;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.skeleton.AbstractSkeleton;
import net.minecraft.world.entity.monster.zombie.Zombie;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.renderstate.RegisterRenderStateModifiersEvent;
import ru.pinkgoosik.winterly.client.WinterlyModelLayers;
import ru.pinkgoosik.winterly.client.model.SantaHatModel;
import ru.pinkgoosik.winterly.client.model.ScarfModel;
import ru.pinkgoosik.winterly.client.render.MobDecorations;
import ru.pinkgoosik.winterly.client.render.NeoForgeDecorationFeatureRenderer;
import ru.pinkgoosik.winterly.client.render.NeoForgeRenderStateKeys;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;
import ru.pinkgoosik.winterly.item.CommonSantaHatItem;
import ru.pinkgoosik.winterly.item.CommonScarfItem;
import ru.pinkgoosik.winterly.registry.WinterlyItems;

@Mod(value = Winterly.MOD_ID, dist = Dist.CLIENT)
public class WinterlyNeoForgeClient {

    public WinterlyNeoForgeClient(IEventBus bus, ModContainer container) {
        bus.addListener(WinterlyNeoForgeClient::clientSetup);
        bus.addListener(WinterlyNeoForgeClient::registerModelLayers);
        bus.addListener(WinterlyNeoForgeClient::registerRenderLayers);
        bus.addListener(WinterlyNeoForgeClient::registerRenderStateModifiers);
    }


    public static void registerModelLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(WinterlyModelLayers.SANTA_HAT_LAYER, SantaHatModel::getTexturedModelData);
        event.registerLayerDefinition(WinterlyModelLayers.SCARF_LAYER, ScarfModel::getTexturedModelData);
    }

    public static void clientSetup(FMLClientSetupEvent event) {
        ModLoadingContext.get().registerExtensionPoint(IConfigScreenFactory.class,
                () -> (_, screen) ->
                        new ConfigScreen(screen, Winterly.MOD_ID));
        MobDecorations.init();
        if (Platform.isModLoaded("curios")) {
            WinterlyItems.ITEMS.entries().forEach(entry -> {
                    if (entry.get() instanceof CommonScarfItem scarf) WinterlyCuriosIntegration.registerScarfRenderer(scarf);
                    if (entry.get() instanceof CommonSantaHatItem hat) WinterlyCuriosIntegration.registerSantaHatRenderer(hat);
            });
        }
    }
    public static void registerRenderLayers(EntityRenderersEvent.AddLayers event) {
        var zombie = event.getRenderer(EntityType.ZOMBIE);
        if (zombie instanceof ZombieRenderer zr) {
            zr.addLayer(new NeoForgeDecorationFeatureRenderer<>(zr));
        }
        var drowned = event.getRenderer(EntityType.DROWNED);
        if (drowned instanceof DrownedRenderer dr) {
            dr.addLayer(new NeoForgeDecorationFeatureRenderer<>(dr));
        }
        var skeleton = event.getRenderer(EntityType.SKELETON);
        if (skeleton instanceof SkeletonRenderer sr) {
            sr.addLayer(new NeoForgeDecorationFeatureRenderer<>(sr));
        }
    }

    public static void registerRenderStateModifiers(RegisterRenderStateModifiersEvent event) {
        event.registerEntityModifier(
                new TypeToken<LivingEntityRenderer<LivingEntity, LivingEntityRenderState, ?>>() {},
                WinterlyNeoForgeClient::copyVisibleHat
        );
        event.registerEntityModifier(
                new TypeToken<AbstractZombieRenderer<Zombie, ZombieRenderState, ?>>() {},
                WinterlyNeoForgeClient::copyZombieDecoration
        );
        event.registerEntityModifier(
                new TypeToken<AbstractSkeletonRenderer<AbstractSkeleton, SkeletonRenderState>>() {},
                WinterlyNeoForgeClient::copySkeletonDecoration
        );
    }

    private static void copyVisibleHat(LivingEntity entity, LivingEntityRenderState state) {
        if (Platform.isModLoaded("curios")) {
            boolean visibleHat = entity instanceof Player player && WinterlyCuriosIntegration.hasVisibleHat(player);
            state.setRenderData(NeoForgeRenderStateKeys.VISIBLE_HAT, visibleHat);
        }
    }

    private static void copyZombieDecoration(Zombie entity, ZombieRenderState state) {
        state.setRenderData(
                NeoForgeRenderStateKeys.MOB_DECORATION,
                WinterlyPlatformHolder.get().getDecorationData(entity)
        );
    }

    private static void copySkeletonDecoration(AbstractSkeleton entity, SkeletonRenderState state) {
        state.setRenderData(
                NeoForgeRenderStateKeys.MOB_DECORATION,
                WinterlyPlatformHolder.get().getDecorationData(entity)
        );
    }
}
