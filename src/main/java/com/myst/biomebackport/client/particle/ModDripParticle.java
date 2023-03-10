package com.myst.biomebackport.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.awt.*;

@OnlyIn(Dist.CLIENT)
public class ModDripParticle extends TextureSheetParticle {
    private final Fluid type;
    protected boolean isGlowing;

    ModDripParticle(ClientLevel pLevel, double pX, double pY, double pZ, Fluid pType) {
        super(pLevel, pX, pY, pZ);
        this.setSize(0.01F, 0.01F);
        this.gravity = 0.06F;
        this.type = pType;
    }

    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    public int getLightColor(float pPartialTick) {
        return this.isGlowing ? 240 : super.getLightColor(pPartialTick);
    }

    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;
        this.preMoveUpdate();
        if (!this.removed) {
            this.yd -= (double)this.gravity;
            this.move(this.xd, this.yd, this.zd);
            this.postMoveUpdate();
            if (!this.removed) {
                this.xd *= (double)0.98F;
                this.yd *= (double)0.98F;
                this.zd *= (double)0.98F;
                BlockPos blockpos = new BlockPos(this.x, this.y, this.z);
                FluidState fluidstate = this.level.getFluidState(blockpos);
                if (fluidstate.getType() == this.type && this.y < (double)((float)blockpos.getY() + fluidstate.getHeight(this.level, blockpos))) {
                    this.remove();
                }

            }
        }
    }
    protected void preMoveUpdate() {
        if (this.lifetime-- <= 0) {
            this.remove();
        }

    }
    protected void postMoveUpdate() {
    }

    @OnlyIn(Dist.CLIENT)
    static class ModFallingDripParticle extends ModDripParticle {
        ModFallingDripParticle(ClientLevel level, double x, double y, double z, Fluid type, int lifetime) {
            super(level, x, y, z, type);
            this.lifetime = lifetime;
        }

        ModFallingDripParticle(ClientLevel level, double x, double y, double z, Fluid type) {
            this(level, x, y, z, type, (int)(64.0D / (Math.random() * 0.8D + 0.2D)));
        }

        @Override
        protected void postMoveUpdate() {
            if (this.onGround) {
                this.remove();
            }

        }
    }
    @OnlyIn(Dist.CLIENT)
    public static class CherryFallParticleProvider implements ParticleProvider<SimpleParticleType> {
        protected final SpriteSet sprite;
        private final RandomSource random;

        public CherryFallParticleProvider(SpriteSet pSprites) {
            this.sprite = pSprites;
            this.random = RandomSource.create();
        }

        public Particle createParticle(SimpleParticleType pType, ClientLevel pLevel, double pX, double pY, double pZ, double pXSpeed, double pYSpeed, double pZSpeed) {
            int i = (int)(64.0F / Mth.randomBetween(this.random, 0.1F, 0.9F));
            ModDripParticle dripparticle = new ModFallingDripParticle(pLevel, pX, pY, pZ, Fluids.EMPTY, i);
            dripparticle.gravity = 0.004F;
            Color color = new Color(209, 147, 180);
            dripparticle.setColor(color.getRed()/255F, color.getGreen()/255F, color.getBlue()/255F);
            dripparticle.pickSprite(this.sprite);
            return dripparticle;
        }
    }
}
