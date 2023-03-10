package com.myst.biomebackport.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.myst.biomebackport.client.DecoratedPotContainerMenu;
import com.myst.biomebackport.core.config.ServerConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

import static com.myst.biomebackport.BiomeBackport.modPath;

public class DecoratedPotScreen extends AbstractContainerScreen<DecoratedPotContainerMenu> {
    public static final ResourceLocation DECORATED_POT_GUI = modPath("textures/gui/decorated_pot.png");
    public static final ResourceLocation SLOT_TEXTURE = modPath("textures/gui/slot.png");

    public DecoratedPotScreen(DecoratedPotContainerMenu container, Inventory inventory, Component title) {
        super(container, inventory, Component.translatable("block.biomebackport.decorated_pot"));
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(PoseStack matrixStack, float partialTicks, int x, int y) {
    }

    private void renderBack(PoseStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, DECORATED_POT_GUI);
        int k = (this.width - this.imageWidth) / 2;
        int l = (this.height - this.imageHeight) / 2;
        this.blit(matrixStack, k, l, 0, 0, this.imageWidth, this.imageHeight);
    }


    private void renderSlots(PoseStack matrixStack) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SLOT_TEXTURE);

        int k = -1 + (this.width - this.imageWidth) / 2;
        int l = -1 + (this.height - this.imageHeight) / 2;

        int size = ServerConfig.SLOTS.get();

        int[] dims = DecoratedPotContainerMenu.getRatio(size);
        if (dims[0] > 9) {
            dims[0] = 9;
        }

        int yp = 17 + (18 * 3) / 2 - (9);

        int dimx;
        int xp;
        dimx = Math.min(dims[0], size);
        xp = 8 + (18 * 9) / 2 - (dimx * 18) / 2;
        for (int j = 0; j < dimx; ++j) {
            blit(matrixStack, k + xp + j * 18, l + yp + 18, 0, 0, 18, 18, 18, 18);
        }
    }

    @Override
    public void render(PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        this.renderBack(matrixStack, partialTicks, mouseX, mouseY);
        this.renderSlots(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    public boolean keyPressed(int key, int b, int c) {
        if (key == 256) {
            this.minecraft.player.closeContainer();
            return true;
        }
        return super.keyPressed(key, b, c);
    }

    @Override
    public void removed() {
        super.removed();
        Minecraft.getInstance().keyboardHandler.setSendRepeatsToGui(false);
    }

    @Override
    public void init() {
        super.init();
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.titleLabelX = (this.imageWidth - this.font.width(this.title)) / 2;
        this.titleLabelY = (this.imageHeight - (125));
    }
}