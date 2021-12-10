package ru.tlmclub.winterly.item;

import io.wispforest.owo.itemgroup.OwoItemGroup;
import io.wispforest.owo.itemgroup.gui.ItemGroupButton;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import ru.tlmclub.winterly.registry.WinterlyItems;

public class WinterlyItemGroup extends OwoItemGroup {

    public WinterlyItemGroup(Identifier id) {
        super(id);
    }

    @Override
    protected void setup() {
        addButton(ItemGroupButton.discord("https://discord.gg/DcemWeskeZ"));
    }

    @Override
    public ItemStack createIcon() {
        return WinterlyItems.CANDLE_HAT.getDefaultStack();
    }
}
