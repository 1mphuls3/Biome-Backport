package com.myst.biomebackport.common.entity.renderer;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.myst.biomebackport.common.entity.model.BambooRaftModel;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

import javax.annotation.Nonnull;

import java.util.Map;
import java.util.stream.Stream;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class ModBoatRenderer extends BoatRenderer {
    private final ResourceLocation boatTexture;
    private final BoatModel boatModel;

    public ModBoatRenderer(EntityRendererProvider.Context context, String textureName, boolean isChestBoat) {
        super(context, isChestBoat);
        this.shadowRadius = 0.8F;
        this.boatTexture = isChestBoat ? modPath("textures/entity/chest_boat/" + textureName + ".png") : modPath("textures/entity/boat/" + textureName + ".png");
        boatModel = !textureName.contains("bamboo") ? new BoatModel(BoatModel.createBodyModel(isChestBoat).bakeRoot(), isChestBoat) :
                new BambooRaftModel(BambooRaftModel.createBodyModel(isChestBoat).bakeRoot(), isChestBoat);
    }

    @Override
    public ResourceLocation getTextureLocation(Boat entity) {
        return boatTexture;
    }

    @Nonnull
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(Boat boat) {
        return Pair.of(boatTexture, boatModel);
    }

    @Override
    public void render(Boat boat, float yaw, float partialTicks, PoseStack stack, MultiBufferSource buffer, int packedLight) {
        stack.pushPose();
        stack.translate(0.0D, 0.375D, 0.0D);
        stack.mulPose(Vector3f.YP.rotationDegrees(180.0F - yaw));
        float f = (float)boat.getHurtTime() - partialTicks;
        float f1 = boat.getDamage() - partialTicks;
        if (f1 < 0.0F) {
            f1 = 0.0F;
        }

        if (f > 0.0F) {
            stack.mulPose(Vector3f.XP.rotationDegrees(Mth.sin(f) * f * f1 / 10.0F * (float)boat.getHurtDir()));
        }

        float f2 = boat.getBubbleAngle(partialTicks);
        if (!Mth.equal(f2, 0.0F)) {
            stack.mulPose(new Quaternion(new Vector3f(1.0F, 0.0F, 1.0F), boat.getBubbleAngle(partialTicks), true));
        }

        Pair<ResourceLocation, BoatModel> pair = getModelWithLocation(boat);
        ResourceLocation resourcelocation = pair.getFirst();
        BoatModel boatmodel = pair.getSecond();
        stack.scale(-1.0F, -1.0F, 1.0F);
        stack.mulPose(Vector3f.YP.rotationDegrees(90.0F));
        boatmodel.setupAnim(boat, partialTicks, 0.0F, -0.1F, 0.0F, 0.0F);
        VertexConsumer vertexconsumer = buffer.getBuffer(boatmodel.renderType(resourcelocation));
        boatmodel.renderToBuffer(stack, vertexconsumer, packedLight, OverlayTexture.NO_OVERLAY, 1.0F, 1.0F, 1.0F, 1.0F);
        if (!boat.isUnderWater()) {
            VertexConsumer vertexconsumer1 = buffer.getBuffer(RenderType.waterMask());
            boatmodel.waterPatch().render(stack, vertexconsumer1, packedLight, OverlayTexture.NO_OVERLAY);
        }

        stack.popPose();
        super.render(boat, yaw, partialTicks, stack, buffer, packedLight);
    }
}