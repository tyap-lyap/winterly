package ru.tlmclub.winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.item.ItemGroup;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.tlmclub.winterly.config.WinterlyClothConfig;
import ru.tlmclub.winterly.config.WinterlyConfig;
import ru.tlmclub.winterly.data.GiftBoxDataStack;
import ru.tlmclub.winterly.registry.WinterlyBlocks;
import ru.tlmclub.winterly.registry.WinterlyFeatures;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class Winterly implements ModInitializer {
    public static final String MOD_ID = "winterly";
	public static final Logger LOGGER = LoggerFactory.getLogger("Winterly");
    public static final ItemGroup ITEM_GROUP = createItemGroup();
    public static WinterlyConfig config = WinterlyClothConfig.init();

    @Override
    public void onInitialize() {
        WinterlyItems.init();
        WinterlyBlocks.init();
        WinterlyFeatures.init();

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
			dispatcher.register(CommandManager.literal("winterly").then(CommandManager.literal("dump-gifts").requires(source -> source.hasPermissionLevel(4)).executes(context -> {
				var player = context.getSource().getPlayer();
				if(player != null) {
					GiftBoxDataStack.stack.forEach((pos, giftBoxData) -> {
						player.sendMessage(Text.of("[" + pos.toShortString() + "]: " + (giftBoxData.stacks.isEmpty() ? "Empty" : "")));
						giftBoxData.stacks.forEach(st -> {
							String name = Language.getInstance().get(st.getTranslationKey());
							player.sendMessage(Text.of("- " + name + " x" + st.getCount()));
						});
					});
				}
				return 1;
			})));
		});
    }

    private static ItemGroup createItemGroup() {
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
