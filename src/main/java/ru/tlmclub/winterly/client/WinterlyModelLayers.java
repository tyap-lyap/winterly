package ru.tlmclub.winterly.client;

import static net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry.registerModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import ru.tlmclub.winterly.client.model.SantaHatModel;
import ru.tlmclub.winterly.client.model.ScarfModel;
import static ru.tlmclub.winterly.WinterlyMod.newId;

public class WinterlyModelLayers {
    public static final EntityModelLayer SANTA_HAT_LAYER = of("santa_hat");
    public static final EntityModelLayer SCARF_LAYER = of("scarf");

    private static EntityModelLayer of(String name){
        return new EntityModelLayer(newId(name), "main");
    }

    public static void init() {
        registerModelLayer(SANTA_HAT_LAYER, SantaHatModel::getTexturedModelData);
        registerModelLayer(SCARF_LAYER, ScarfModel::getTexturedModelData);
    }
}
