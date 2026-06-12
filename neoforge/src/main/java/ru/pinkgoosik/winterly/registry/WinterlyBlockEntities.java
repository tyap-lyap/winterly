package ru.pinkgoosik.winterly.registry;

import cc.sighs.oelib.registry.DeferredRegister;
import cc.sighs.oelib.registry.RegisterSupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import ru.pinkgoosik.winterly.Winterly;
import ru.pinkgoosik.winterly.block.entity.GiftBoxBlockEntity;

import java.util.Set;

public class WinterlyBlockEntities {
	public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(Registries.BLOCK_ENTITY_TYPE, Winterly.MOD_ID);

	public static final RegisterSupplier<BlockEntityType<GiftBoxBlockEntity>> GIFT_BOX = BLOCK_ENTITY_TYPES.register("gift_box", () ->
		new BlockEntityType<>(GiftBoxBlockEntity::new, Set.of(
			WinterlyNeoForgeBlocks.RED_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.ORANGE_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.YELLOW_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.GREEN_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.CYAN_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.BLUE_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.PURPLE_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.BLACK_GIFT_BOX.get(),
			WinterlyNeoForgeBlocks.WHITE_GIFT_BOX.get()
		)));
}
