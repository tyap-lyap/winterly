package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.item.GiftBoxBlockItem;

public class WinterlyFabricItems {
	public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, Winterly.MOD_ID);

	static void registerBlockItem(String name, RegisterSupplier<? extends Block> block) {
		ITEMS.register(name, () -> {
			ResourceKey<Item> key = ResourceKey.create(Registries.ITEM, Identifier.fromNamespaceAndPath(Winterly.MOD_ID, name));
			return new GiftBoxBlockItem(block.get(), new Item.Properties().setId(key));
		});
	}
}
