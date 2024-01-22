package winterly.neoforge.registry;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import winterly.block.GiftBoxBlock;
import winterly.block.entity.GiftBoxBlockEntity;
import winterly.registry.CommonWinterlyBlocks;

import java.util.ArrayList;

import static winterly.registry.CommonWinterlyBlockEntities.GIFT_BOX_BLOCK_ENTITY;

public class WinterlyBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES_REGISTERER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "winterly");

	public static void init(IEventBus eventBus) {

		BLOCK_ENTITIES_REGISTERER.register("gift_box", () -> {
			var type = BlockEntityType.Builder.of(GiftBoxBlockEntity::new, getGiftBoxes()).build(null);
			GIFT_BOX_BLOCK_ENTITY = type;
			return type;
		});
		
		BLOCK_ENTITIES_REGISTERER.register(eventBus);
	}

	public static GiftBoxBlock[] getGiftBoxes() {
		ArrayList<GiftBoxBlock> gifts = new ArrayList<>();

		CommonWinterlyBlocks.BLOCKS.forEach((identifier, block) -> {
			if(block instanceof GiftBoxBlock box) {
				gifts.add(box);
			}
		});

		return gifts.toArray(new GiftBoxBlock[0]);
	}
}
