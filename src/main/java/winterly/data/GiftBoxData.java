package winterly.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import winterly.block.entity.GiftBoxBlockEntity;

import java.util.ArrayList;

public class GiftBoxData {
	public ArrayList<ItemStack> stacks = new ArrayList<>();

	public static GiftBoxData fromNbt(NbtCompound nbt) {
		GiftBoxData gift = new GiftBoxData();

		int size = nbt.getInt("size");
		for(int i = 0; i < size; i++) {
			NbtCompound entryNbt = nbt.getCompound(String.valueOf(i));

			var item = Registries.ITEM.getOrEmpty(new Identifier(entryNbt.getString("item")));
			if(item.isPresent()) {
				ItemStack temp = new ItemStack(item.get(), entryNbt.getInt("count"));
				temp.setNbt((NbtCompound)entryNbt.get("nbt"));
				gift.stacks.add(temp);
			}
		}

		return gift;
	}

	public static NbtCompound toNbt(GiftBoxBlockEntity entity) {
		NbtCompound nbt = new NbtCompound();
		nbt.putInt("size", entity.stacks.size());

		for(int i = 0; i < entity.stacks.size(); i++) {
			ItemStack stack = entity.stacks.get(i);
			NbtCompound entryNbt = new NbtCompound();
			entryNbt.putString("item", Registries.ITEM.getId(stack.getItem()).toString());
			entryNbt.put("nbt", stack.getOrCreateNbt());
			entryNbt.putInt("count", stack.getCount());

			nbt.put(Integer.toString(i), entryNbt);
		}
		return nbt;
	}
}
