package winterly.neoforge.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import winterly.Winterly;
import winterly.item.tool.*;
import winterly.neoforge.item.SantaHatItem;
import winterly.neoforge.item.ScarfItem;

import java.util.function.Supplier;

import static winterly.registry.CommonWinterlyItems.*;

@SuppressWarnings("unused")
public class WinterlyItems {

    public static final DeferredRegister.Items ITEMS_REGISTERER = DeferredRegister.createItems("winterly");

    public static void init(IEventBus eventBus) {
        add("red_candy_cane", () -> RED_CANDY_CANE = new Item(settings().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));
        add("green_candy_cane", () -> GREEN_CANDY_CANE = new Item(settings().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));
        add("blue_candy_cane", () -> BLUE_CANDY_CANE = new Item(settings().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));

        add("cryomarble_shard", () -> CRYOMARBLE_SHARD = new Item(settings()));
        add("cryomarble", () -> CRYOMARBLE = new Item(settings()));

        add("cryomarble_sword", () -> CRYOMARBLE_SWORD = new CryomarbleSwordItem(Tiers.DIAMOND, 3, -2.4F, settings()));
        add("cryomarble_shovel", () -> CRYOMARBLE_SHOVEL = new CryomarbleShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, settings()));
        add("cryomarble_pickaxe", () -> CRYOMARBLE_PICKAXE = new CryomarblePickaxeItem(Tiers.DIAMOND, 1, -2.8F, settings()));
        add("cryomarble_axe", () -> CRYOMARBLE_AXE = new CryomarbleAxeItem(Tiers.DIAMOND, 5.0F, -3.0F, settings()));
        add("cryomarble_hoe", () -> CRYOMARBLE_HOE = new CryomarbleHoeItem(Tiers.DIAMOND, -3, 0.0F, settings()));

        add("red_santa_hat", () -> RED_SANTA_HAT = new SantaHatItem(settings(), "red"));
        add("blue_santa_hat", () -> BLUE_SANTA_HAT = new SantaHatItem(settings(), "blue"));

        add("white_scarf", () -> WHITE_SCARF = new ScarfItem(settings(), "white"));
        add("red_scarf", () -> RED_SCARF = new ScarfItem(settings(), "red"));
        add("green_scarf", () -> GREEN_SCARF = new ScarfItem(settings(), "green"));
        add("blue_scarf", () -> BLUE_SCARF = new ScarfItem(settings(), "blue"));
        add("rainbow_scarf", () -> RAINBOW_SCARF = new ScarfItem(settings(), "rainbow"));

        ITEMS_REGISTERER.register(eventBus);
    }

    private static <T extends Item> void add(String name, Supplier<? extends T> sup) {
        ITEMS_REGISTERER.register(name, () -> {
            var item = sup.get();
            ITEMS.put(Winterly.id(name), item);
            return item;
        });
    }
}
