package winterly.data;

import winterly.block.entity.GiftBoxBlockEntity;

import java.util.ArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import winterly.block.entity.GiftBoxBlockEntityData;

public class GiftBoxData {
	public ArrayList<ItemStack> stacks = new ArrayList<>();

	public static GiftBoxData fromNbt(CompoundTag nbt) {
		GiftBoxData gift = new GiftBoxData();

		int size = nbt.getInt("size");
		for(int i = 0; i < size; i++) {
			CompoundTag entryNbt = nbt.getCompound(String.valueOf(i));

			var item = BuiltInRegistries.ITEM.getOptional(new ResourceLocation(entryNbt.getString("item")));
			if(item.isPresent()) {
				ItemStack temp = new ItemStack(item.get(), entryNbt.getInt("count"));
				temp.setTag((CompoundTag)entryNbt.get("nbt"));
				gift.stacks.add(temp);
			}
		}

		return gift;
	}

	public static CompoundTag toNbt(GiftBoxBlockEntityData entity) {
		CompoundTag nbt = new CompoundTag();
		nbt.putInt("size", entity.getStacks().size());

		for(int i = 0; i < entity.getStacks().size(); i++) {
			ItemStack stack = entity.getStacks().get(i);
			CompoundTag entryNbt = new CompoundTag();
			entryNbt.putString("item", BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
			entryNbt.put("nbt", stack.getOrCreateTag());
			entryNbt.putInt("count", stack.getCount());

			nbt.put(Integer.toString(i), entryNbt);
		}
		return nbt;
	}
}
