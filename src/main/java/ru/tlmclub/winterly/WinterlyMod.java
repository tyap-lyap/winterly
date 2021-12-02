package ru.tlmclub.winterly;

import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;

public class WinterlyMod implements ModInitializer {
    public static final String MOD_ID = "winterly";

    @Override
    public void onInitialize() {

    }
    public static Identifier newId(String path){
        return new Identifier(MOD_ID, path);
    }
}
