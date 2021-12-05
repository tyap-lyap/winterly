package ru.tlmclub.winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";

    public static final ItemGroup ITEM_GROUP = FabricItemGroupBuilder
            .create(newId("items"))
            .icon(WinterlyItems.CANDLE_HAT::getDefaultStack)
            .build();

    @Override
    public void onInitialize() {
        WinterlyItems.register();
        WinterlyBlocks.register();
    }

    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
