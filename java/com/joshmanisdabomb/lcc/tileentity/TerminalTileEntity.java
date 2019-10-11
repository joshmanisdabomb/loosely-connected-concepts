package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.container.TerminalContainer;
import com.joshmanisdabomb.lcc.misc.ComputerSession;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.stream.Collectors;

import static com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity.WIRED_NETWORK;

public class TerminalTileEntity extends TileEntity implements INamedContainerProvider {

    public ITextComponent customName;

    public TerminalTileEntity() {
        super(LCCTileEntities.terminal);
    }

    @Override
    public void read(CompoundNBT tag) {
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
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

    public List<ComputingTileEntity.ComputingModule> getActiveComputers() {
        List<Pair<BlockPos, SlabType>> modules = WIRED_NETWORK.discover(this.getWorld(), new ImmutablePair<>(this.getPos(), null)).getTraversables();
        return modules.stream().map(m -> {
            TileEntity te = this.getWorld().getTileEntity(m.getLeft());
            if (te instanceof ComputingTileEntity) {
                return ((ComputingTileEntity)te).getModule(m.getRight());
            }
            return null;
        }).filter(module -> module != null && module.type == ComputingTileEntity.ComputingModuleType.COMPUTER && module.powerState).collect(Collectors.toList());
    }

    public ComputerSession getCurrentSession() {
        List<ComputingTileEntity.ComputingModule> computers = this.getActiveComputers();
        if (computers.size() != 1) return null;
        return computers.get(0).getSession();
    }

}
