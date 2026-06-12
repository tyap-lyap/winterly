package ru.pinkgoosik.winterly.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.TypedEntityData;
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
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent;
import net.neoforged.neoforge.transfer.ResourceHandler;
import net.neoforged.neoforge.transfer.item.ItemResource;
import net.neoforged.neoforge.transfer.transaction.Transaction;
import org.jetbrains.annotations.Nullable;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;

public class GiftBoxBlock extends Block implements EntityBlock {
    public static final VoxelShape SHAPE = box(4, 0, 4, 12, 8, 12);
    private static final TagKey<Enchantment> GIFT_BOX_PICKUP = TagKey.create(Registries.ENCHANTMENT, Winterly.id("gift_box_pickup"));

    public GiftBoxBlock(Properties settings) {
        super(settings);
    }

    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getLevel().isClientSide()) return;
        Level level = event.getLevel();
        BlockPos pos = event.getPos();
        if (!(level.getBlockEntity(pos) instanceof GiftBoxBlockEntity be)) return;
        if (!(level.getBlockState(pos).getBlock() instanceof GiftBoxBlock)) return;

        ResourceHandler<ItemResource> inv = be.getInventory();
        Player player = event.getEntity();
        ItemStack stack = event.getItemStack();

        if (stack.isEmpty()) return;

        if (!be.isItemValid(stack)) return;

        int requested = player.isShiftKeyDown() ? stack.getCount() : 1;
        int inserted;
        try (var tx = Transaction.openRoot()) {
            inserted = insert(inv, ItemResource.of(stack), requested, tx);
            if (inserted > 0) {
                stack.shrink(inserted);
                tx.commit();
            }
        }

        if (inserted <= 0) return;

        be.setChanged();
        level.playSound(null, pos, SoundEvents.BUNDLE_INSERT, SoundSource.BLOCKS, 1, 1);
        event.setCancellationResult(InteractionResult.SUCCESS);
        event.setCanceled(true);
    }

    private static int insert(ResourceHandler<ItemResource> inventory, ItemResource resource, int amount, Transaction tx) {
        int inserted = 0;
        for (int slot = 0; slot < inventory.size(); slot++) {
            if (inventory.getResource(slot).equals(resource)) {
                inserted += inventory.insert(slot, resource, amount - inserted, tx);
                if (inserted >= amount) {
                    return inserted;
                }
            }
        }
        for (int slot = 0; slot < inventory.size(); slot++) {
            if (inventory.getResource(slot).isEmpty()) {
                inserted += inventory.insert(slot, resource, amount - inserted, tx);
                if (inserted >= amount) {
                    return inserted;
                }
            }
        }
        return inserted;
    }

    private static void dropContents(Level world, BlockPos pos, ResourceHandler<ItemResource> inventory) {
        boolean dropped = false;
        for (int slot = 0; slot < inventory.size(); slot++) {
            ItemStack stack = inventory.getResource(slot).toStack(inventory.getAmountAsInt(slot));
            if (!stack.isEmpty()) {
                dropped = true;
                popResource(world, pos, stack);
            }
        }
        if (!dropped) {
            popResource(world, pos, new ItemStack(world.getBlockState(pos).getBlock()));
        }
    }

    private static boolean hasItems(ResourceHandler<ItemResource> inventory) {
        for (int slot = 0; slot < inventory.size(); slot++) {
            if (!inventory.getResource(slot).isEmpty()) {
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
            var inv = be.getInventory();
            ItemStack handStack = player.getItemInHand(InteractionHand.MAIN_HAND);

            if (!EnchantmentHelper.hasTag(handStack, GIFT_BOX_PICKUP)) {
                dropContents(world, pos, inv);
            } else {
                ItemStack box = new ItemStack(this);
                if (hasItems(inv)) {
                    box.set(DataComponents.BLOCK_ENTITY_DATA, TypedEntityData.of(be.getType(), be.saveCustomOnly(world.registryAccess())));
                }
                popResource(world, pos, box);
            }
        }
        return super.playerWillDestroy(world, pos, state, player);
    }
}
