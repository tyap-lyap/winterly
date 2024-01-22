package winterly.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CommonWinterlyItems {
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();

    //    public static final Item GINGERBREAD_MAN = add("gingerbread_man", new Item(settings().food(new FoodComponent.Builder().hunger(3).saturationModifier(0.5F).build())));
//    public static final Item EDIBLE_SNOW = add("edible_snow", new Item(settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 120, 0), 0.5F).statusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 120, 1), 0.5F).build())));
//    public static final Item PISS_SNOW = add("yellow_snow", new Item(settings().food(new FoodComponent.Builder().hunger(1).saturationModifier(0F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 0.9F).statusEffect(new StatusEffectInstance(StatusEffects.POISON, 80, 1), 0.8F).build())));
    public static Item RED_CANDY_CANE;
    public static Item GREEN_CANDY_CANE;
    public static Item BLUE_CANDY_CANE;
//    public static final Item YELLOW_CANDY_CANE = add("yellow_candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
//    public static final Item CANDY_CANE = add("candy_cane", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.1F).snack().build())));
//    public static final Item MULLED_WINE = add("mulled_wine", new Item(settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.2F).build())));
//    public static final Item EGGNOG = add("eggnog", new Item(settings().food(new FoodComponent.Builder().hunger(2).saturationModifier(0.4F).statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 200, 0), 0.2F).build())));
//    public static final Item COCOA = add("cocoa", new Item(settings().food(new FoodComponent.Builder().hunger(4).saturationModifier(0.5F).build())));
//    public static final Item KIDS_CHAMPAGNE = add("kids_champagne", new Item(settings().food(new FoodComponent.Builder().hunger(10).saturationModifier(0.1F).statusEffect(new StatusEffectInstance(StatusEffects.REGENERATION, 100, 0), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 400, 0), 0.3F).statusEffect(new StatusEffectInstance(StatusEffects.INSTANT_HEALTH, 0, 0), 0.3F).build())));

    public static Item CRYOMARBLE_SHARD;
    public static Item CRYOMARBLE;

    public static Item CRYOMARBLE_SWORD;
    public static Item CRYOMARBLE_SHOVEL;
    public static Item CRYOMARBLE_PICKAXE;
    public static Item CRYOMARBLE_AXE;
    public static Item CRYOMARBLE_HOE;

    public static Item RED_SANTA_HAT;
    public static Item BLUE_SANTA_HAT;

    public static Item WHITE_SCARF;
    public static Item RED_SCARF;
    public static Item GREEN_SCARF;
    public static Item BLUE_SCARF;
    public static Item RAINBOW_SCARF;
}

