package ru.pinkgoosik.winterly;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.config.WinterlyConfig;
import ru.pinkgoosik.winterly.data.ChunkFlowerCache;
import ru.pinkgoosik.winterly.registry.*;

@Mod(Winterly.MOD_ID)
public class Winterly {
	public static final String MOD_ID = "winterly";
	public static final Logger LOGGER = LoggerFactory.getLogger("Winterly");

	public Winterly() {
		var bus = FMLJavaModLoadingContext.get().getModEventBus();

		WinterlyBlocks.BLOCKS.register(bus);
		WinterlyItems.ITEMS.register(bus);
		WinterlyBlockEntities.BLOCK_ENTITY_TYPES.register(bus);
		WinterlyFeatures.FEATURES.register(bus);
		WinterlyCreativeTab.CREATIVE_MODE_TABS.register(bus);

		bus.addListener(this::commonSetup);

		WinterlyConfig.register();
		MinecraftForge.EVENT_BUS.addGenericListener(LevelChunk.class, Winterly::onAttachChunk);
		MinecraftForge.EVENT_BUS.addListener(GiftBoxBlock::onRightClickBlock);

		if (FMLLoader.getDist() == Dist.CLIENT) {
			WinterlyClient.init();
		}
	}

	private static void onAttachChunk(AttachCapabilitiesEvent<LevelChunk> event) {
		event.addCapability(id("flower_cache"), new ChunkFlowerCache());
	}

	public static ResourceLocation id(String path) {
		return new ResourceLocation(MOD_ID, path);
	}

	public static WinterlyConfig config() {
		return WinterlyConfig.UNIT.get();
	}

	private void commonSetup(FMLCommonSetupEvent event) {
	}
}
