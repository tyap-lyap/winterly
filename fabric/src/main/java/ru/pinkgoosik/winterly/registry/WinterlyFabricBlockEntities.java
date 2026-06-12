package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;

public class WinterlyFabricBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Winterly.MOD_ID);

	public static final RegisterSupplier<BlockEntityType<GiftBoxBlockEntity>> GIFT_BOX = BLOCK_ENTITY_TYPES.register("gift_box",
		() -> FabricBlockEntityTypeBuilder.create(GiftBoxBlockEntity::new,
			WinterlyFabricBlocks.RED_GIFT_BOX.get(),
			WinterlyFabricBlocks.ORANGE_GIFT_BOX.get(),
			WinterlyFabricBlocks.YELLOW_GIFT_BOX.get(),
			WinterlyFabricBlocks.GREEN_GIFT_BOX.get(),
			WinterlyFabricBlocks.CYAN_GIFT_BOX.get(),
			WinterlyFabricBlocks.BLUE_GIFT_BOX.get(),
			WinterlyFabricBlocks.PURPLE_GIFT_BOX.get(),
			WinterlyFabricBlocks.BLACK_GIFT_BOX.get(),
			WinterlyFabricBlocks.WHITE_GIFT_BOX.get()
		).build());
}
