package ru.tlmclub.winterly;

import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.fabricmc.api.ModInitializer;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import ru.pinkgoosik.goosikconfig.api.Config;
import ru.tlmclub.winterly.config.WinterlyConfig;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";
    public static final Config CONFIG = new WinterlyConfig();
    public static final ItemGroup ITEM_GROUP = createItemGroup();

    @Override
    public void onInitialize() {
        WinterlyItems.register();
        WinterlyBlocks.register();
        ((OwoItemGroup)ITEM_GROUP).initialize();
    }

    private static ItemGroup createItemGroup(){
        return new OwoItemGroup(newId("items")) {
            @Override
            protected void setup() {
                addButton(ItemGroupButton.discord("https://discord.gg/DcemWeskeZ"));
            }
            @Override
            public ItemStack createIcon() {
                return WinterlyBlocks.SNOWGUY.asItem().getDefaultStack();
            }
            @Override
            public void appendStacks(DefaultedList<ItemStack> stacks) {
                WinterlyItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                WinterlyBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
            }
        };
    }

    public static Identifier newId(String path) {
        return new Identifier(MOD_ID, path);
    }
}
