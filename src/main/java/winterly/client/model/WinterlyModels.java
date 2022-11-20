package winterly.client.model;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import winterly.client.WinterlyModelLayers;

public class WinterlyModels {
    public static final ScarfModel SCARF_MODEL = new ScarfModel(getModelPart(WinterlyModelLayers.SCARF_LAYER));
    public static final SantaHatModel SANTA_HAT_MODEL = new SantaHatModel(getModelPart(WinterlyModelLayers.SANTA_HAT_LAYER));

    public static ModelPart getModelPart(EntityModelLayer layer){
        return MinecraftClient.getInstance().getEntityModelLoader().getModelPart(layer);
    }
}
