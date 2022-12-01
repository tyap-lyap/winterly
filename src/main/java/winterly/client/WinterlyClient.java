package winterly.client;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import winterly.block.*;
import winterly.client.render.DecorationFeatureRenderer;
import winterly.client.render.MobDecorationRenderers;
import winterly.registry.WinterlyBlocks;
import winterly.registry.WinterlyItems;

public class WinterlyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WinterlyModelLayers.init();
        MobDecorationRenderers.init();

        BlockRenderLayerMap map = BlockRenderLayerMap.INSTANCE;
        WinterlyBlocks.BLOCKS.forEach((id, block) -> {
			if(block instanceof GiftBoxBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof GarlandLightsBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof SnowguyBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof IcicleBlock) map.putBlock(block, RenderLayer.getCutout());
			if(block instanceof FrozenGrassBlock) map.putBlock(block, RenderLayer.getCutout());
			if(block instanceof FrozenFlowerBlock) map.putBlock(block, RenderLayer.getCutout());
        });
        map.putBlock(WinterlyBlocks.ICICLE_BLOCK, RenderLayer.getTranslucent());
        map.putBlock(WinterlyBlocks.ICICLE_PANE, RenderLayer.getTranslucent());
        map.putBlock(WinterlyBlocks.ICICLE_BARS, RenderLayer.getCutout());

        WinterlyItems.ITEMS.forEach((id, item) -> {
            if(item instanceof TrinketRenderer renderer) TrinketRendererRegistry.registerRenderer(item, renderer);
        });

        LivingEntityFeatureRendererRegistrationCallback.EVENT.register((entityType, entityRenderer, registrationHelper, context) -> {
            if(entityRenderer instanceof ZombieEntityRenderer renderer) {
                registrationHelper.register(new DecorationFeatureRenderer<>(renderer));
            }
            if(entityRenderer instanceof DrownedEntityRenderer renderer) {
                registrationHelper.register(new DecorationFeatureRenderer<>(renderer));
            }
            if(entityRenderer instanceof SkeletonEntityRenderer renderer) {
                registrationHelper.register(new DecorationFeatureRenderer<>(renderer));
            }
        });

    }

}
