package winterly.fabric;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import winterly.Winterly;
import winterly.config.WinterlyClothConfig;
import winterly.fabric.compat.WinterlyOwoLibIntegration;
import winterly.fabric.registry.WinterlyBlockEntities;
import winterly.fabric.registry.WinterlyBlocks;
import winterly.registry.CommonWinterlyBlocks;
import winterly.fabric.registry.WinterlyFeatures;
import winterly.fabric.registry.WinterlyItems;
import winterly.registry.CommonWinterlyItems;

public class WinterlyFabric implements ModInitializer {
    public static CreativeModeTab itemGroup;

    @Override
    public void onInitialize() {
		itemGroup = createItemGroup();
        WinterlyItems.init();

		WinterlyBlocks.init();
		WinterlyBlockEntities.init();
        WinterlyFeatures.init();

		if(FabricLoader.getInstance().isModLoaded("owo")) {
			WinterlyOwoLibIntegration.initItemGroup();
			ItemGroupEvents.modifyEntriesEvent(BuiltInRegistries.CREATIVE_MODE_TAB.getResourceKey(itemGroup).get()).register(entries -> {
				CommonWinterlyItems.ITEMS.forEach((id, item) -> entries.accept(item.getDefaultInstance()));
				CommonWinterlyBlocks.ITEMS.forEach((id, item) -> entries.accept(item.getDefaultInstance()));
			});
		}
    }

    private static CreativeModeTab createItemGroup() {
		if(FabricLoader.getInstance().isModLoaded("owo")) {
			return WinterlyOwoLibIntegration.createItemGroup();
		}
        var group = FabricItemGroup.builder().title(Component.translatable("itemGroup.winterly.items"))
			.icon(() -> CommonWinterlyBlocks.SNOWGUY.asItem().getDefaultInstance())
			.displayItems((displayContext, entries) -> {
				CommonWinterlyItems.ITEMS.forEach((id, item) -> entries.accept(item.getDefaultInstance()));
				CommonWinterlyBlocks.ITEMS.forEach((id, item) -> entries.accept(item.getDefaultInstance()));
			}).build();
		Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB, Winterly.id("items"), group);
		return group;
    }

}
