package ru.tlmclub.winterly.registry;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import ru.tlmclub.winterly.WinterlyMod;
import ru.tlmclub.winterly.item.*;
import ru.tlmclub.winterly.item.tool.*;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class WinterlyItems {
    public static final Map<Identifier, Item> ITEMS = new LinkedHashMap<>();

//    public static final Item GINGERBREAD_MAN = add("gingerbread_man", new Item(settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.5F).build())));
//    public static final Item EDIBLE_SNOW = add("edible_snow", new Item(settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 0), 0.5F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 1), 0.5F).build())));
//    public static final Item PISS_SNOW = add("yellow_snow", new Item(settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 0.9F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), 0.8F).build())));
    public static final Item RED_CANDY_CANE = add("red_candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
    public static final Item GREEN_CANDY_CANE = add("green_candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
    public static final Item BLUE_CANDY_CANE = add("blue_candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
//    public static final Item YELLOW_CANDY_CANE = add("yellow_candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
//    public static final Item CANDY_CANE = add("candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
//    public static final Item MULLED_WINE = add("mulled_wine", new Item(settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).build())));
//    public static final Item EGGNOG = add("eggnog", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 0.2F).build())));
//    public static final Item COCOA = add("cocoa", new Item(settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).build())));
//    public static final Item KIDS_CHAMPAGNE = add("kids_champagne", new Item(settings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 400, 0), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0, 0), 0.3F).build())));

    public static final Item CRYOMARBLE_SHARD = add("cryomarble_shard", new Item(settings()));
    public static final Item CRYOMARBLE = add("cryomarble", new Item(settings()));

    public static final Item CRYOMARBLE_SWORD = add("cryomarble_sword", new CryomarbleSwordItem(ToolMaterials.DIAMOND, 3, -2.4F, settings()));
    public static final Item CRYOMARBLE_SHOVEL = add("cryomarble_shovel", new CryomarbleShovelItem(ToolMaterials.DIAMOND, 1.5F, -3.0F, settings()));
    public static final Item CRYOMARBLE_PICKAXE = add("cryomarble_pickaxe", new CryomarblePickaxeItem(ToolMaterials.DIAMOND, 1, -2.8F, settings()));
    public static final Item CRYOMARBLE_AXE = add("cryomarble_axe", new CryomarbleAxeItem(ToolMaterials.DIAMOND, 5.0F, -3.0F, settings()));
    public static final Item CRYOMARBLE_HOE = add("cryomarble_hoe", new CryomarbleHoeItem(ToolMaterials.DIAMOND, -3, 0.0F, settings()));

    public static final Item RED_SANTA_HAT = add("red_santa_hat", new SantaHatItem(settings(), "red"));
    public static final Item BLUE_SANTA_HAT = add("blue_santa_hat", new SantaHatItem(settings(), "blue"));

    public static final Item WHITE_SCARF = add("white_scarf", new ScarfItem(settings(), "white"));
    public static final Item RED_SCARF = add("red_scarf", new ScarfItem(settings(), "red"));
    public static final Item GREEN_SCARF = add("green_scarf", new ScarfItem(settings(), "green"));
    public static final Item BLUE_SCARF = add("blue_scarf", new ScarfItem(settings(), "blue"));

    private static Item add(String name, Item item) {
        ITEMS.put(WinterlyMod.newId(name), item);
        return item;
    }

    private static FabricItemSettings settings() {
        FabricItemSettings settings = new FabricItemSettings();
        settings.group(WinterlyMod.ITEM_GROUP);
        return settings;
    }

    public static void init() {
        ITEMS.forEach((id, item) -> Registry.register(Registry.ITEM, id, item));
    }
}
