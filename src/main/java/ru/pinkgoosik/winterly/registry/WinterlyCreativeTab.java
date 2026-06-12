package ru.pinkgoosik.winterly.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import ru.pinkgoosik.winterly.Winterly;

public class WinterlyCreativeTab {
	public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Winterly.MOD_ID);

	public static final RegistryObject<CreativeModeTab> ITEMS = CREATIVE_MODE_TABS.register("items", () -> CreativeModeTab.builder()
		.icon(() -> new ItemStack(WinterlyBlocks.SNOWGUY.get()))
		.title(Component.translatable("itemGroup.winterly.items"))
		.displayItems((params, output) -> {
			WinterlyItems.ITEMS.getEntries().forEach(entry -> output.accept(entry.get()));
			WinterlyBlocks.BLOCKS.getEntries().forEach(entry -> output.accept(entry.get()));
		})
		.build());
}
