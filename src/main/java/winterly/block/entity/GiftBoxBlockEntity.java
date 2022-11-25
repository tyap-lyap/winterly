package winterly.block.entity;

import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.math.BlockPos;
import winterly.data.GiftBoxData;
import winterly.registry.WinterlyBlockEntities;

import java.util.ArrayList;

public class GiftBoxBlockEntity extends BlockEntity {
	public ArrayList<ItemStack> stacks = new ArrayList<>();

	public GiftBoxBlockEntity(BlockPos pos, BlockState state) {
		super(WinterlyBlockEntities.GIFT_BOX_BLOCK_ENTITY, pos, state);
	}

	@Override
	public void writeNbt(NbtCompound nbt) {
		super.writeNbt(nbt);
		nbt.put("giftBoxData", GiftBoxData.toNbt(this));
	}

	@Override
	public void readNbt(NbtCompound nbt) {
		super.readNbt(nbt);
		var data = GiftBoxData.fromNbt(nbt.getCompound("giftBoxData"));
		this.stacks = data.stacks;
	}

}
