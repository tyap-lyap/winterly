package ru.pinkgoosik.winterly.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.item.ItemStacksResourceHandler;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.registry.WinterlyBlockEntities;

public class GiftBoxBlockEntity extends BlockEntity {
	private final ItemStacksResourceHandler inventory;

	public GiftBoxBlockEntity(BlockPos pos, BlockState state) {
		super(WinterlyBlockEntities.GIFT_BOX.get(), pos, state);
		this.inventory = new ItemStacksResourceHandler(Math.max(1, Winterly.config().getGiftBoxCapacity())) {
			@Override
			protected void onContentsChanged(int index, ItemStack previousContents) {
				setChanged();
			}

			@Override
			public boolean isValid(int index, ItemResource resource) {
				return super.isValid(index, resource) && GiftBoxBlockEntity.this.isItemValid(resource.toStack());
			}
		};
	}

	public ItemStacksResourceHandler getInventory() {
		return inventory;
	}

	public boolean isItemValid(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			return !(blockItem.getBlock() instanceof ShulkerBoxBlock) && !(blockItem.getBlock() instanceof GiftBoxBlock);
		}
		return true;
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		var list = output.list("giftBoxData", ItemStack.OPTIONAL_CODEC);
		for (int slot = 0; slot < inventory.size(); slot++) {
			list.add(inventory.getResource(slot).toStack(inventory.getAmountAsInt(slot)));
		}
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		for (int slot = 0; slot < inventory.size(); slot++) {
			inventory.set(slot, ItemResource.EMPTY, 0);
		}
		var list = input.listOrEmpty("giftBoxData", ItemStack.OPTIONAL_CODEC);
		int slot = 0;
		for (var item : list) {
			if (slot >= inventory.size()) {
				break;
			}
			if (!item.isEmpty()) {
				inventory.set(slot, ItemResource.of(item), item.getCount());
			}
			slot++;
		}
	}
}
