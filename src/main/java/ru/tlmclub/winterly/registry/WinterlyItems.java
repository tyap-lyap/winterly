package ru.tlmclub.winterly.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.item.CandleHatItem;
import ru.tlmclub.winterly.item.SantaHatItem;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

    public static final Item CANDLE_HAT = add("candle_hat", new CandleHatItem(settings()));
    public static final Item RED_SANTA_HAT = add("red_santa_hat", new SantaHatItem(settings()));
    public static final Item BLUE_SANTA_HAT = add("blue_santa_hat", new SantaHatItem(settings()));

    private static Item add(String name, Item item) {
        ITEMS.put(WinterlyMod.newId(name), item);
        return item;
    }

    private static FabricItemSettings settings() {
        FabricItemSettings settings = new FabricItemSettings();
        settings.group(WinterlyMod.ITEM_GROUP);
        return settings;
    }

    public static void register(){
        for (Identifier id : ITEMS.keySet()) {
            Registry.register(Registry.ITEM, id, ITEMS.get(id));
        }
    }
}
