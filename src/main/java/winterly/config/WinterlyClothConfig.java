package winterly.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;

@Config(name = "winterly")
public class WinterlyClothConfig extends WinterlyConfig implements ConfigData {

	public static WinterlyClothConfig init() {
		AutoConfig.register(WinterlyClothConfig.class, GsonConfigSerializer::new);
		return AutoConfig.getConfigHolder(WinterlyClothConfig.class).getConfig();
	}

}
