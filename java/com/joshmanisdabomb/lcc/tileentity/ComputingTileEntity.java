package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;

import javax.annotation.Nullable;

public class ComputingTileEntity extends TileEntity implements IContainerProvider {

    public ComputingTileEntity() {
        super(LCCTileEntities.computing);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        return compound;
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;//new ComputerCaseContainer(i, this, playerEntity, playerInventory);
    }

}
