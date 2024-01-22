package winterly.neoforge.data;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.Nullable;
import winterly.Winterly;
import winterly.data.CachedFlowers;

import java.util.function.Supplier;

public class WinterlyDataAttachments {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Winterly.MOD_ID);

    private static final Supplier<AttachmentType<WorldData>> WORLD_DATA = ATTACHMENT_TYPES.register(
            "world_data", () -> AttachmentType.builder(WorldData::new).serialize(new WorldData.WorldDataIAttachmentSerializer()).build()
    );

    public static void init(IEventBus eventBus) {

        CachedFlowers.instance = new CachedFlowers() {
            @Override
            public @Nullable Block getFlowerImpl(Level world, BlockPos pos) {
                return world.getData(WORLD_DATA).cachedFlowers.get(pos);
            }

            @Override
            public void cacheFlowerImpl(Level world, BlockPos pos, Block flower) {
                world.getData(WORLD_DATA).cachedFlowers.put(pos, flower);
            }
        };

        ATTACHMENT_TYPES.register(eventBus);
    }
}
