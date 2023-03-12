package winterly.compat;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import winterly.Winterly;
import winterly.registry.WinterlyBlocks;
import winterly.util.UpdateChecker;

public class WinterlyOwoLibIntegration {
	public static final Identifier ICONS_TEXTURE = Winterly.id("textures/gui/icons.png");

	public static ItemGroup createItemGroup() {
		return OwoItemGroup.builder(Winterly.id("items"), () -> Icon.of(WinterlyBlocks.SNOWGUY))
				.initializer(group -> {
					group.addButton(ItemGroupButton.link(group, Icon.of(ICONS_TEXTURE, 0, 0, 64, 64), "discord", "https://discord.gg/DcemWeskeZ"));
					if(UpdateChecker.check()) {
						group.addButton(ItemGroupButton.link(group, Icon.of(ICONS_TEXTURE, 16, 0, 64, 64), "updated", "https://modrinth.com/mod/winterly"));
					}
				}).build();
	}

	public static void initItemGroup() {
		if(Winterly.itemGroup instanceof OwoItemGroup owoItemGroup) {
			owoItemGroup.initialize();
		}
	}
}
