package ru.tlmclub.winterly.client;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.minecraft.client.render.RenderLayer;
import ru.tlmclub.winterly.block.PresentBlock;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        WinterlyModels.register();

        BlockRenderLayerMap map = BlockRenderLayerMap.INSTANCE;

        WinterlyBlocks.BLOCKS.forEach((id, block) -> {
            if(block instanceof PresentBlock) map.putBlock(block, RenderLayer.getCutout());
        });

        WinterlyItems.ITEMS.forEach((id, item) -> {
            if(item instanceof TrinketRenderer renderer) TrinketRendererRegistry.registerRenderer(item, renderer);
        });
    }
}
