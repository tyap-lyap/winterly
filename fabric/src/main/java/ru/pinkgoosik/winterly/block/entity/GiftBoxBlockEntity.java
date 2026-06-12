package ru.pinkgoosik.winterly.block.entity;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.CombinedStorage;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientGamePacketListener;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.registry.WinterlyFabricBlockEntities;

import java.util.ArrayList;
import java.util.List;

public class GiftBoxBlockEntity extends BlockEntity {
	private final List<SingleVariantStorage<ItemVariant>> slots = new ArrayList<>();
	private final CombinedStorage<ItemVariant, SingleVariantStorage<ItemVariant>> combined;

	public GiftBoxBlockEntity(BlockPos pos, BlockState state) {
		super(WinterlyFabricBlockEntities.GIFT_BOX.get(), pos, state);
		int capacity = Winterly.config().getGiftBoxCapacity();
		for (int i = 0; i < capacity; i++) {
			slots.add(createSlot());
		}
		this.combined = new CombinedStorage<>(List.copyOf(slots));
	}

	private SingleVariantStorage<ItemVariant> createSlot() {
		return new SingleVariantStorage<>() {
			@Override
			protected ItemVariant getBlankVariant() {
				return ItemVariant.blank();
			}

			@Override
			protected long getCapacity(ItemVariant variant) {
				return variant.getItem().getDefaultMaxStackSize();
			}

			@Override
			protected boolean canInsert(ItemVariant variant) {
				return isItemValid(variant.toStack());
			}

			@Override
			protected void onFinalCommit() {
				setChanged();
				if (level != null && !level.isClientSide()) {
					level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), Block.UPDATE_CLIENTS);
				}
			}
		};
	}

	public CombinedStorage<ItemVariant, SingleVariantStorage<ItemVariant>> getInventory() {
		return combined;
	}

	public boolean isItemValid(ItemStack stack) {
		return GiftBoxBlock.canStore(stack);
	}

	@Override
	public Packet<ClientGamePacketListener> getUpdatePacket() {
		return ClientboundBlockEntityDataPacket.create(this);
	}

	@Override
	public CompoundTag getUpdateTag(HolderLookup.Provider registries) {
		return saveWithoutMetadata(registries);
	}

	@Override
	protected void saveAdditional(ValueOutput output) {
		super.saveAdditional(output);
		var list = output.list("giftBoxData", ItemStack.OPTIONAL_CODEC);
		for (var slot : slots) {
			list.add(slot.variant.toStack((int) slot.amount));
		}
	}

	@Override
	protected void loadAdditional(ValueInput input) {
		super.loadAdditional(input);
		for (var slot : slots) {
			slot.variant = ItemVariant.blank();
			slot.amount = 0;
		}
		var list = input.listOrEmpty("giftBoxData", ItemStack.OPTIONAL_CODEC);
		int i = 0;
		for (var item : list) {
			if (i >= slots.size()) break;
			if (!item.isEmpty()) {
				slots.get(i).variant = ItemVariant.of(item);
				slots.get(i).amount = item.getCount();
			}
			i++;
		}
	}
}
