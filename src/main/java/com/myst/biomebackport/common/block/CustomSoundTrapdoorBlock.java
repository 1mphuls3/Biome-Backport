package com.myst.biomebackport.common.block;

import com.myst.biomebackport.common.sound.ModSoundTypes;
import com.myst.biomebackport.common.sound.SoundRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.TrapDoorBlock;
import org.jetbrains.annotations.Nullable;

public class CustomSoundTrapdoorBlock extends TrapDoorBlock {
    private final SoundEvent sound;
    public CustomSoundTrapdoorBlock(Properties properties, SoundEvent sound) {
        super(properties);
        this.sound = sound;
    }

    @Override
    protected void playSound(@Nullable Player player, Level level, BlockPos pos, boolean isOpened) {
        level.playSound(player, pos, sound, SoundSource.BLOCKS, 1F, 1F);
    }
}
