package ru.pinkgoosik.winterly.item;

import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import ru.pinkgoosik.winterly.compat.WinterlyCuriosIntegration;

import java.util.function.Consumer;

@SuppressWarnings("NullableProblems")
public class ScarfItem extends CommonScarfItem {

	public ScarfItem(Properties settings, String color) {
		super(settings, color);

		if (ModListCheck.isCuriosLoaded()) {
			WinterlyCuriosIntegration.registerCurio(this);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
		if (!ModListCheck.isCuriosLoaded()) {
			Language lang = Language.getInstance();
			String key = "tip.winterly.requires_curios.";

			for (int i = 0; i <= 32; i++) {
				if (lang.has(key + i)) {
					builder.accept(Component.translatable(key + i).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
				}
				if (!lang.has(key + (i + 1))) {
					break;
				}
			}
		}
	}
}
