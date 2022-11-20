package winterly.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class ScarfModel extends Model {
    public final ModelPart scarf;

    public ScarfModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.scarf = root.getChild("scarf");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData scarf = modelPartData.addChild("scarf", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -1.0F, -5.0F, 10.0F, 3.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        scarf.addChild("back_hanger", ModelPartBuilder.create().uv(0, 13).cuboid(-5.0F, 3.0F, 3.0F, 5.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.rotation(0.3491F, 0.0F, 0.0F));
        scarf.addChild("front_hanger", ModelPartBuilder.create().uv(0, 13).cuboid(-1.0F, 2.0F, -4.0F, 5.0F, 16.0F, 0.0F, new Dilation(0.0F)), ModelTransform.rotation(0.0F, -0.2618F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float a) {
        ImmutableList.of(this.scarf).forEach((part) -> part.render(matrices, vertices, light, overlay, r, g, b, a));
    }
}
