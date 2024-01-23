package winterly.fabric.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.ChatFormatting;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import winterly.fabric.compat.WinterlyTrinketsIntegration;
import winterly.item.CommonSantaHatItem;

import java.util.List;

public class SantaHatItem extends CommonSantaHatItem {

    public SantaHatItem(Item.Properties settings, String color) {
        super(settings, color);
		if(FabricLoader.getInstance().isModLoaded("trinkets")) {
			WinterlyTrinketsIntegration.registerTrinket(this);
		}
    }

	@Environment(EnvType.CLIENT)
	@Override
	public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
		tooltip.add(Component.translatable("tag.winterly.cosmetic").withStyle(ChatFormatting.GRAY));
		tooltip.add(Component.nullToEmpty(" "));

		if(!FabricLoader.getInstance().isModLoaded("trinkets")) {
			Language lang = Language.getInstance();
			String key = "tip.winterly.requires_trinkets.";

			for(int i = 0; i <= 32; i++) {
				if(lang.has(key + i)) {
					tooltip.add(Component.translatable(key + i).toFlatList(Style.EMPTY.withColor(ChatFormatting.GRAY)).get(0));
				}
				if(!lang.has(key + (i + 1))) {
					break;
				}
			}
		}
	}
}
