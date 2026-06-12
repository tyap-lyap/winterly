package ru.pinkgoosik.winterly;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.common.NeoForge;
import ru.pinkgoosik.winterly.block.GiftBoxBlock;
import ru.pinkgoosik.winterly.compat.WinterlyPlatformHolder;
import ru.pinkgoosik.winterly.registry.WinterlyBlockEntities;
import ru.pinkgoosik.winterly.registry.WinterlyNeoForgeBlocks;
import ru.pinkgoosik.winterly.registry.WinterlyNeoForgeItems;

@Mod(Winterly.MOD_ID)
public class WinterlyNeoForge {

    public WinterlyNeoForge(IEventBus modBus, ModContainer container) {
        Winterly.init();
        WinterlyPlatformHolder.setInstance(new NeoForgeWinterlyPlatform());

        NeoForgeAttachments.ATTACHMENT_TYPES.register(modBus);
        WinterlyBlocks.BLOCKS.register();
        WinterlyItems.ITEMS.register();
        WinterlyFeatures.FEATURES.register();
        WinterlyNeoForgeBlocks.BLOCKS.register();
        WinterlyNeoForgeItems.ITEMS.register();
        WinterlyBlockEntities.BLOCK_ENTITY_TYPES.register();
        WinterlyCreativeTab.CREATIVE_MODE_TABS.register();

        NeoForge.EVENT_BUS.addListener(GiftBoxBlock::onRightClickBlock);
    }
}
