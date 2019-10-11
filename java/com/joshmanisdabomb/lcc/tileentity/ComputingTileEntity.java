package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.block.network.ComputingNetwork;
import com.joshmanisdabomb.lcc.computing.ComputingModule;
import com.joshmanisdabomb.lcc.container.ComputingContainer;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;

import static com.joshmanisdabomb.lcc.block.ComputingBlock.flip;

public class ComputingTileEntity extends TileEntity implements INamedContainerProvider {

    public static final ComputingNetwork LOCAL_NETWORK = new ComputingNetwork(64, true);
    public static final ComputingNetwork WIRED_NETWORK = new ComputingNetwork(64, false);

    protected ComputingModule top = null;
    protected ComputingModule bottom = null;

    private LazyOptional<CombinedInvWrapper> totalInventory = LazyOptional.empty();

    public boolean _containerModuleIsTop;

    public ComputingTileEntity() {
        super(LCCTileEntities.computing);
    }

    @Override
    public void read(CompoundNBT tag) {
        if (tag.contains("TopModule", Constants.NBT.TAG_COMPOUND)) {
            this.top = this.readModule(tag.getCompound("TopModule"), SlabType.TOP);
        }
        if (tag.contains("BottomModule", Constants.NBT.TAG_COMPOUND)) {
            this.bottom = this.readModule(tag.getCompound("BottomModule"), SlabType.BOTTOM);
        }
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        if (this.top != null) tag.put("TopModule", this.top.write(new CompoundNBT()));
        if (this.bottom != null) tag.put("BottomModule", this.bottom.write(new CompoundNBT()));
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
        return new ComputingContainer(i, this, _containerModuleIsTop ? SlabType.TOP : SlabType.BOTTOM, playerEntity, playerInventory);
    }

    public void setModule(ComputingModule.Type type, DyeColor color, Direction facing, ITextComponent customName, SlabType location) {
        ComputingModule m = new ComputingModule(this, location, type, color, facing, customName);
        switch (location) {
            case TOP:
                this.top = m;
                break;
            case BOTTOM:
                this.bottom = m;
                break;
            default:
                break;
        }
        this.calculateTotalInventory();
    }

    public void clearModule(SlabType location) {
        switch (location) {
            case TOP:
                this.top = null;
                break;
            case BOTTOM:
                this.bottom = null;
                break;
            default:
                break;
        }
        this.calculateTotalInventory();
    }

    public ComputingModule getModule(SlabType location) {
        switch (location) {
            case TOP: return this.top;
            case BOTTOM: return this.bottom;
            default: return null;
        }
    }

    public ArrayList<ComputingModule> getInstalledModules() {
        ArrayList<ComputingModule> modules = new ArrayList<>();
        if (this.top != null) modules.add(this.top);
        if (this.bottom != null) modules.add(this.bottom);
        return modules;
    }

    public boolean isModuleConnectedAbove(SlabType location) {
        if (location == SlabType.BOTTOM) {
            ComputingModule cm = this.getModule(flip(location));
            if (cm == null) return false;
            return cm.color == this.getModule(location).color;
        } else {
            TileEntity te = this.world.getTileEntity(pos.up());
            if (te instanceof ComputingTileEntity) {
                ComputingModule cm = ((ComputingTileEntity)te).getModule(flip(location));
                if (cm == null) return false;
                return cm.color == this.getModule(location).color;
            }
            return false;
        }
    }

    public boolean isModuleConnectedBelow(SlabType location) {
        if (location == SlabType.TOP) {
            ComputingModule cm = this.getModule(flip(location));
            if (cm == null) return false;
            return cm.color == this.getModule(location).color;
        } else {
            TileEntity te = this.world.getTileEntity(pos.down());
            if (te instanceof ComputingTileEntity) {
                ComputingModule cm = ((ComputingTileEntity)te).getModule(flip(location));
                if (cm == null) return false;
                return cm.color == this.getModule(location).color;
            }
            return false;
        }
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            switch (side) {
                case UP:
                    if (this.top == null) return LazyOptional.empty();
                    else return this.top.inventory.cast();
                case DOWN:
                    if (this.bottom == null) return LazyOptional.empty();
                    else return this.bottom.inventory.cast();
                default:
                    return totalInventory.cast();
            }
        }
        return super.getCapability(cap, side);
    }

    public void calculateTotalInventory() {
        this.totalInventory = LazyOptional.empty();
        if (this.top != null && this.top.inventory.isPresent() && this.bottom != null && this.bottom.inventory.isPresent()) {
            this.top.inventory.ifPresent(h -> {
                this.bottom.inventory.ifPresent(h2 -> {
                    totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h, h2));
                });
            });
        }
        else if (this.top != null && this.top.inventory.isPresent()) this.top.inventory.ifPresent(h -> totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h)));
        else if (this.bottom != null && this.bottom.inventory.isPresent()) this.bottom.inventory.ifPresent(h -> totalInventory = LazyOptional.of(() -> new CombinedInvWrapper(h)));
    }

    @Override
    public ITextComponent getDisplayName() {
        return new TranslationTextComponent("block.lcc.computing");
    }

    private ComputingModule readModule(CompoundNBT tag, SlabType location) {
        if (!tag.contains("module", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        ComputingModule.Type module = ComputingModule.Type.values()[tag.getByte("module")];
        if (!tag.contains("color", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        DyeColor color = DyeColor.values()[tag.getByte("color")];
        if (!tag.contains("direction", Constants.NBT.TAG_ANY_NUMERIC)) return null;
        Direction direction = Direction.values()[tag.getByte("direction")];

        ITextComponent name = tag.contains("customName", Constants.NBT.TAG_STRING) ? ITextComponent.Serializer.fromJson(tag.getString("customName")) : null;

        ComputingModule m = new ComputingModule(this, location, module, color, direction, name);

        CompoundNBT invTag = tag.getCompound("inventory");
        m.inventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        if (m.type == ComputingModule.Type.COMPUTER) {
            if (tag.contains("powerState", Constants.NBT.TAG_ANY_NUMERIC)) m.powerState = tag.getBoolean("powerState");
        }

        return m;
    }

}
