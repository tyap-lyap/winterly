package winterly.neoforge.data;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.attachment.IAttachmentSerializer;

import java.util.LinkedHashMap;
import java.util.Map;

public class WorldData {
    public Map<BlockPos, Block> cachedFlowers = new LinkedHashMap<>();

    public static class WorldDataIAttachmentSerializer implements IAttachmentSerializer<Tag, WorldData> {

        @Override
        public WorldData read(Tag nbt) {
            WorldData data = new WorldData();

            var cachedFlowersNbt = ((CompoundTag) nbt).getCompound("cachedFlowers");

            if (!cachedFlowersNbt.isEmpty()) {
                int size = cachedFlowersNbt.getInt("size");

                for (int i = 0; i < size; i++) {
                    CompoundTag entry = cachedFlowersNbt.getCompound(String.valueOf(i));
                    BlockPos pos = new BlockPos(entry.getInt("x"), entry.getInt("y"), entry.getInt("z"));
                    var block = BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(entry.getString("block")));
                    block.ifPresent(bl -> data.cachedFlowers.put(pos, bl));
                }
            }

            return data;
        }

        @Override
        public Tag write(WorldData data) {
            CompoundTag cachedFlowersNbt = new CompoundTag();
            cachedFlowersNbt.putInt("size", data.cachedFlowers.size());
            int index = -1;

            for (var entry : data.cachedFlowers.entrySet()) {
                index++;
                CompoundTag entryNbt = new CompoundTag();
                entryNbt.putInt("x", entry.getKey().getX());
                entryNbt.putInt("y", entry.getKey().getY());
                entryNbt.putInt("z", entry.getKey().getZ());
                entryNbt.putString("block", BuiltInRegistries.BLOCK.getKey(entry.getValue()).toString());
                cachedFlowersNbt.put(String.valueOf(index), entryNbt);
            }

            CompoundTag nbt = new CompoundTag();
            nbt.put("cachedFlowers", cachedFlowersNbt);

            return nbt;
        }
    }
}
