package winterly.block;

import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.locale.Language;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;
import winterly.Winterly;
import winterly.block.entity.GiftBoxBlockEntity;
import winterly.block.entity.GiftBoxBlockEntityData;
import winterly.data.GiftBoxData;

import java.util.List;

@SuppressWarnings("deprecation")
public class GiftBoxBlock extends Block implements EntityBlock {
	public static final VoxelShape SHAPE = box(4, 0, 4, 12, 8, 12);

	public GiftBoxBlock(Properties settings) {
		super(settings);
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

		if(world.getBlockEntity(pos) instanceof GiftBoxBlockEntityData entity) {
			entity.load(stack.getOrCreateTag());
		}
	}

	@Override
	public BlockState playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
		super.playerWillDestroy(world, pos, state, player);

		if(!world.isClientSide && world.getBlockEntity(pos) instanceof GiftBoxBlockEntityData entity) {
			ItemStack stack = player.getItemInHand(InteractionHand.MAIN_HAND);
			if(!EnchantmentHelper.getEnchantments(stack).containsKey(Enchantments.SILK_TOUCH)) {
				if(entity.getStacks().isEmpty()) {
					popResource(world, pos, new ItemStack(this.asItem()));
				}
				else {
					entity.getStacks().forEach(st -> popResource(world, pos, st));
				}
			}
			else {
				ItemStack box = new ItemStack(this);

				if(!entity.getStacks().isEmpty()) {
					var nbt = new CompoundTag();
					entity.saveAdditional(nbt);
					box.setTag(nbt);
				}
				popResource(world, pos, box);
			}
		}
		return state;
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
		if(!world.isClientSide && world.getBlockEntity(pos) instanceof GiftBoxBlockEntityData entity) {
			var stack = player.getItemInHand(hand);

			if(!stack.isEmpty() && entity.getStacks().size() < Winterly.config.getGiftBoxCapacity()) {
				if(stack.getItem() instanceof BlockItem blockItem) {
					if(blockItem.getBlock() instanceof ShulkerBoxBlock || blockItem.getBlock() instanceof GiftBoxBlock) {
						return InteractionResult.PASS;
					}
				}
				entity.getStacks().add(stack.copy());
				player.setItemInHand(hand, ItemStack.EMPTY);
				world.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1, 1);
				return InteractionResult.SUCCESS;
			}
		}
		return super.use(state, world, pos, player, hand, hit);
	}

	@Override
	public void appendHoverText(ItemStack stack, @Nullable BlockGetter world, List<Component> tooltip, TooltipFlag options) {
		super.appendHoverText(stack, world, tooltip, options);

		if(stack.getOrCreateTag().contains("giftBoxData")) {
			var gift = GiftBoxData.fromNbt(stack.getOrCreateTag().getCompound("giftBoxData"));

			gift.stacks.forEach(st -> {
				String name = Language.getInstance().getOrDefault(st.getDescriptionId());
				tooltip.add(Component.nullToEmpty("- " + name + " x" + st.getCount()).toFlatList(Style.EMPTY.withColor(ChatFormatting.GRAY)).get(0));
			});
		}
		else {
			Language lang = Language.getInstance();
			String key = "description.winterly.gift_box.";

			for(int i = 0; i <= 32; i++) {
				if(lang.has(key + i)) {
					tooltip.add(Component.translatable(key + i).toFlatList(Style.EMPTY.withColor(ChatFormatting.GRAY)).get(0));
				}
				if(!lang.has(key + (i + 1))) {
					break;
				}
			}
		}
	}
}
