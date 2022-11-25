package winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemGroup;
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
		}
    }

    private static ItemGroup createItemGroup() {
		if(FabricLoader.getInstance().isModLoaded("owo")) {
			return WinterlyOwoLibIntegration.createItemGroup();
		}

        return FabricItemGroupBuilder
                .create(id("items"))
                .icon(() -> WinterlyBlocks.SNOWGUY.asItem().getDefaultStack())
                .appendItems(stacks -> {
                    WinterlyItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                    WinterlyBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
                })
                .build();
    }

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

}
