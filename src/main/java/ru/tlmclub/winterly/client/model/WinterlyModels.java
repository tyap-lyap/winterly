package ru.tlmclub.winterly.client.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;

import static ru.tlmclub.winterly.client.WinterlyModelLayers.*;

public class WinterlyModels {
    public static final ScarfModel SCARF_MODEL = new ScarfModel(getModelPart(SCARF_LAYER));
    public static final SantaHatModel SANTA_HAT_MODEL = new SantaHatModel(getModelPart(SANTA_HAT_LAYER));

    public static ModelPart getModelPart(EntityModelLayer layer){
        return MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer);
    }
}
