package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import ru.pinkgoosik.winterly.Winterly;

import java.util.stream.StreamSupport;

public class WinterlyCreativeTab {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Winterly.MOD_ID);

	public static final RegisterSupplier<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("items", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
		.icon(() -> new ItemStack(WinterlyBlocks.SNOWGUY.get()))
		.title(Component.translatable("itemGroup.winterly.items"))
		.displayItems((_, output) -> {
			StreamSupport.stream(BuiltInRegistries.ITEM.spliterator(), false)
				.filter(item -> item != Items.AIR)
					.filter(item -> {
						Identifier id = BuiltInRegistries.ITEM.getKey(item);
						return Winterly.MOD_ID.equals(id.getNamespace());
					})
				.forEach(output::accept);
		})
		.build());
}
