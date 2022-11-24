package winterly.compat;

import io.wispforest.owo.itemgroup.Icon;
import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import winterly.Winterly;
import winterly.registry.WinterlyBlocks;
import winterly.registry.WinterlyItems;

public class WinterlyOwoLibIntegration {
	public static final Identifier ICONS_TEXTURE = Winterly.id("textures/gui/icons.png");

	public static ItemGroup createItemGroup() {
		return new OwoItemGroup(Winterly.id("items")) {
			@Override
			protected void setup() {
				this.addButton(ItemGroupButton.link(Icon.of(ICONS_TEXTURE, 0, 0, 64, 64), "discord", "https://discord.gg/DcemWeskeZ"));
			}

			@Override
			public ItemStack createIcon() {
				return WinterlyBlocks.SNOWGUY.asItem().getDefaultStack();
			}

			@Override
			public void appendStacks(DefaultedList<ItemStack> stacks) {
				WinterlyItems.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
				WinterlyBlocks.ITEMS.forEach((id, item) -> stacks.add(item.getDefaultStack()));
			}
		};
	}

	public static void initItemGroup() {
		if(Winterly.itemGroup instanceof OwoItemGroup owoItemGroup) {
			owoItemGroup.initialize();
		}
	}
}
