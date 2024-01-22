package winterly.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class SantaHatModel extends Model {
    public final ModelPart hat;

    public SantaHatModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.hat = root.getChild("hat");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition hat = modelPartData.addOrReplaceChild("hat", CubeListBuilder.create().texOffs(27, 14).addBox(-4.0F, -8.0F, 7.0F, 3.0F, 3.0F, 3.0F), PartPose.offset(0.0F, 24.0F, 0.0F));

        hat.addOrReplaceChild("cube_1", CubeListBuilder.create().texOffs(0, 27).addBox(-3.0F, -5.0F, 0.0F, 6.0F, 4.0F, 8.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.3491F, 0.0F, 0.0F));
        hat.addOrReplaceChild("cube_2", CubeListBuilder.create().texOffs(0, 14).addBox(-4.5F, -3.0F, -3.5F, 9.0F, 4.0F, 9.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.5236F, 0.0F, 0.0F));
        hat.addOrReplaceChild("cube_3", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0.0F, -4.5F, 11.0F, 3.0F, 11.0F), PartPose.offsetAndRotation(0.0F, -8.0F, 0.0F, -0.2618F, 0.0F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float a) {
        ImmutableList.of(this.hat).forEach((part) -> part.render(matrices, vertices, light, overlay, r, g, b, a));
    }
}
