package com.myst.biomebackport.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.jetbrains.annotations.Nullable;

public class CustomSoundButtonBlock extends ButtonBlock {
    private final SoundEvent sound;
    public CustomSoundButtonBlock(boolean sensitive, Properties properties, SoundEvent sound) {
        super(sensitive, properties);
        this.sound = sound;
    }

    @Override
    protected SoundEvent getSound(boolean pIsOn) {
        return sound;
    }
}
