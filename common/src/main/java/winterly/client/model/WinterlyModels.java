package winterly.client.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import winterly.client.WinterlyModelLayers;

public class WinterlyModels {
    public static final ScarfModel SCARF_MODEL = new ScarfModel(getModelPart(WinterlyModelLayers.SCARF_LAYER));
    public static final SantaHatModel SANTA_HAT_MODEL = new SantaHatModel(getModelPart(WinterlyModelLayers.SANTA_HAT_LAYER));

    public static ModelPart getModelPart(ModelLayerLocation layer){
        return Minecraft.getInstance().getEntityModels().bakeLayer(layer);
    }
}
