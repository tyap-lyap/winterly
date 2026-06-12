package ru.pinkgoosik.winterly;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.world.InteractionResult;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;
import ru.pinkgoosik.winterly.registry.WinterlyFabricBlockEntities;
import ru.pinkgoosik.winterly.registry.WinterlyFabricBlocks;
import ru.pinkgoosik.winterly.registry.WinterlyFabricItems;
import ru.pinkgoosik.winterly.worldgen.WinterlyFabricWorldgen;

public class WinterlyFabric implements ModInitializer {

	@Override
	public void onInitialize() {
		Winterly.init();
		WinterlyPlatformHolder.setInstance(new FabricWinterlyPlatform());
		// Force attachment type registration before Fabric's attachment sync handshake runs.
		FabricAttachments.register();

		WinterlyBlocks.BLOCKS.register();
		WinterlyItems.ITEMS.register();
		WinterlyFeatures.FEATURES.register();
		WinterlyCreativeTab.CREATIVE_MODE_TABS.register();

		WinterlyFabricBlocks.BLOCKS.register();
		WinterlyFabricItems.ITEMS.register();
		WinterlyFabricBlockEntities.BLOCK_ENTITY_TYPES.register();
		WinterlyFabricWorldgen.register();

		UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
			var pos = hitResult.getBlockPos();
			if (!(world.getBlockState(pos).getBlock() instanceof GiftBoxBlock)) return InteractionResult.PASS;

			var held = player.getItemInHand(hand);
			if (world.isClientSide()) {
				if (held.isEmpty()) {
					return InteractionResult.PASS;
				}
				if (!GiftBoxBlock.canStore(held)) {
					return InteractionResult.FAIL;
				}
				return GiftBoxBlock.getInsertableAmount(world, pos, player, held) > 0 ? InteractionResult.SUCCESS : InteractionResult.FAIL;
			}
			return GiftBoxBlock.onUse(world, pos, player, held);
		});
	}
}
