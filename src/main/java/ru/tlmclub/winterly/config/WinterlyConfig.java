package ru.tlmclub.winterly.config;

import ru.pinkgoosik.goosikconfig.api.Config;
import ru.tlmclub.winterly.WinterlyMod;

public class WinterlyConfig extends Config {

    public WinterlyConfig() {
        super(WinterlyMod.MOD_ID);
    }

    @Override
    public void init() {
        addBoolean("enabled", "decorations_on_mobs", true);
        addBoolean("only_in_winter", "decorations_on_mobs", true);
        addInteger("chance_percentage", "decorations_on_mobs", 10);
    }
}
