package winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winterly.compat.WinterlyOwoLibIntegration;
import winterly.config.WinterlyClothConfig;
import winterly.config.WinterlyConfig;
import winterly.registry.WinterlyBlockEntities;
import winterly.registry.WinterlyBlocks;
import winterly.registry.WinterlyFeatures;
import winterly.registry.WinterlyItems;

public class Winterly implements ModInitializer {
    public static final String MOD_ID = "winterly";
	public static final Logger LOGGER = LoggerFactory.getLogger("Winterly");
    public static ItemGroup itemGroup;
    public static WinterlyConfig config = WinterlyClothConfig.init();

    @Override
    public void onInitialize() {
		itemGroup = createItemGroup();
        WinterlyItems.init();
        WinterlyBlocks.init();
		WinterlyBlockEntities.init();
        WinterlyFeatures.init();

		if(FabricLoader.getInstance().isModLoaded("owo")) {
			WinterlyOwoLibIntegration.initItemGroup();
			ItemGroupEvents.modifyEntriesEvent(Registries.ITEM_GROUP.getKey(itemGroup).get()).register(entries -> {
				WinterlyItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
				WinterlyBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
			});
		}
    }

    private static ItemGroup createItemGroup() {
		if(FabricLoader.getInstance().isModLoaded("owo")) {
			return WinterlyOwoLibIntegration.createItemGroup();
		}
        var group = FabricItemGroup.builder().displayName(Text.translatable("itemGroup.winterly.items"))
			.icon(() -> WinterlyBlocks.SNOWGUY.asItem().getDefaultStack())
			.entries((displayContext, entries) -> {
				WinterlyItems.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
				WinterlyBlocks.ITEMS.forEach((id, item) -> entries.add(item.getDefaultStack()));
			}).build();
		Registry.register(Registries.ITEM_GROUP, id("items"), group);
		return group;
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

}
