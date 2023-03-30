package com.myst.biomebackport.client.renderer;

import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import com.myst.biomebackport.common.blockentity.DecoratedPotBlockEntity;
import com.myst.biomebackport.core.registry.BlockRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.ChestBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.minecraft.world.level.redstone.Redstone;
import net.minecraftforge.client.RenderTypeHelper;

import java.util.BitSet;
import java.util.List;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class DecoratedPotRenderer<T extends DecoratedPotBlockEntity> implements BlockEntityRenderer<T> {
    public DecoratedPotRenderer(BlockEntityRendererProvider.Context context){
    }

    @Override
    public void render(T blockEntity, float partialTicks, PoseStack stackIn, MultiBufferSource bufferIn, int packedLight, int packedOverlay) {
        Level level = Minecraft.getInstance().level;

        String[] types = new String[]{"angler", "archer", "arms_up", "blade", "brewer", "burn", "danger",
                "explorer", "friend", "heart", "heartbreak", "howl", "miner", "mourner", "plenty", "prize",
                "sheaf", "shelter", "skull", "snort"};
        ResourceLocation[] TEXTURES = new ResourceLocation[20];

        for (int i = 0; i < types.length; i++) {
            TEXTURES[i] = modPath("block/" + types[i] + "_pottery_pattern");
        }

        int indexN = blockEntity.NORTH;
        int indexE = blockEntity.EAST;
        int indexW = blockEntity.WEST;
        int indexS = blockEntity.SOUTH;

        int[] indexes = new int[]{indexS, indexW, indexN, indexE};

        stackIn.pushPose();
        VertexConsumer vertexConsumer = bufferIn.getBuffer(RenderType.solid());

        stackIn.translate(0, 2, 0);
        stackIn.mulPose(Vector3f.XP.rotationDegrees(180));
        stackIn.mulPose(Vector3f.YP.rotationDegrees(180));
        stackIn.translate(-1, 1, 14/16F);

        for (int i = 0; i < 4; i++) {
            if(indexes[i] > 0) {
                TextureAtlasSprite texture = Minecraft.getInstance().getTextureAtlas(InventoryMenu.BLOCK_ATLAS).apply(TEXTURES[indexes[i]-1]);

                int ambientOcclusion = level.getBlockState(blockEntity.getBlockPos().below()).isViewBlocking(level, blockEntity.getBlockPos().below()) ? 160 : 175;

                renderTexture(stackIn, vertexConsumer, texture,
                        15/16F, 0F, 1/16F, 1/16F, 1F, 15/16F,
                        ambientOcclusion, ambientOcclusion, ambientOcclusion, 1, packedLight);
            }
            stackIn.translate(14/16F, 0, 2/16F);
            stackIn.mulPose(Vector3f.YP.rotationDegrees(90));
        }

        stackIn.popPose();
    }
    public static void renderTexture(PoseStack stack, VertexConsumer vertexConsumer, TextureAtlasSprite sprite,
                                     float x1, float y1, float z1, float x2, float y2, float z2,
                                     int r, int g, int b, int alpha, int brightness) {
        Matrix4f matrix = stack.last().pose();
        vertexConsumer.vertex(matrix, x2, y1, z1).color(r, g, b, alpha).uv(sprite.getU(x1 * 16), sprite.getV(y1 * 16)).uv2(brightness).normal(0, 0, -1).endVertex();
        vertexConsumer.vertex(matrix, x1, y1, z1).color(r, g, b, alpha).uv(sprite.getU(x2 * 16), sprite.getV(y1 * 16)).uv2(brightness).normal(0, 0, -1).endVertex();
        vertexConsumer.vertex(matrix, x1, y2, z1).color(r, g, b, alpha).uv(sprite.getU(x2 * 16), sprite.getV(y2 * 16)).uv2(brightness).normal(0, 0, -1).endVertex();
        vertexConsumer.vertex(matrix, x2, y2, z1).color(r, g, b, alpha).uv(sprite.getU(x1 * 16), sprite.getV(y2 * 16)).uv2(brightness).normal(0, 0, -1).endVertex();
    }
}
