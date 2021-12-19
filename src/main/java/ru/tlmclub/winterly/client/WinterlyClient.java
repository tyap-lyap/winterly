package ru.tlmclub.winterly.client;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.LivingEntityFeatureRendererRegistrationCallback;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.DrownedEntityRenderer;
import net.minecraft.client.render.entity.SkeletonEntityRenderer;
import net.minecraft.client.render.entity.ZombieEntityRenderer;
import ru.tlmclub.winterly.block.GarlandLightsBlock;
import ru.tlmclub.winterly.block.IcicleBlock;
import ru.tlmclub.winterly.block.PresentBlock;
import ru.tlmclub.winterly.block.SnowguyBlock;
import ru.tlmclub.winterly.client.render.DecorationFeatureRenderer;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WinterlyModels.register();

        BlockRenderLayerMap map = BlockRenderLayerMap.INSTANCE;
        WinterlyBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof PresentBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof GarlandLightsBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof SnowguyBlock) map.putBlock(block, RenderLayer.getCutout());
            if(block instanceof IcicleBlock) map.putBlock(block, RenderLayer.getCutout());
        });
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
