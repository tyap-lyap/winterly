package ru.pinkgoosik.winterly.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.items.IItemHandler;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;
import ru.pinkgoosik.winterly.data.GiftBoxData;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class GiftBoxBlock extends Block implements EntityBlock {
	public static final VoxelShape SHAPE = box(4, 0, 4, 12, 8, 12);
	private static final TagKey<Enchantment> GIFT_BOX_PICKUP = TagKey.create(Registries.ENCHANTMENT, new ResourceLocation(Winterly.MOD_ID, "gift_box_pickup"));

	public GiftBoxBlock(Properties settings) {
		super(settings);
	}

	public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
		if (event.getLevel().isClientSide) return;
		Level level = event.getLevel();
		BlockPos pos = event.getPos();
		if (!(level.getBlockEntity(pos) instanceof GiftBoxBlockEntity be)) return;
		if (!(level.getBlockState(pos).getBlock() instanceof GiftBoxBlock)) return;

		IItemHandler inv = be.getInventory();
		Player player = event.getEntity();
		ItemStack stack = event.getItemStack();
		if (stack.isEmpty()) return;

		int capacity = Winterly.config().getGiftBoxCapacity();
		boolean inserted;

		if (player.isShiftKeyDown()) {
			ItemStack toInsert = stack.copy();
			int before = toInsert.getCount();
			ItemStack remaining = tryInsert(inv, toInsert, capacity);
			player.setItemInHand(event.getHand(), remaining);
			inserted = remaining.getCount() < before;
		} else {
			ItemStack single = stack.copyWithCount(1);
			int before = single.getCount();
			ItemStack remaining = tryInsert(inv, single, capacity);
			inserted = remaining.getCount() < before;
			if (inserted) {
				stack.shrink(1);
			}
		}

		if (!inserted) return;

		be.setChanged();
		level.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1, 1);
		event.setCancellationResult(InteractionResult.SUCCESS);
		event.setCanceled(true);
	}

	private static ItemStack tryInsert(IItemHandler inv, ItemStack stack, int capacity) {
		for (int i = 0; i < inv.getSlots(); i++) {
			ItemStack inSlot = inv.getStackInSlot(i);
			if (!inSlot.isEmpty() && ItemStack.isSameItemSameTags(inSlot, stack)) {
				stack = inv.insertItem(i, stack, false);
				if (stack.isEmpty()) return ItemStack.EMPTY;
			}
		}
		int usedSlots = 0;
		for (int i = 0; i < inv.getSlots(); i++) {
			if (!inv.getStackInSlot(i).isEmpty()) usedSlots++;
		}
		if (usedSlots >= capacity) return stack;
		for (int i = 0; i < inv.getSlots(); i++) {
			if (inv.getStackInSlot(i).isEmpty()) {
				stack = inv.insertItem(i, stack, false);

				if (stack.isEmpty()) {
					return ItemStack.EMPTY;
				}
			}
		}
		return stack;
	}

	@Nullable
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new GiftBoxBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return GiftBoxBlock.SHAPE;
	}

	@Override
	public void setPlacedBy(Level world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.setPlacedBy(world, pos, state, placer, stack);

		if (world.getBlockEntity(pos) instanceof GiftBoxBlockEntity be && placer != null) {
			CompoundTag stackTag = stack.getTag();
			if (stackTag != null && stackTag.contains("BlockEntityData")) {
				be.load(stackTag.getCompound("BlockEntityData"));
			}
		}
	}

	@Override
	public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(world, pos, state, player);

		if (!world.isClientSide && world.getBlockEntity(pos) instanceof GiftBoxBlockEntity be) {
			IItemHandler inv = be.getInventory();
			ItemStack handStack = player.getItemInHand(InteractionHand.MAIN_HAND);

			if (!hasGiftBoxPickup(handStack, world)) {
				boolean hasItems = false;
				for (int i = 0; i < inv.getSlots(); i++) {
					ItemStack st = inv.getStackInSlot(i);
					if (!st.isEmpty()) {
						hasItems = true;
						popResource(world, pos, st);
					}
				}
				if (!hasItems) {
					popResource(world, pos, new ItemStack(this));
				}
			} else {
				ItemStack box = new ItemStack(this);
				boolean hasItems = false;
				for (int i = 0; i < inv.getSlots(); i++) {
					if (!inv.getStackInSlot(i).isEmpty()) {
						hasItems = true;
						break;
					}
				}
				if (hasItems) {
					CompoundTag nbt = new CompoundTag();
					be.saveAdditional(nbt);
					box.addTagElement("BlockEntityData", nbt);
				}
				popResource(world, pos, box);
			}
		}
	}

	private boolean hasGiftBoxPickup(ItemStack stack, Level level) {
		var registry = level.registryAccess().registryOrThrow(Registries.ENCHANTMENT);
		return EnchantmentHelper.getEnchantments(stack)
			.keySet()
			.stream()
			.anyMatch(enchantment -> {
				var key = registry.getResourceKey(enchantment);
				return key.isPresent() && registry.getHolderOrThrow(key.get()).is(GIFT_BOX_PICKUP);
			});
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flag) {
		super.appendHoverText(stack, level, tooltip, flag);

		CompoundTag stackTag = stack.getTag();
		if (stackTag != null && stackTag.contains("BlockEntityData")) {
			CompoundTag data = stackTag.getCompound("BlockEntityData");
			if (data.contains("giftBoxData")) {
				List<ItemStack> items = GiftBoxData.readItems(data.getCompound("giftBoxData"));
				if (!items.isEmpty()) {
					items.forEach(st -> {
						String name = Language.getInstance().getOrDefault(st.getDescriptionId());
						tooltip.add(Component.literal("- " + name + " x" + st.getCount()).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
					});
					return;
				}
			}
		}

		Language lang = Language.getInstance();
		String key = "description.winterly.gift_box.";
		for (int i = 0; i <= 32; i++) {
			if (lang.has(key + i)) {
				tooltip.add(Component.translatable(key + i).setStyle(Style.EMPTY.applyFormat(ChatFormatting.GRAY)));
			}
			if (!lang.has(key + (i + 1))) {
				break;
			}
		}
	}
}
