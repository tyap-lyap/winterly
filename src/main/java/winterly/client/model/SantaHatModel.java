package winterly.client.model;

import com.google.common.collect.ImmutableList;
import net.minecraft.client.model.*;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.util.math.MatrixStack;

public class SantaHatModel extends Model {
    public final ModelPart hat;

    public SantaHatModel(ModelPart root) {
        super(RenderLayer::getEntityCutoutNoCull);
        this.hat = root.getChild("hat");
    }

	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

		ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(27, 14).cuboid(-4.0F, -8.0F, 7.0F, 3.0F, 3.0F, 3.0F), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		hat.addChild("cube_1", ModelPartBuilder.create().uv(0, 27).cuboid(-3.0F, -5.0F, 0.0F, 6.0F, 4.0F, 8.0F), ModelTransform.of(0.0F, -8.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
		hat.addChild("cube_2", ModelPartBuilder.create().uv(0, 14).cuboid(-4.5F, -3.0F, -3.5F, 9.0F, 4.0F, 9.0F), ModelTransform.of(0.0F, -8.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
		hat.addChild("cube_3", ModelPartBuilder.create().uv(0, 0).cuboid(-5.5F, 0.0F, -4.5F, 11.0F, 3.0F, 11.0F), ModelTransform.of(0.0F, -8.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

		return TexturedModelData.of(modelData, 64, 64);
	}

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float a) {
        ImmutableList.of(this.hat).forEach((part) -> part.render(matrices, vertices, light, overlay, r, g, b, a));
    }
}
