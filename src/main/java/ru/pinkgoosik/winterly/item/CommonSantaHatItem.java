package ru.pinkgoosik.winterly.item;

import net.minecraft.world.item.Item;

public class CommonSantaHatItem extends Item {
    public final String color;

    public CommonSantaHatItem(Properties settings, String color) {
        super(settings);
        this.color = color;
    }
}
