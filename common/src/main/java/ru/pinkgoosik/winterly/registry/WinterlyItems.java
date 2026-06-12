package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ToolMaterial;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.level.block.Block;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.item.SantaHatItem;
import ru.pinkgoosik.winterly.item.ScarfItem;
import ru.pinkgoosik.winterly.item.tool.*;

import java.util.function.BiFunction;
import java.util.function.Supplier;

public class WinterlyItems {

	public static final DeferredRegister<Item> ITEMS =
			DeferredRegister.create(Registries.ITEM, Winterly.MOD_ID);

	public static final RegisterSupplier<Item> RED_CANDY_CANE =
			register(
					"red_candy_cane",
					(_, props) -> new Item(props),
					WinterlyItems::candyCaneProperties
			);

	public static final RegisterSupplier<Item> GREEN_CANDY_CANE =
			register(
					"green_candy_cane",
					(_, props) -> new Item(props),
					WinterlyItems::candyCaneProperties
			);

	public static final RegisterSupplier<Item> BLUE_CANDY_CANE =
			register(
					"blue_candy_cane",
					(_, props) -> new Item(props),
					WinterlyItems::candyCaneProperties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_SHARD =
			register(
					"cryomarble_shard",
					(_, props) -> new Item(props),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE =
			register(
					"cryomarble",
					(_, props) -> new Item(props),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_SWORD =
			register(
					"cryomarble_sword",
					(_, props) -> new CryomarbleSwordItem(
							ToolMaterial.DIAMOND,
							3,
							-2.4F,
							props
					),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_SHOVEL =
			register(
					"cryomarble_shovel",
					(_, props) -> new CryomarbleShovelItem(
							ToolMaterial.DIAMOND,
							1.5F,
							-3.0F,
							props
					),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_PICKAXE =
			register(
					"cryomarble_pickaxe",
					(_, props) -> new CryomarblePickaxeItem(
							ToolMaterial.DIAMOND,
							1,
							-2.8F,
							props
					),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_AXE =
			register(
					"cryomarble_axe",
					(_, props) -> new CryomarbleAxeItem(
							ToolMaterial.DIAMOND,
							5.0F,
							-3.0F,
							props
					),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> CRYOMARBLE_HOE =
			register(
					"cryomarble_hoe",
					(_, props) -> new CryomarbleHoeItem(
							ToolMaterial.DIAMOND,
							-3,
							0.0F,
							props
					),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> RED_SANTA_HAT =
			register(
					"red_santa_hat",
					(_, props) -> new SantaHatItem(props, "red"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> BLUE_SANTA_HAT =
			register(
					"blue_santa_hat",
					(_, props) -> new SantaHatItem(props, "blue"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> WHITE_SCARF =
			register(
					"white_scarf",
					(_, props) -> new ScarfItem(props, "white"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> RED_SCARF =
			register(
					"red_scarf",
					(_, props) -> new ScarfItem(props, "red"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> GREEN_SCARF =
			register(
					"green_scarf",
					(_, props) -> new ScarfItem(props, "green"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> BLUE_SCARF =
			register(
					"blue_scarf",
					(_, props) -> new ScarfItem(props, "blue"),
					WinterlyItems::properties
			);

	public static final RegisterSupplier<Item> RAINBOW_SCARF =
			register(
					"rainbow_scarf",
					(_, props) -> new ScarfItem(props, "rainbow"),
					WinterlyItems::properties
			);

	private static <T extends Item> RegisterSupplier<T> register(
			String name,
			BiFunction<String, Item.Properties, T> factory,
			Supplier<Item.Properties> propertiesSupplier
	) {
		return ITEMS.register(name, () -> {

			ResourceKey<Item> key = ResourceKey.create(
					Registries.ITEM,
					Identifier.fromNamespaceAndPath(
							Winterly.MOD_ID,
							name
					)
			);

			Item.Properties properties =
					propertiesSupplier.get().setId(key);

			return factory.apply(name, properties);
		});
	}

	static void registerBlockItem(
			String name,
			RegisterSupplier<? extends Block> block
	) {
		ITEMS.register(name, () -> {

			ResourceKey<Item> key = ResourceKey.create(
					Registries.ITEM,
					Identifier.fromNamespaceAndPath(
							Winterly.MOD_ID,
							name
					)
			);

			return new BlockItem(
					block.get(),
					properties().setId(key)
			);
		});
	}

	static Item.Properties properties() {
		return new Item.Properties();
	}

	static Item.Properties candyCaneProperties() {
		return new Item.Properties()
				.food(
						new FoodProperties.Builder()
								.nutrition(2)
								.saturationModifier(0.1F)
								.build()
				)
				.component(
						DataComponents.CONSUMABLE,
						Consumable.builder()
								.consumeSeconds(0.8F)
								.build()
				);
	}
}
