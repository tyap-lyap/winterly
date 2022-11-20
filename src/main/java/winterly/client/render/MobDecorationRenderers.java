package winterly.client.render;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class MobDecorationRenderers {
    public static final List<MobDecorationRenderer> LIST = new ArrayList<>();

    public static void init() {
        scarf("red_scarf");
        scarf("green_scarf");
        scarf("blue_scarf");

        santaHat("red_santa_hat");
        santaHat("blue_santa_hat");
    }

    private static void scarf(String texture) {
        LIST.add(new ScarfRenderer(texture));
    }

    private static void santaHat(String texture) {
        LIST.add(new SantaHatRenderer(texture));
    }

    public static MobDecorationRenderer getRenderer(int index){
        try {
            return MobDecorationRenderers.LIST.get(index);
        }catch (IndexOutOfBoundsException e) {
            return MobDecorationRenderers.LIST.get(MobDecorationRenderers.LIST.size() - 1);
        }
    }
}
