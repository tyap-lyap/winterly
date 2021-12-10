package ru.tlmclub.winterly;

import io.wispforest.owo.itemgroup.OwoItemGroup;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import ru.tlmclub.winterly.config.WinterlyConfig;
import ru.tlmclub.winterly.item.WinterlyItemGroup;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";
    public static final WinterlyConfig CONFIG = new WinterlyConfig();
    public static final OwoItemGroup ITEM_GROUP = new WinterlyItemGroup(newId("items"));

    @Override
    public void onInitialize() {
        WinterlyItems.register();
        WinterlyBlocks.register();
        ITEM_GROUP.initialize();
    }

    public static Identifier newId(String path) {
        return new Identifier(MOD_ID, path);
    }
}
