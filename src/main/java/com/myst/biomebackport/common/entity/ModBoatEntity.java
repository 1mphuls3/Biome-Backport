package com.myst.biomebackport.common.entity;

import com.myst.biomebackport.core.registry.ItemRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.Nonnull;

public class ModBoatEntity extends Boat {
    private final RegistryObject<Item> boatItem;

    public ModBoatEntity(EntityType<? extends ModBoatEntity> type, Level level, RegistryObject<Item> boatItem) {
        super(type, level);
        this.boatItem = boatItem;
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (this.boatItem == ItemRegistry.BAMBOO_RAFT) {
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
