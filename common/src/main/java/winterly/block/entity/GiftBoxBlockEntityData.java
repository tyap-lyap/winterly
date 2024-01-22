package winterly.block.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;

public interface GiftBoxBlockEntityData {
    ArrayList<ItemStack> getStacks();
    void saveAdditional(CompoundTag nbt);
    void load(CompoundTag nbt);
}
