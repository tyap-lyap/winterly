package ru.tlmclub.winterly.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.item.HatItem;
import ru.tlmclub.winterly.item.ScarfItem;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    public static final Item CANDLE_HAT = add("candle_hat", new HatItem(settings(), "candle_hat_on_head"));
    public static final Item RED_SANTA_HAT = add("red_santa_hat", new HatItem(settings(), "red_santa_hat_on_head"));
    public static final Item BLUE_SANTA_HAT = add("blue_santa_hat", new HatItem(settings(), "blue_santa_hat_on_head"));

    public static final Item WHITE_SCARF = add("white_scarf", new ScarfItem(settings(), "white_scarf_on_body"));
    public static final Item RED_SCARF = add("red_scarf", new ScarfItem(settings(), "red_scarf_on_body"));
    public static final Item GREEN_SCARF = add("green_scarf", new ScarfItem(settings(), "green_scarf_on_body"));
    public static final Item BLUE_SCARF = add("blue_scarf", new ScarfItem(settings(), "blue_scarf_on_body"));

    private static Item add(String name, Item item) {
        ITEMS.put(WinterlyMod.newId(name), item);
        return item;
    }

    private static FabricItemSettings settings() {
        FabricItemSettings settings = new FabricItemSettings();
        settings.group(WinterlyMod.ITEM_GROUP);
        return settings;
    }

    public static void register() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
    }
}
