package ru.tlmclub.winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import ru.pinkgoosik.goosikconfig.api.Config;
import ru.tlmclub.winterly.config.WinterlyConfig;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyFeatures;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";
    public static final Config CONFIG = new WinterlyConfig();
    public static final ItemGroup ITEM_GROUP = createItemGroup();

    @Override
    public void onInitialize() {
        WinterlyItems.init();
        WinterlyBlocks.init();
        WinterlyFeatures.init();
    }

    private static ItemGroup createItemGroup() {
        return FabricItemGroupBuilder
                .create(locate("items"))
                .icon(() -> WinterlyBlocks.SNOWGUY.asItem().getDefaultStack())
                .appendItems(stacks -> {
                    WinterlyItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                    WinterlyBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                })
                .build();
    }

    public static Identifier locate(String path) {
        return new Identifier(MOD_ID, path);
    }
}
