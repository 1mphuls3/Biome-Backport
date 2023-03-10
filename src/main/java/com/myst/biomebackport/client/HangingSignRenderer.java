package com.myst.biomebackport.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.blaze3d.platform.NativeImage;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import com.myst.biomebackport.common.block.HangingSignBlock;
import com.myst.biomebackport.common.block.HangingSignBlockWall;
import com.myst.biomebackport.common.block.HangingSignBlockCeiling;
import com.myst.biomebackport.common.blockentity.HangingSignBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.geom.*;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;
import java.util.Map;

import static net.minecraft.client.renderer.Sheets.SIGN_SHEET;

@OnlyIn(Dist.CLIENT)
public class HangingSignRenderer implements BlockEntityRenderer<HangingSignBlockEntity> {
    private static final int OUTLINE_RENDER_DISTANCE = Mth.square(16);
    private final Map<WoodType, HangingSignRenderer.SignModel> signModels;
    private final Font font;

    public HangingSignRenderer(BlockEntityRendererProvider.Context context) {
        this.signModels = WoodType.values().collect(ImmutableMap.toImmutableMap((type) -> {
            return type;
        }, (type) -> {
            return new HangingSignRenderer.SignModel(context.bakeLayer(createSignModelName(type)));
        }));
        this.font = context.getFont();
    }

    @Override
    public void render(HangingSignBlockEntity blockEntity, float partialTick, PoseStack stack, MultiBufferSource buffer, int packedLight, int packedOverlay) {
        BlockState blockState = blockEntity.getBlockState();
        stack.pushPose();
        WoodType woodtype = getWoodType(blockState.getBlock());
        HangingSignRenderer.SignModel signrenderer$signmodel = this.signModels.get(woodtype);
        stack.translate(0.5D, 0.5D, 0.5D);
        stack.mulPose(Vector3f.XP.rotationDegrees(180));
        stack.translate(0, -1D, 0);
        if (blockState.getBlock() instanceof HangingSignBlockCeiling) {
            float f1 = ((float)(blockState.getValue(HangingSignBlockCeiling.ROTATION) * 360) / 16.0F);
            stack.mulPose(Vector3f.YP.rotationDegrees(f1));
            signrenderer$signmodel.bar.visible = false;
            if(blockState.getValue(HangingSignBlockCeiling.ATTACHED)) {
                signrenderer$signmodel.chains.visible = false;
                signrenderer$signmodel.vchains.visible = true;
            } else {
                signrenderer$signmodel.chains.visible = true;
                signrenderer$signmodel.vchains.visible = false;
            }

        } else {
            float f4 = -blockState.getValue(HangingSignBlockWall.FACING).toYRot();
            stack.mulPose(Vector3f.YP.rotationDegrees(f4));
            signrenderer$signmodel.bar.visible = true;
            signrenderer$signmodel.chains.visible = true;
            signrenderer$signmodel.vchains.visible = false;
        }

        stack.pushPose();
        ResourceLocation location = new ResourceLocation(woodtype.name());
        Material material = new Material(SIGN_SHEET, new ResourceLocation("biomebackport", "entity/signs/hanging/" + location.getPath()));

        VertexConsumer vertexconsumer = material.buffer(buffer, signrenderer$signmodel::renderType);
        signrenderer$signmodel.root.render(stack, vertexconsumer, packedLight, packedOverlay);
        stack.popPose();
        int i = getDarkColor(blockEntity);
        FormattedCharSequence[] aformattedcharsequence = blockEntity.getRenderMessages(Minecraft.getInstance().isTextFilteringEnabled(), (p_173653_) -> {
            List<FormattedCharSequence> list = this.font.split(p_173653_, 48);
            return list.isEmpty() ? FormattedCharSequence.EMPTY : list.get(0);
        });
        int k;
        boolean flag;
        int l;
        if (blockEntity.hasGlowingText()) {
            k = blockEntity.getColor().getTextColor();
            flag = isOutlineVisible(blockEntity, k);
            l = 15728880;
        } else {
            k = i;
            flag = false;
            l = packedLight;
        }

        if(blockState.getBlock() instanceof HangingSignBlockWall) {
            if(blockState.getValue(HangingSignBlockWall.FACING) == Direction.WEST || blockState.getValue(HangingSignBlockWall.FACING) == Direction.EAST) {
                stack.mulPose(Vector3f.YP.rotationDegrees(180));
            }
        }
        stack.translate(0.0F, 1.2, -0.065F);

        float scale = 0.0145F;
        stack.scale(scale, scale, scale);

        for(int i1 = 0; i1 < 4; ++i1) {
            FormattedCharSequence formattedcharsequence = aformattedcharsequence[i1];
            float f3 = (float)(-this.font.width(formattedcharsequence) / 2);
            if (flag) {
                this.font.drawInBatch8xOutline(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, i, stack.last().pose(), buffer, l);
            } else {
                this.font.drawInBatch(formattedcharsequence, f3, (float)(i1 * 10 - 20), k, false, stack.last().pose(), buffer, false, 0, l);
            }
        }

        stack.popPose();
    }
    private static boolean isOutlineVisible(HangingSignBlockEntity pBlockEntity, int pTextColor) {
        if (pTextColor == DyeColor.BLACK.getTextColor()) {
            return true;
        } else {
            Minecraft minecraft = Minecraft.getInstance();
            LocalPlayer localplayer = minecraft.player;
            if (localplayer != null && minecraft.options.getCameraType().isFirstPerson() && localplayer.isScoping()) {
                return true;
            } else {
                Entity entity = minecraft.getCameraEntity();
                return entity != null && entity.distanceToSqr(Vec3.atCenterOf(pBlockEntity.getBlockPos())) < (double)OUTLINE_RENDER_DISTANCE;
            }
        }
    }

    private static int getDarkColor(HangingSignBlockEntity pBlockEntity) {
        int i = pBlockEntity.getColor().getTextColor();
        int j = (int)((double) NativeImage.getR(i) * 0.4D);
        int k = (int)((double)NativeImage.getG(i) * 0.4D);
        int l = (int)((double)NativeImage.getB(i) * 0.4D);
        return i == DyeColor.BLACK.getTextColor() && pBlockEntity.hasGlowingText() ? -988212 : NativeImage.combine(0, l, k, j);
    }

    public static WoodType getWoodType(Block block) {
        WoodType woodtype;
        if (block instanceof HangingSignBlock sign) {
            woodtype = sign.type();
        } else {
            woodtype = WoodType.OAK;
        }

        return woodtype;
    }

    public static HangingSignRenderer.SignModel createSignModel(EntityModelSet entityModelSet, WoodType type) {
        return new HangingSignRenderer.SignModel(entityModelSet.bakeLayer(createSignModelName(type)));
    }

    public static ModelLayerLocation createSignModelName(WoodType type) {
        ResourceLocation location = new ResourceLocation(type.name());
        return new ModelLayerLocation(new ResourceLocation("biomebackport", "sign/" + location.getPath()), "main");
    }

    public static LayerDefinition createSignLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        partdefinition.addOrReplaceChild("bar", CubeListBuilder.create().texOffs(0, 0).mirror().addBox(-8.0F, -16.0F, -2.0F, 16.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        PartDefinition chains = partdefinition.addOrReplaceChild("chains", CubeListBuilder.create(), PartPose.offset(-13.0F, 11.0F, 8.0F));

        chains.addOrReplaceChild("cube_r1", CubeListBuilder.create().texOffs(3, 7).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, 1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, -3.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(8.0F, 0.0F, -8.0F, 0.0F, 0.7854F, 0.0F));
        chains.addOrReplaceChild("cube_r2", CubeListBuilder.create().texOffs(3, 7).addBox(-1.5F, -2.0F, 0.0F, 3.0F, 4.0F, 0.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, 1.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F))
                .texOffs(0, 7).addBox(0.0F, -3.0F, -1.5F, 0.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(18.0F, 0.0F, -8.0F, 0.0F, 0.7854F, 0.0F));

        partdefinition.addOrReplaceChild("sign", CubeListBuilder.create().texOffs(0, 12).mirror().addBox(-7.0F, -10.0F, -1.0F, 14.0F, 10.0F, 2.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offset(0.0F, 24.0F, 0.0F));

        partdefinition.addOrReplaceChild("vchains", CubeListBuilder.create().texOffs(13, 6).addBox(-7.0F, -16.0F, 0.0F, 14.0F, 6.0F, 0.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));

        return LayerDefinition.create(meshdefinition, 64, 32);
    }

    @OnlyIn(Dist.CLIENT)
    public static final class SignModel extends Model {
        public final ModelPart root;
        public final ModelPart bar;
        public final ModelPart vchains;
        public final ModelPart chains;

        public SignModel(ModelPart pRoot) {
            super(RenderType::entityCutoutNoCull);
            this.root = pRoot;
            this.bar = pRoot.getChild("bar");
            this.vchains = pRoot.getChild("vchains");
            this.chains = pRoot.getChild("chains");
        }

        public void renderToBuffer(PoseStack pPoseStack, VertexConsumer pBuffer, int pPackedLight, int pPackedOverlay, float pRed, float pGreen, float pBlue, float pAlpha) {
            this.root.render(pPoseStack, pBuffer, pPackedLight, pPackedOverlay, pRed, pGreen, pBlue, pAlpha);
        }
    }
}