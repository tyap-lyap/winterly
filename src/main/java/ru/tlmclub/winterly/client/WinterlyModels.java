package ru.tlmclub.winterly.client;

import net.minecraft.client.util.ModelIdentifier;
import ru.tlmclub.winterly.WinterlyMod;

import java.util.ArrayList;

public class WinterlyModels {
    private static final ArrayList<ModelIdentifier> MODELS = new ArrayList<>();

    public static final ModelIdentifier CANDLE_HAT = add("candle_hat_on_head");
    public static final ModelIdentifier RED_SANTA_HAT = add("red_santa_hat_on_head");
    public static final ModelIdentifier BLUE_SANTA_HAT = add("blue_santa_hat_on_head");

    private static ModelIdentifier add(String model) {
        ModelIdentifier id = new ModelIdentifier(WinterlyMod.MOD_ID + ":" + model + "#inventory");
        MODELS.add(id);
        return id;
    }

    public static void register(){
        MODELS.forEach(model -> {

        });
    }
}
