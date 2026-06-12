package ru.pinkgoosik.winterly.registry;

import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.item.SantaHatItem;
import ru.pinkgoosik.winterly.item.ScarfItem;
import ru.pinkgoosik.winterly.item.tool.*;

import java.util.function.Supplier;

public class WinterlyItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Winterly.MOD_ID);

	public static final RegistryObject<Item> RED_CANDY_CANE = register("red_candy_cane", () -> new Item(properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));
	public static final RegistryObject<Item> GREEN_CANDY_CANE = register("green_candy_cane", () -> new Item(properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));
	public static final RegistryObject<Item> BLUE_CANDY_CANE = register("blue_candy_cane", () -> new Item(properties().food(new FoodProperties.Builder().nutrition(2).saturationMod(0.1F).fast().build())));

	public static final RegistryObject<Item> CRYOMARBLE_SHARD = register("cryomarble_shard", () -> new Item(properties()));
	public static final RegistryObject<Item> CRYOMARBLE = register("cryomarble", () -> new Item(properties()));

	public static final RegistryObject<Item> CRYOMARBLE_SWORD = register("cryomarble_sword", () -> new CryomarbleSwordItem(Tiers.DIAMOND, 3, -2.4F, properties()));
	public static final RegistryObject<Item> CRYOMARBLE_SHOVEL = register("cryomarble_shovel", () -> new CryomarbleShovelItem(Tiers.DIAMOND, 1.5F, -3.0F, properties()));
	public static final RegistryObject<Item> CRYOMARBLE_PICKAXE = register("cryomarble_pickaxe", () -> new CryomarblePickaxeItem(Tiers.DIAMOND, 1, -2.8F, properties()));
	public static final RegistryObject<Item> CRYOMARBLE_AXE = register("cryomarble_axe", () -> new CryomarbleAxeItem(Tiers.DIAMOND, 5.0F, -3.0F, properties()));
	public static final RegistryObject<Item> CRYOMARBLE_HOE = register("cryomarble_hoe", () -> new CryomarbleHoeItem(Tiers.DIAMOND, -3, 0.0F, properties()));

	public static final RegistryObject<Item> RED_SANTA_HAT = register("red_santa_hat", () -> new SantaHatItem(properties(), "red"));
	public static final RegistryObject<Item> BLUE_SANTA_HAT = register("blue_santa_hat", () -> new SantaHatItem(properties(), "blue"));

	public static final RegistryObject<Item> WHITE_SCARF = register("white_scarf", () -> new ScarfItem(properties(), "white"));
	public static final RegistryObject<Item> RED_SCARF = register("red_scarf", () -> new ScarfItem(properties(), "red"));
	public static final RegistryObject<Item> GREEN_SCARF = register("green_scarf", () -> new ScarfItem(properties(), "green"));
	public static final RegistryObject<Item> BLUE_SCARF = register("blue_scarf", () -> new ScarfItem(properties(), "blue"));
	public static final RegistryObject<Item> RAINBOW_SCARF = register("rainbow_scarf", () -> new ScarfItem(properties(), "rainbow"));

	static <T extends Item> RegistryObject<T> register(String name, Supplier<T> item) {
		return ITEMS.register(name, item);
	}

	static void registerBlockItem(String name, RegistryObject<? extends Block> block) {
		ITEMS.register(name, () -> new BlockItem(block.get(), properties()));
	}
	static Item.Properties properties() {
		return new Item.Properties();
	}
}
