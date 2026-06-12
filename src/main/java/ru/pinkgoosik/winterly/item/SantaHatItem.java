package ru.pinkgoosik.winterly.item;

import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.ModList;
import ru.pinkgoosik.winterly.compat.WinterlyCuriosIntegration;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class SantaHatItem extends CommonSantaHatItem {

	public SantaHatItem(Properties settings, String color) {
		super(settings, color);

		if (ModList.get().isLoaded("curios")) {
			WinterlyCuriosIntegration.registerCurio(this);
		}
	}

	@Override
	public void appendHoverText(ItemStack stack, @javax.annotation.Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
		if (!ModList.get().isLoaded("curios")) {
			Language lang = Language.getInstance();
			String key = "tip.winterly.requires_curios.";

			for (int i = 0; i <= 32; i++) {
				if (lang.has(key + i)) {
					tooltip.add(Component.translatable(key + i).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
				}
				if (!lang.has(key + (i + 1))) {
					break;
				}
			}
		}
	}
}
