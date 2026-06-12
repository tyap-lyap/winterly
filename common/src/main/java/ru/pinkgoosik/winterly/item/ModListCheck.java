package ru.pinkgoosik.winterly.item;

import cc.sighs.oelib.platform.Platform;

public class ModListCheck {

	public static boolean isCuriosLoaded() {
		return Platform.isModLoaded("curios") || Platform.isModLoaded("trinkets");
	}
}
