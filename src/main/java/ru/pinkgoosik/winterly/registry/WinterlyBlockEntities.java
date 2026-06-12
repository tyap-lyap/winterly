package ru.pinkgoosik.winterly.registry;

import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;

public class WinterlyBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Winterly.MOD_ID);

	public static final RegistryObject<BlockEntityType<GiftBoxBlockEntity>> GIFT_BOX = BLOCK_ENTITY_TYPES.register("gift_box", () ->
		BlockEntityType.Builder.of(GiftBoxBlockEntity::new,
			WinterlyBlocks.RED_GIFT_BOX.get(),
			WinterlyBlocks.ORANGE_GIFT_BOX.get(),
			WinterlyBlocks.YELLOW_GIFT_BOX.get(),
			WinterlyBlocks.GREEN_GIFT_BOX.get(),
			WinterlyBlocks.CYAN_GIFT_BOX.get(),
			WinterlyBlocks.BLUE_GIFT_BOX.get(),
			WinterlyBlocks.PURPLE_GIFT_BOX.get(),
			WinterlyBlocks.BLACK_GIFT_BOX.get(),
			WinterlyBlocks.WHITE_GIFT_BOX.get()
		).build(null));
}
