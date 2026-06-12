package ru.pinkgoosik.winterly.compat;

import net.minecraft.world.item.Item;

public class WinterlyCuriosIntegration {

	public static void registerCurio(Item item) {
		WinterlyPlatformHolder.get().registerCurio(item);
	}
}
