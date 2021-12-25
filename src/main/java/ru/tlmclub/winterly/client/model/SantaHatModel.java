package ru.tlmclub.winterly.client.model;

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

        ModelPartData hat = modelPartData.addChild("hat", ModelPartBuilder.create().uv(0, 34).cuboid(-4.0F, -14.0F, 5.0F, 3.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

        hat.addChild("bone_1", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -7.5F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F))
                .uv(0, 12).cuboid(-4.0F, -11.0F, -6.5F, 8.0F, 4.0F, 8.0F, new Dilation(0.1F)), ModelTransform.rotation(-0.3927F, 0.0F, 0.0F));

        hat.addChild("bone_2", ModelPartBuilder.create().uv(0, 24).cuboid(-3.0F, -12.9F, -9.0F, 6.0F, 5.0F, 5.0F, new Dilation(0.1F)), ModelTransform.rotation(-0.7854F, 0.0F, 0.0F));

        return TexturedModelData.of(modelData, 64, 64);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float a) {
        ImmutableList.of(this.hat).forEach((part) -> part.render(matrices, vertices, light, overlay, r, g, b, a));
    }
}
