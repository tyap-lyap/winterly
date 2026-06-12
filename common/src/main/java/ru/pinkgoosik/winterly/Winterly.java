package ru.pinkgoosik.winterly;

import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.pinkgoosik.winterly.config.WinterlyConfig;

public class Winterly {
	public static final String MOD_ID = "winterly";
	public static final Logger LOG = LoggerFactory.getLogger("Winterly");

	public static void init() {
		WinterlyConfig.register();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}

	public static WinterlyConfig config() {
		return WinterlyConfig.UNIT.get();
	}
}
