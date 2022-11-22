package winterly.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Language;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import winterly.data.GiftBoxDataStack;

import java.util.List;

@SuppressWarnings("deprecation")
public class GiftBoxBlock extends Block {
	public static final VoxelShape SHAPE = createCuboidShape(4, 0, 4, 12, 8, 12);

	public GiftBoxBlock(Settings settings) {
		super(settings);
	}

	@Override
	public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
		return GiftBoxBlock.SHAPE;
	}

	@Override
	public void onPlaced(World world, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
		super.onPlaced(world, pos, state, placer, stack);
		GiftBoxDataStack.stack.remove(pos);
		if(stack.getOrCreateNbt().contains("giftBoxData")) {
			var gift = GiftBoxDataStack.fromNbt(stack.getOrCreateNbt().getCompound("giftBoxData"));
			GiftBoxDataStack.stack.put(pos, gift);
		}
	}

	@Override
	public void onStacksDropped(BlockState state, ServerWorld world, BlockPos pos, ItemStack stack, boolean dropExperience) {
		super.onStacksDropped(state, world, pos, stack, dropExperience);
		var data = GiftBoxDataStack.get(pos);

		if(!EnchantmentHelper.get(stack).containsKey(Enchantments.SILK_TOUCH)) {
			if(data.stacks.isEmpty()) {
				dropStack(world, pos, new ItemStack(this.asItem()));
			}
			else {
				data.stacks.forEach(st -> dropStack(world, pos, st));
				GiftBoxDataStack.stack.remove(pos);
			}
		}
		else {
			ItemStack box = new ItemStack(this);

			if(!data.stacks.isEmpty()) {
				box.getOrCreateNbt().put("giftBoxData", data.toNbt());
			}
			dropStack(world, pos, box);
			GiftBoxDataStack.stack.remove(pos);
		}
	}

	@Override
	public PistonBehavior getPistonBehavior(BlockState state) {
		return PistonBehavior.DESTROY;
	}

	@Override
	public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
		if(!world.isClient) {
			var data = GiftBoxDataStack.get(pos);
			var stack = player.getStackInHand(hand);
			if(!stack.isEmpty() && data.stacks.size() < 3) {
				if(stack.getItem() instanceof BlockItem blockItem) {
					if(blockItem.getBlock() instanceof ShulkerBoxBlock || blockItem.getBlock() instanceof GiftBoxBlock) {
						return ActionResult.PASS;
					}
				}
				data.stacks.add(stack.copy());
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
			var gift = GiftBoxDataStack.fromNbt(stack.getOrCreateNbt().getCompound("giftBoxData"));

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
