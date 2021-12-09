package ru.tlmclub.winterly.client;

import net.fabricmc.fabric.api.client.model.ModelLoadingRegistry;
import net.minecraft.client.util.ModelIdentifier;
import ru.tlmclub.winterly.WinterlyMod;

import java.util.ArrayList;
import java.util.List;

public class WinterlyModels {
    public static final List<ModelIdentifier> MODELS = new ArrayList<>();

    public static final ModelIdentifier CANDLE_HAT = add("candle_hat_on_head");
    public static final ModelIdentifier RED_SANTA_HAT = add("red_santa_hat_on_head");
    public static final ModelIdentifier BLUE_SANTA_HAT = add("blue_santa_hat_on_head");

    public static final ModelIdentifier RED_SCARF = add("red_scarf_on_body");
    public static final ModelIdentifier GREEN_SCARF = add("green_scarf_on_body");
    public static final ModelIdentifier BLUE_SCARF = add("blue_scarf_on_body");

    private static ModelIdentifier add(String model) {
        ModelIdentifier id = new ModelIdentifier(WinterlyMod.MOD_ID + ":" + model + "#inventory");
        MODELS.add(id);
        return id;
    }

    public static void register(){
        ModelLoadingRegistry.INSTANCE.registerModelProvider((manager, out) -> MODELS.forEach(out));
    }
}
