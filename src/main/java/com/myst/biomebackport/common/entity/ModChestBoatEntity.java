package com.myst.biomebackport.common.entity;

import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.entity.vehicle.ChestBoat;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.network.NetworkHooks;
import net.minecraftforge.registries.RegistryObject;

public class ModChestBoatEntity extends ChestBoat {
    private final RegistryObject<Item> boatItem;

    public ModChestBoatEntity(EntityType<? extends ModChestBoatEntity> type, Level level, RegistryObject<Item> boatItem) {
        super(type, level);
        this.boatItem = boatItem;
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (this.boatItem == ItemRegistry.BAMBOO_CHEST_RAFT) {
            passenger.setPos(passenger.position().x, passenger.position().y+7/16F, passenger.position().z);
        }
    }

    @Override
    public Item getDropItem() {
        return boatItem.get();
    }

    @Override
    public Packet<?> getAddEntityPacket() {
        return new ClientboundAddEntityPacket(this);
    }
}
