package winterly.block;

import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import winterly.Winterly;
import winterly.block.entity.GiftBoxBlockEntity;
import winterly.data.GiftBoxData;

import java.util.List;

@SuppressWarnings("deprecation")
public class GiftBoxBlock extends Block implements BlockEntityProvider {
	public static final VoxelShape SHAPE = createCuboidShape(4, 0, 4, 12, 8, 12);

	public GiftBoxBlock(Settings settings) {
		super(settings);
	}

	@Nullable
	@Override
	public BlockEntity createBlockEntity(BlockPos pos, BlockState state) {
		return new GiftBoxBlockEntity(pos, state);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return GiftBoxBlock.SHAPE;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onPlaced(world, pos, state, placer, stack);

		if(world.getBlockEntity(pos) instanceof GiftBoxBlockEntity entity) {
			entity.readNbt(stack.getOrCreateNbt());
		}
	}

	@Override
	public BlockState onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
		super.onBreak(world, pos, state, player);

		if(!world.isClient && world.getBlockEntity(pos) instanceof GiftBoxBlockEntity entity) {
			ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);
			if(!EnchantmentHelper.get(stack).containsKey(Enchantments.SILK_TOUCH)) {
				if(entity.stacks.isEmpty()) {
					dropStack(world, pos, new ItemStack(this.asItem()));
				}
				else {
					entity.stacks.forEach(st -> dropStack(world, pos, st));
				}
			}
			else {
				ItemStack box = new ItemStack(this);

				if(!entity.stacks.isEmpty()) {
					var nbt = new NbtCompound();
					entity.writeNbt(nbt);
					box.setNbt(nbt);
				}
				dropStack(world, pos, box);
			}
		}
		return state;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(!world.isClient && world.getBlockEntity(pos) instanceof GiftBoxBlockEntity entity) {
			var stack = player.getStackInHand(hand);

			if(!stack.isEmpty() && entity.stacks.size() < Winterly.config.getGiftBoxCapacity()) {
				if(stack.getItem() instanceof BlockItem blockItem) {
					if(blockItem.getBlock() instanceof ShulkerBoxBlock || blockItem.getBlock() instanceof GiftBoxBlock) {
						return ActionResult.PASS;
					}
				}
				entity.stacks.add(stack.copy());
				player.setStackInHand(hand, ItemStack.EMPTY);
				world.playSound(null, pos, SoundEvents.ITEM_BUNDLE_INSERT, SoundCategory.BLOCKS, 1, 1);
				return ActionResult.SUCCESS;
			}
		}
		return super.onUse(state, world, pos, player, hand, hit);
	}

	@Override
	public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
		super.appendTooltip(stack, world, tooltip, options);

		if(stack.getOrCreateNbt().contains("giftBoxData")) {
			var gift = GiftBoxData.fromNbt(stack.getOrCreateNbt().getCompound("giftBoxData"));

			gift.stacks.forEach(st -> {
				String name = Language.getInstance().get(st.getTranslationKey());
				tooltip.add(Text.of("- " + name + " x" + st.getCount()).getWithStyle(Style.EMPTY.withColor(Formatting.GRAY)).get(0));
			});
		}
		else {
			Language lang = Language.getInstance();
			String key = "description.winterly.gift_box.";

			for(int i = 0; i <= 32; i++) {
				if(lang.hasTranslation(key + i)) {
					tooltip.add(Text.translatable(key + i).getWithStyle(Style.EMPTY.withColor(Formatting.GRAY)).get(0));
				}
				if(!lang.hasTranslation(key + (i + 1))) {
					break;
				}
			}
		}
	}
}
