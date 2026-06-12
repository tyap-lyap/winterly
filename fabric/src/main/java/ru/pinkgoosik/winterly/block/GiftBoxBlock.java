package ru.pinkgoosik.winterly.block;

import net.fabricmc.fabric.api.transfer.v1.item.ItemVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.Storage;
import net.fabricmc.fabric.api.transfer.v1.storage.StorageView;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;

public class GiftBoxBlock extends Block implements EntityBlock {
	public static final VoxelShape SHAPE = Block.box(4, 0, 4, 12, 8, 12);
	private static final TagKey<Enchantment> GIFT_BOX_PICKUP = TagKey.create(Registries.ENCHANTMENT, Winterly.id("gift_box_pickup"));

	public GiftBoxBlock(BlockBehaviour.Properties properties) {
		super(properties);
	}

	public static boolean canStore(ItemStack stack) {
		if (stack.getItem() instanceof BlockItem blockItem) {
			return !(blockItem.getBlock() instanceof ShulkerBoxBlock) && !(blockItem.getBlock() instanceof GiftBoxBlock);
		}
		return true;
	}

	public static long getInsertableAmount(Level level, BlockPos pos, Player player, ItemStack held) {
		if (held.isEmpty() || !(level.getBlockEntity(pos) instanceof GiftBoxBlockEntity be)) {
			return 0;
		}

		long requested = player.isShiftKeyDown() ? held.getCount() : 1;
		try (var tx = Transaction.openOuter()) {
			return be.getInventory().insert(ItemVariant.of(held), requested, tx);
		}
	}

	public static InteractionResult onUse(Level level, BlockPos pos, Player player, ItemStack held) {
		if (!(level.getBlockEntity(pos) instanceof GiftBoxBlockEntity be)) return InteractionResult.PASS;
		if (held.isEmpty()) return InteractionResult.PASS;

		if (!canStore(held)) return InteractionResult.FAIL;

		long requested = player.isShiftKeyDown() ? held.getCount() : 1;
		try (var tx = Transaction.openOuter()) {
			long inserted = be.getInventory().insert(ItemVariant.of(held), requested, tx);
			if (inserted <= 0) {
				return InteractionResult.FAIL;
			}

			held.shrink((int) inserted);
			tx.commit();
			be.setChanged();
			level.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1, 1);
		}
		return InteractionResult.SUCCESS;
	}

	private static void dropContents(Level world, BlockPos pos, Storage<ItemVariant> inventory) {
		boolean dropped = false;
		for (StorageView<ItemVariant> view : inventory) {
			if (view.isResourceBlank()) {
				continue;
			}

			ItemStack stack = view.getResource().toStack((int) Math.min(view.getAmount(), Integer.MAX_VALUE));
			if (!stack.isEmpty()) {
				dropped = true;
				popResource(world, pos, stack);
			}
		}

		if (!dropped) {
			popResource(world, pos, new ItemStack(world.getBlockState(pos).getBlock()));
		}
	}

	private static boolean hasItems(Storage<ItemVariant> inventory) {
		for (StorageView<ItemVariant> view : inventory) {
			if (!view.isResourceBlank() && view.getAmount() > 0) {
				return true;
			}
		}
		return false;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GiftBoxBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return SHAPE;
	}

	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		if (!world.isClientSide() && world.getBlockEntity(pos) instanceof GiftBoxBlockEntity be) {
			ItemStack handStack = player.getItemInHand(InteractionHand.MAIN_HAND);

			if (!EnchantmentHelper.hasTag(handStack, GIFT_BOX_PICKUP)) {
				dropContents(world, pos, be.getInventory());
			} else {
				ItemStack box = new ItemStack(this);
				if (hasItems(be.getInventory())) {
					box.set(DataComponents.BLOCK_ENTITY_DATA, TypedEntityData.of(be.getType(), be.saveCustomOnly(world.registryAccess())));
				}
				popResource(world, pos, box);
			}
		}
		return super.playerWillDestroy(world, pos, state, player);
	}
}
