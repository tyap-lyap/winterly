package ru.tlmclub.winterly.client;

import dev.emi.trinkets.api.client.TrinketRenderer;
import dev.emi.trinkets.api.client.TrinketRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {

        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> {
            out.accept(new ModelIdentifier(WinterlyMod.MOD_ID + ":red_santa_hat_on_head" + "#inventory"));
            out.accept(new ModelIdentifier(WinterlyMod.MOD_ID + ":blue_santa_hat_on_head" + "#inventory"));
            out.accept(new ModelIdentifier(WinterlyMod.MOD_ID + ":candle_hat_on_head" + "#inventory"));
        });

        WinterlyItems.ITEMS.forEach((id, item) -> {
            if(item instanceof TrinketRenderer renderer) {
                TrinketRendererRegistry.registerRenderer(item, renderer);
            }
        });
    }
}
