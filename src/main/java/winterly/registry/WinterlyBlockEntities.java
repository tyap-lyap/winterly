package winterly.registry;

import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.util.registry.Registry;
import winterly.Winterly;
import winterly.block.GiftBoxBlock;
import winterly.block.entity.GiftBoxBlockEntity;

import java.util.ArrayList;

public class WinterlyBlockEntities {

	public static final BlockEntityType<GiftBoxBlockEntity> GIFT_BOX_BLOCK_ENTITY = Registry.register(
			Registry.BLOCK_ENTITY_TYPE,
			Winterly.id("gift_box"),
			FabricBlockEntityTypeBuilder.create(GiftBoxBlockEntity::new, getGiftBoxes()).build()
	);

	public static void init() {

	}

	public static GiftBoxBlock[] getGiftBoxes() {
		ArrayList<GiftBoxBlock> gifts = new ArrayList<>();

		WinterlyBlocks.BLOCKS.forEach((identifier, block) -> {
			if(block instanceof GiftBoxBlock box) {
				gifts.add(box);
			}
		});

		return gifts.toArray(new GiftBoxBlock[0]);
	}
}
