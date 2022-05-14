package ru.tlmclub.winterly.compat;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import ru.tlmclub.winterly.config.WinterlyClothConfig;

@Environment(EnvType.CLIENT)
public class WinterlyModMenuIntegration implements ModMenuApi {

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        if (FabricLoader.getInstance().isModLoaded("cloth-config")) {
            return WinterlyClothConfig.getModConfigScreenFactory();
        }
        return null;
    }

}
