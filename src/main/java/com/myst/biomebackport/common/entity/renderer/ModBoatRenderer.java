package com.myst.biomebackport.common.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.datafixers.util.Pair;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.myst.biomebackport.common.entity.model.BambooRaftModel;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.vehicle.Boat;

import javax.annotation.Nonnull;

import java.util.Objects;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class ModBoatRenderer extends BoatRenderer {
    private final ResourceLocation boatTexture;
    private final BoatModel boatModel;

    public ModBoatRenderer(EntityRendererProvider.Context context, String textureName, boolean isChestBoat) {
        super(context, isChestBoat);
        this.boatTexture = isChestBoat ? modPath("textures/entity/chest_boat/" + textureName + ".png") : modPath("textures/entity/boat/" + textureName + ".png");
        boatModel = !Objects.equals(textureName, "bamboo") ? new BoatModel(BoatModel.createBodyModel(isChestBoat).bakeRoot(), isChestBoat) :
                new BambooRaftModel(BambooRaftModel.createBodyModel(isChestBoat).bakeRoot(), isChestBoat);
    }

    @Nonnull
    public Pair<ResourceLocation, BoatModel> getModelWithLocation(@Nonnull Boat boat) {
        return Pair.of(boatTexture, boatModel);
    }
}