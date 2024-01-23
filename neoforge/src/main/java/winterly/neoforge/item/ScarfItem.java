package winterly.neoforge.item;

import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;
import winterly.item.CommonScarfItem;
import winterly.neoforge.compat.WinterlyCuriosIntegration;

import java.util.List;

public class ScarfItem extends CommonScarfItem {

    public ScarfItem(Item.Properties settings, String color) {
        super(settings, color);

		if(ModList.get().isLoaded("curios")) {
			WinterlyCuriosIntegration.registerCurio(this);
		}
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		if(!ModList.get().isLoaded("curios")) {
			tooltip.add(Component.translatable("tag.winterly.cosmetic").withStyle(ChatFormatting.GRAY));
			tooltip.add(Component.nullToEmpty(" "));
			Language lang = Language.getInstance();
			String key = "tip.winterly.requires_curios.";

			for(int i = 0; i <= 32; i++) {
				if(lang.has(key + i)) {
					tooltip.add(Component.translatable(key + i).toFlatList(Style.EMPTY.withColor(ChatFormatting.GRAY)).get(0));
				}
				if(!lang.has(key + (i + 1))) {
					break;
				}
			}
		}
		else {
			tooltip.add(Component.nullToEmpty(" "));
			tooltip.add(Component.translatable("tag.winterly.cosmetic").withStyle(ChatFormatting.GRAY));
		}
    }
}
