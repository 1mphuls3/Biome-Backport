package com.myst.biomebackport.common.entity.model;

import com.google.common.collect.ImmutableList;
import com.myst.biomebackport.common.entity.ModBoatEntity;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ListModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

public class BambooRaftModel extends BoatModel {
    private static final String LEFT_PADDLE = "left_paddle";
    private static final String RIGHT_PADDLE = "right_paddle";
    private static final String WATER_PATCH = "water_patch";
    private static final String TOP = "top";
    private static final String BOTTOM = "bottom";
    private static final String CHEST_BOTTOM = "chest_bottom";
    private static final String CHEST_LID = "chest_lid";
    private static final String CHEST_LOCK = "chest_lock";
    private final ModelPart leftPaddle;
    private final ModelPart rightPaddle;
    private final ModelPart waterPatch;
    private final ImmutableList<ModelPart> parts;

    public BambooRaftModel(ModelPart root, boolean hasChest) {
        super(root, hasChest);
        this.leftPaddle = root.getChild("left_paddle");
        this.rightPaddle = root.getChild("right_paddle");
        this.waterPatch = root.getChild("water_patch");
        ImmutableList.Builder<ModelPart> builder = new ImmutableList.Builder<>();
        builder.add(root.getChild("bottom"), root.getChild("back"), root.getChild("front"), root.getChild("right"), root.getChild("left"), this.leftPaddle, this.rightPaddle);
        if (hasChest) {
            builder.add(root.getChild("chest_bottom"));
            builder.add(root.getChild("chest_lid"));
            builder.add(root.getChild("chest_lock"));
        }

        this.parts = builder.build();
    }

    public static LayerDefinition createBodyModel(boolean hasChest) {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("front", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));
        partdefinition.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.offset(0.0F, 0.0F, 0.0F));

        partdefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-14.0F, -10.0F, 7.0F, 28.0F, 20.0F, 4.0F, new CubeDeformation(0.0F))
                .texOffs(0, 0).addBox(-14.0F, -8.0F, 11.0F, 28.0F, 16.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -9.0F, 0.0F, -1.5708F, 0.0F, 0.0F));

        if (hasChest) {
            partdefinition.addOrReplaceChild("chest_bottom", CubeListBuilder.create().texOffs(0, 76).addBox(0.0F, 0.0F, 0.0F, 12.0F, 8.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -10.0F, -6.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F));
            partdefinition.addOrReplaceChild("chest_lid", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 12.0F, 4.0F, 12.0F), PartPose.offsetAndRotation(-2.0F, -14.0F, -6.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F));
            partdefinition.addOrReplaceChild("chest_lock", CubeListBuilder.create().texOffs(0, 59).addBox(0.0F, 0.0F, 0.0F, 2.0F, 4.0F, 1.0F), PartPose.offsetAndRotation(-1.0F, -11.0F, -1.0F, 0.0F, (-(float)Math.PI / 2F), 0.0F));
        }

        partdefinition.addOrReplaceChild("left_paddle", CubeListBuilder.create().texOffs(0, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(-1.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, 9.0F, 0.0F, 0.0F, 0.19634955F));
        partdefinition.addOrReplaceChild("right_paddle", CubeListBuilder.create().texOffs(40, 24).addBox(-1.0F, 0.0F, -5.0F, 2.0F, 2.0F, 18.0F).addBox(0.001F, -3.0F, 8.0F, 1.0F, 6.0F, 7.0F), PartPose.offsetAndRotation(3.0F, -5.0F, -9.0F, 0.0F, (float)Math.PI, 0.19634955F));
        partdefinition.addOrReplaceChild("water_patch", CubeListBuilder.create().texOffs(0, 0), PartPose.offsetAndRotation(0.0F, -3.0F, 1.0F, ((float)Math.PI / 2F), 0.0F, 0.0F));
        return LayerDefinition.create(meshdefinition, 128, hasChest ? 128 : 64);
    }

    @Override
    public void setupAnim(Boat entity, float swing, float swingAmount, float age, float neadYaw, float headPitch) {
        animatePaddle(entity, 0, this.leftPaddle, swing);
        animatePaddle(entity, 1, this.rightPaddle, swing);
    }

    @Override
    public ImmutableList<ModelPart> parts() {
        return this.parts;
    }

    @Override
    public ModelPart waterPatch() {
        return this.waterPatch;
    }

    private static void animatePaddle(Boat boat, int side, ModelPart paddle, float swing) {
        float f = boat.getRowingTime(side, swing);
        paddle.xRot = Mth.clampedLerp((-(float)Math.PI / 3F), -0.2617994F, (Mth.sin(-f) + 1.0F) / 2.0F);
        paddle.yRot = Mth.clampedLerp((-(float)Math.PI / 4F), ((float)Math.PI / 4F), (Mth.sin(-f + 1.0F) + 1.0F) / 2.0F);
        if (side == 1) {
            paddle.yRot = (float)Math.PI - paddle.yRot;
        }

    }
}
