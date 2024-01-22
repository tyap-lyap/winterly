package winterly.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import java.util.List;

public class CommonSantaHatItem extends Item {
    public final String color;

    public CommonSantaHatItem(Item.Properties settings, String color) {
        super(settings);
        this.color = color;
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level world, List<Component> tooltip, TooltipFlag context) {
        tooltip.add(Component.translatable("tag.winterly.cosmetic").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.nullToEmpty(" "));
        super.appendHoverText(stack, world, tooltip, context);
    }
}
