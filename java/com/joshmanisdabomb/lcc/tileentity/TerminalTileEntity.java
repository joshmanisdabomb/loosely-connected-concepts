package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.container.TerminalContainer;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TerminalTileEntity extends TileEntity implements INamedContainerProvider {

    public ITextComponent customName;

    public CompoundNBT state = new CompoundNBT();

    public TerminalTileEntity() {
        super(LCCTileEntities.terminal);
    }

    @Override
    public void read(CompoundNBT tag) {
        this.state = tag.getCompound("state");
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        tag.put("state", this.state);
        return super.write(tag);
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        CompoundNBT tag = this.write(new CompoundNBT());
        return new SUpdateTileEntityPacket(getPos(), 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        this.read(pkt.getNbtCompound());
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new TerminalContainer(i, this, playerEntity, playerInventory);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("block.lcc.terminal");
    }

    public void receiveState() {

    }

}
