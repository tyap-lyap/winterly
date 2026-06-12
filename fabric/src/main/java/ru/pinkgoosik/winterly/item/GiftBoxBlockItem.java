package ru.pinkgoosik.winterly.item;

import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.level.block.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class GiftBoxBlockItem extends BlockItem {
	public GiftBoxBlockItem(Block block, Properties properties) {
		super(block, properties);
	}

	@Override
	public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> builder, TooltipFlag flag) {
		super.appendHoverText(stack, context, display, builder, flag);
		TypedEntityData<?> data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
		if (data != null) {
			CompoundTag tag = data.getUnsafe();
			if (tag.contains("giftBoxData")) {
				tag.getList("giftBoxData").ifPresent(items -> {
					List<ItemStack> stacks = new ArrayList<>();
					for (int i = 0; i < items.size(); i++) {
						items.getCompound(i)
							.flatMap(entry -> ItemStack.OPTIONAL_CODEC.parse(NbtOps.INSTANCE, entry).result())
							.filter(st -> !st.isEmpty() && !st.is(Items.AIR))
							.ifPresent(stacks::add);
					}
					if (!stacks.isEmpty()) {
						stacks.forEach(st -> {
							String name = Language.getInstance().getOrDefault(st.getItem().getDescriptionId());
							builder.accept(Component.literal("- " + name + " x" + st.getCount()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
						});
                    }
				});
			}
		}
		Language lang = Language.getInstance();
		String key = "description.winterly.gift_box.";
		for (int i = 0; i <= 32; i++) {
			if (lang.has(key + i)) {
				builder.accept(Component.translatable(key + i).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
			}
			if (!lang.has(key + (i + 1))) break;
		}
	}
}
