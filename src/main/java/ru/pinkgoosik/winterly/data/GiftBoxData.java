package ru.pinkgoosik.winterly.data;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

import java.util.ArrayList;
import java.util.List;

public class GiftBoxData {

	public static List<ItemStack> readItems(CompoundTag tag) {
		List<ItemStack> list = new ArrayList<>();
		ListTag items = tag.getList("Items", Tag.TAG_COMPOUND);
		for (int i = 0; i < items.size(); i++) {
			CompoundTag entry = items.getCompound(i);
			ItemStack stack = ItemStack.of(entry);
			if (!stack.isEmpty()) {
				list.add(stack);
			}
		}
		return list;
	}

	public static CompoundTag writeItems(ItemStackHandler handler) {
		return handler.serializeNBT();
	}
}
