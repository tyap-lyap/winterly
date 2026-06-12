package ru.pinkgoosik.winterly.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.registry.WinterlyBlockEntities;

public class GiftBoxBlockEntity extends BlockEntity implements GiftBoxBlockEntityData {
	private final ItemStackHandler inventory;
	private final LazyOptional<IItemHandler> holder;

	public GiftBoxBlockEntity(BlockPos pos, BlockState state) {
		super(WinterlyBlockEntities.GIFT_BOX.get(), pos, state);
		this.inventory = new ItemStackHandler(9) {
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}

			@Override
			public boolean isItemValid(int slot, ItemStack stack) {
				if (stack.getItem() instanceof BlockItem blockItem) {
					return !(blockItem.getBlock() instanceof ShulkerBoxBlock) && !(blockItem.getBlock() instanceof GiftBoxBlock);
				}
				return true;
			}
		};
		this.holder = LazyOptional.of(() -> inventory);
	}

	public ItemStackHandler getInventory() {
		return inventory;
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
		if (cap == ForgeCapabilities.ITEM_HANDLER) {
			return holder.cast();
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void invalidateCaps() {
		super.invalidateCaps();
		holder.invalidate();
	}

	@Override
	public void saveAdditional(CompoundTag tag) {
		super.saveAdditional(tag);
		tag.put("giftBoxData", inventory.serializeNBT());
	}

	@Override
	public void load(CompoundTag tag) {
		super.load(tag);
		if (tag.contains("giftBoxData")) {
			inventory.deserializeNBT(tag.getCompound("giftBoxData"));
		}
	}
}
