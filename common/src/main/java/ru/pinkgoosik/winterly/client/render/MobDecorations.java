package ru.pinkgoosik.winterly.client.render;

import java.util.ArrayList;
import java.util.List;

public class MobDecorations {
	public static final List<MobDecorationRenderer> LIST = new ArrayList<>();

	public static void init() {
		scarf("red_scarf");
		scarf("green_scarf");
		scarf("blue_scarf");

		santaHat("red_santa_hat");
		santaHat("blue_santa_hat");

		scarfAndHat("red_scarf", "red_santa_hat");
		scarfAndHat("green_scarf", "red_santa_hat");
		scarfAndHat("blue_scarf", "red_santa_hat");

		scarfAndHat("red_scarf", "blue_santa_hat");
		scarfAndHat("green_scarf", "blue_santa_hat");
		scarfAndHat("blue_scarf", "blue_santa_hat");
	}

	private static void scarf(String texture) {
		LIST.add(new ScarfRenderer(texture));
	}

	private static void santaHat(String texture) {
		LIST.add(new SantaHatRenderer(texture));
	}

	private static void scarfAndHat(String scarf, String hat) {
		LIST.add(new HatAndScarfRenderer(scarf, hat));
	}

	public static MobDecorationRenderer getRenderer(int index) {
		try {
			return MobDecorations.LIST.get(index);
		} catch (IndexOutOfBoundsException e) {
			return MobDecorations.LIST.getLast();
		}
	}
}
