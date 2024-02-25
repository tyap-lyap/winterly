package winterly.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;

@SuppressWarnings("unused")
public class CommonWinterlyItems {
    public static final Map<ResourceLocation, Item> ITEMS = new LinkedHashMap<>();

    public static Item RED_CANDY_CANE;
    public static Item GREEN_CANDY_CANE;
    public static Item BLUE_CANDY_CANE;

    public static Item CRYOMARBLE_SHARD;
    public static Item CRYOMARBLE;

    public static Item CRYOMARBLE_SWORD;
    public static Item CRYOMARBLE_SHOVEL;
    public static Item CRYOMARBLE_PICKAXE;
    public static Item CRYOMARBLE_AXE;
    public static Item CRYOMARBLE_HOE;

    public static Item RED_SANTA_HAT;
    public static Item BLUE_SANTA_HAT;

    public static Item WHITE_SCARF;
    public static Item RED_SCARF;
    public static Item GREEN_SCARF;
    public static Item BLUE_SCARF;
    public static Item RAINBOW_SCARF;

	public static Item.Properties settings() {
		return new Item.Properties();
	}
}

