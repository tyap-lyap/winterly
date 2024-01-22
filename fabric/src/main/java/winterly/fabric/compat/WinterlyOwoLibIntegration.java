package winterly.fabric.compat;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import winterly.Winterly;
import winterly.fabric.WinterlyFabric;
import winterly.registry.CommonWinterlyBlocks;

public class WinterlyOwoLibIntegration {
	public static final ResourceLocation ICONS_TEXTURE = Winterly.id("textures/gui/icons.png");

	public static CreativeModeTab createItemGroup() {
		return OwoItemGroup.builder(Winterly.id("items"), () -> Icon.of(CommonWinterlyBlocks.SNOWGUY))
				.initializer(group -> {
					group.addButton(ItemGroupButton.link(group, Icon.of(ICONS_TEXTURE, 0, 0, 64, 64), "discord", "https://discord.gg/DcemWeskeZ"));
//					if(UpdateChecker.check()) {
//						group.addButton(ItemGroupButton.link(group, Icon.of(ICONS_TEXTURE, 16, 0, 64, 64), "updated", "https://modrinth.com/mod/winterly"));
//					}
				}).build();
	}

	public static void initItemGroup() {
		if(WinterlyFabric.itemGroup instanceof OwoItemGroup owoItemGroup) {
			owoItemGroup.initialize();
		}
	}
}
