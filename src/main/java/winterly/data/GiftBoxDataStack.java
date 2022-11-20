package winterly.data;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GiftBoxDataStack {

	public static Map<BlockPos, GiftBoxData> stack = new LinkedHashMap<>();

	public static GiftBoxData get(BlockPos pos) {
		if(stack.containsKey(pos)) {
			return stack.get(pos);
		}
		else {
			GiftBoxData gift = new GiftBoxData();
			stack.put(pos, gift);
			return gift;
		}
	}

	public static GiftBoxData fromNbt(NbtCompound nbt) {
		GiftBoxData gift = new GiftBoxData();

		int size = nbt.getInt("size");
		for(int i = 0; i < size; i++) {
			NbtCompound entryNbt = nbt.getCompound(String.valueOf(i));

			var item = Registry.ITEM.getOrEmpty(new Identifier(entryNbt.getString("item")));
			if(item.isPresent()) {
				ItemStack temp = new ItemStack(item.get(), entryNbt.getInt("count"));
				temp.setNbt((NbtCompound)entryNbt.get("nbt"));
				gift.stacks.add(temp);
			}
		}

		return gift;
	}

	public static class GiftBoxData {
		public ArrayList<ItemStack> stacks = new ArrayList<>();

		public NbtCompound toNbt() {
			NbtCompound nbt = new NbtCompound();
			nbt.putInt("size", this.stacks.size());

			for(int i = 0; i < this.stacks.size(); i++) {
				ItemStack stack = this.stacks.get(i);
				NbtCompound entryNbt = new NbtCompound();
				entryNbt.putString("item", Registry.ITEM.getId(stack.getItem()).toString());
				entryNbt.put("nbt", stack.getOrCreateNbt());
				entryNbt.putInt("count", stack.getCount());

				nbt.put(Integer.toString(i), entryNbt);
			}
			return nbt;
		}
	}
}
