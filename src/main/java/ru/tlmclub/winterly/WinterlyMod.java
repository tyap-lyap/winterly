package ru.tlmclub.winterly;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import ru.tlmclub.winterly.block.WinterlyBlocks;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";

    @Override
    public void onInitialize() {
        WinterlyBlocks.register();
    }

    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
