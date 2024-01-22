package winterly.block.entity;

import winterly.data.GiftBoxData;
import winterly.registry.CommonWinterlyBlockEntities;

import java.util.ArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class GiftBoxBlockEntity extends BlockEntity implements GiftBoxBlockEntityData {
    public ArrayList<ItemStack> stacks = new ArrayList<>();

    public GiftBoxBlockEntity(BlockPos pos, BlockState state) {
        super(CommonWinterlyBlockEntities.GIFT_BOX_BLOCK_ENTITY, pos, state);
    }

    @Override
    public ArrayList<ItemStack> getStacks() {
        return this.stacks;
    }

    @Override
    public void saveAdditional(CompoundTag nbt) {
        super.saveAdditional(nbt);
        nbt.put("giftBoxData", GiftBoxData.toNbt(this));
    }

    @Override
    public void load(CompoundTag nbt) {
        super.load(nbt);
        var data = GiftBoxData.fromNbt(nbt.getCompound("giftBoxData"));
        this.stacks = data.stacks;
    }

}
