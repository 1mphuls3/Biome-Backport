package com.myst.biomebackport.client.screen;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import com.myst.biomebackport.client.HangingSignRenderer;
import com.myst.biomebackport.common.blockentity.HangingSignBlockEntity;
import com.myst.biomebackport.core.networking.HangingSignC2SPacket;
import com.myst.biomebackport.core.networking.ModMessages;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.stream.IntStream;

import static net.minecraft.client.renderer.Sheets.SIGN_SHEET;

public class HangingSignEditScreen extends Screen {
    private final HangingSignBlockEntity sign;
    private int frame;
    private int line;
    private TextFieldHelper signField;
    private WoodType woodType;
    private HangingSignRenderer.SignModel signModel;
    private final String[] messages;
    public HangingSignEditScreen(HangingSignBlockEntity sign, boolean filteringEnabled) {
        super(Component.translatable("sign.edit"));
        this.messages = IntStream.range(0, 4).mapToObj((p_169818_) -> {
            return sign.getMessage(p_169818_, filteringEnabled);
        }).map(Component::getString).toArray(String[]::new);
        this.sign = sign;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, CommonComponents.GUI_DONE, (p_169820_) -> {
            this.onDone();
        }));
        this.sign.setEditable(false);
        this.signField = new TextFieldHelper(() -> {
            return this.messages[this.line];
        }, (message) -> {
            this.messages[this.line] = message;
            this.sign.setMessage(this.line, Component.literal(message));
        }, TextFieldHelper.createClipboardGetter(this.minecraft), TextFieldHelper.createClipboardSetter(this.minecraft), (p_169822_) -> {
            return this.minecraft.font.width(p_169822_) <= 48;
        });
        BlockState blockstate = this.sign.getBlockState();
        this.woodType = HangingSignRenderer.getWoodType(blockstate.getBlock());
        this.signModel = HangingSignRenderer.createSignModel(this.minecraft.getEntityModels(), this.woodType);
    }

    @Override
    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
        ClientPacketListener clientpacketlistener = this.minecraft.getConnection();
        if (clientpacketlistener != null) {
            ModMessages.sendToServer(new HangingSignC2SPacket(this.sign.getBlockPos(), this.messages[0], this.messages[1], this.messages[2], this.messages[3]));
        }

        this.sign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.frame;
        if (!this.sign.getType().isValid(this.sign.getBlockState())) {
            this.onDone();
        }
    }

    private void onDone() {
        this.sign.setChanged();
        this.minecraft.setScreen(null);
    }

    public boolean charTyped(char code, int mod) {
        this.signField.charTyped(code);
        return true;
    }

    @Override
    public void onClose() {
        this.onDone();
    }

    @Override
    public boolean keyPressed(int code, int scanCode, int mod) {
        if (code == 265) {
            this.line = this.line - 1 & 3;
            this.signField.setCursorToEnd();
            return true;
        } else if (code != 264 && code != 257 && code != 335) {
            return this.signField.keyPressed(code) || super.keyPressed(code, scanCode, mod);
        } else {
            this.line = this.line + 1 & 3;
            this.signField.setCursorToEnd();
            return true;
        }
    }

    @Override
    public void render(PoseStack stack, int mouseX, int mouseY, float partialTick) {
        Lighting.setupForFlatItems();
        this.renderBackground(stack);
        drawCenteredString(stack, this.font, this.title, this.width/2, 40, 16777215);
        stack.pushPose();
        stack.translate((this.width/2D), 0.0D, 50.0D);
        stack.scale(93.75F, -93.75F, 93.75F);
        stack.translate(0.0D, -1.3125D, 0.0D);

        boolean flag1 = this.frame / 6 % 2 == 0;
        stack.pushPose();
        stack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        stack.translate(0, -1.15, 0);
        MultiBufferSource.BufferSource multibuffersource$buffersource = this.minecraft.renderBuffers().bufferSource();
        Material material = new Material(SIGN_SHEET, new ResourceLocation("biomebackport", "entity/signs/hanging/" + this.woodType.name()));
        VertexConsumer vertexconsumer = material.buffer(multibuffersource$buffersource, this.signModel::renderType);
        this.signModel.vchains.visible = false;
        this.signModel.root.render(stack, vertexconsumer, 15728880, OverlayTexture.NO_OVERLAY);
        stack.popPose();
        stack.translate(0.0D, -0.04, 0.046666667F);
        stack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.sign.getColor().getTextColor();
        int j = this.signField.getCursorPos();
        int k = this.signField.getSelectionPos();
        int l = this.line * 10 - this.messages.length * 5;
        Matrix4f matrix4f = stack.last().pose();

        for(int i1 = 0; i1 < this.messages.length; ++i1) {
            String s = this.messages[i1];
            if (s != null) {
                if (this.font.isBidirectional()) {
                    s = this.font.bidirectionalShaping(s);
                }

                float f3 = (float)(-this.minecraft.font.width(s) / 2);
                this.minecraft.font.drawInBatch(s, f3, (float)(i1 * 10 - this.messages.length * 5), i, false,
                        matrix4f, multibuffersource$buffersource, false, 0, 15728880, false);
                if (i1 == this.line && j >= 0 && flag1) {
                    int j1 = this.minecraft.font.width(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
                    int k1 = j1 - this.minecraft.font.width(s) / 2;
                    if (j >= s.length()) {
                        this.minecraft.font.drawInBatch("_", (float)k1, (float)l, i, false, matrix4f,
                                multibuffersource$buffersource, false, 0, 15728880, false);
                    }
                }
            }
        }

        multibuffersource$buffersource.endBatch();

        for(int i3 = 0; i3 < this.messages.length; ++i3) {
            String s1 = this.messages[i3];
            if (s1 != null && i3 == this.line && j >= 0) {
                int j3 = this.minecraft.font.width(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
                int k3 = j3 - this.minecraft.font.width(s1) / 2;
                if (flag1 && j < s1.length()) {
                    fill(stack, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
                }

                if (k != j) {
                    int l3 = Math.min(j, k);
                    int l1 = Math.max(j, k);
                    int i2 = this.minecraft.font.width(s1.substring(0, l3)) - this.minecraft.font.width(s1) / 2;
                    int j2 = this.minecraft.font.width(s1.substring(0, l1)) - this.minecraft.font.width(s1) / 2;
                    int k2 = Math.min(i2, j2);
                    int l2 = Math.max(i2, j2);
                    Tesselator tesselator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tesselator.getBuilder();
                    RenderSystem.setShader(GameRenderer::getPositionColorShader);
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferbuilder.begin(VertexFormat.Mode.QUADS, DefaultVertexFormat.POSITION_COLOR);
                    bufferbuilder.vertex(matrix4f, (float)k2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)l2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)l2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)k2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    BufferUploader.drawWithShader(bufferbuilder.end());
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        stack.popPose();
        Lighting.setupFor3DItems();
        super.render(stack, mouseX, mouseY, partialTick);
    }
}