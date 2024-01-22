package winterly.fabric.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import winterly.Winterly;
import winterly.block.GiftBoxBlock;
import winterly.block.entity.GiftBoxBlockEntity;
import winterly.registry.CommonWinterlyBlocks;
import static winterly.registry.CommonWinterlyBlockEntities.*;

import java.util.ArrayList;

public class WinterlyBlockEntities {

	public static void init() {
		GIFT_BOX_BLOCK_ENTITY = Registry.register(
				BuiltInRegistries.BLOCK_ENTITY_TYPE,
				Winterly.id("gift_box"),
				FabricBlockEntityTypeBuilder.create(GiftBoxBlockEntity::new, getGiftBoxes()).build()
		);
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
