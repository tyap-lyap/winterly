package winterly.client.model;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.*;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;

public class ScarfModel extends Model {
    public final ModelPart scarf;

    public ScarfModel(ModelPart root) {
        super(RenderType::entityCutoutNoCull);
        this.scarf = root.getChild("scarf");
    }

    public static LayerDefinition getTexturedModelData() {
        MeshDefinition modelData = new MeshDefinition();
        PartDefinition modelPartData = modelData.getRoot();

        PartDefinition scarf = modelPartData.addOrReplaceChild("scarf", CubeListBuilder.create().texOffs(0, 0).addBox(-5.0F, -1.0F, -5.0F, 10.0F, 3.0F, 10.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        scarf.addOrReplaceChild("back_hanger", CubeListBuilder.create().texOffs(0, 13).addBox(-5.0F, 3.0F, 3.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.rotation(0.3491F, 0.0F, 0.0F));
        scarf.addOrReplaceChild("front_hanger", CubeListBuilder.create().texOffs(0, 13).addBox(-1.0F, 2.0F, -4.0F, 5.0F, 16.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.rotation(0.0F, -0.2618F, 0.0F));

        return LayerDefinition.create(modelData, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack matrices, VertexConsumer vertices, int light, int overlay, float r, float g, float b, float a) {
        ImmutableList.of(this.scarf).forEach((part) -> part.render(matrices, vertices, light, overlay, r, g, b, a));
    }
}
