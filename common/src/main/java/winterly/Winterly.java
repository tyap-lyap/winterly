package winterly;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import winterly.config.WinterlyClothConfig;
import winterly.config.WinterlyConfig;

public class Winterly {
    public static final String MOD_ID = "winterly";
	public static final Logger LOGGER = LoggerFactory.getLogger("Winterly");
	public static WinterlyConfig config = WinterlyClothConfig.init();

    public static ResourceLocation id(String path) {
        return new ResourceLocation(MOD_ID, path);
    }

}
