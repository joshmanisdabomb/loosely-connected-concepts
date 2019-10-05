package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.LCC;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.IContainerProvider;
import net.minecraft.item.DyeColor;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.state.properties.SlabType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;

public class ComputingTileEntity extends TileEntity implements IContainerProvider {

    protected ComputingModuleType topModule = null;
    protected ComputingModuleType bottomModule = null;

    protected DyeColor topColor = null;
    protected DyeColor bottomColor = null;

    protected Direction topDirection = null;
    protected Direction bottomDirection = null;

    protected ITextComponent topName = null;
    protected ITextComponent bottomName = null;

    public ComputingTileEntity() {
        super(LCCTileEntities.computing);
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

    public void read(CompoundNBT tag) {
        if (tag.contains("TopModule", Constants.NBT.TAG_ANY_NUMERIC)) this.topModule = ComputingModuleType.values()[tag.getByte("TopModule")];
        if (tag.contains("BottomModule", Constants.NBT.TAG_ANY_NUMERIC)) this.bottomModule = ComputingModuleType.values()[tag.getByte("BottomModule")];
        if (tag.contains("TopColor", Constants.NBT.TAG_ANY_NUMERIC)) this.topColor = DyeColor.values()[tag.getByte("TopColor")];
        if (tag.contains("BottomColor", Constants.NBT.TAG_ANY_NUMERIC)) this.bottomColor = DyeColor.values()[tag.getByte("BottomColor")];
        if (tag.contains("TopDirection", Constants.NBT.TAG_ANY_NUMERIC)) this.topDirection = Direction.values()[tag.getByte("TopDirection")];
        if (tag.contains("BottomDirection", Constants.NBT.TAG_ANY_NUMERIC)) this.bottomDirection = Direction.values()[tag.getByte("BottomDirection")];
        if (tag.contains("TopCustomName", Constants.NBT.TAG_STRING)) this.topName = ITextComponent.Serializer.fromJson(tag.getString("TopCustomName"));
        if (tag.contains("BottomCustomName", Constants.NBT.TAG_STRING)) this.bottomName = ITextComponent.Serializer.fromJson(tag.getString("BottomCustomName"));
        super.read(tag);
    }

    public CompoundNBT write(CompoundNBT tag) {
        if (this.topModule != null) tag.putByte("TopModule", (byte)this.topModule.ordinal());
        if (this.bottomModule != null) tag.putByte("BottomModule", (byte)this.bottomModule.ordinal());
        if (this.topColor != null) tag.putByte("TopColor", (byte)this.topColor.ordinal());
        if (this.bottomColor != null) tag.putByte("BottomColor", (byte)this.bottomColor.ordinal());
        if (this.topDirection != null) tag.putByte("TopDirection", (byte)this.topDirection.ordinal());
        if (this.bottomDirection != null) tag.putByte("BottomDirection", (byte)this.bottomDirection.ordinal());
        if (this.topName != null) tag.putString("TopCustomName", ITextComponent.Serializer.toJson(this.topName));
        if (this.bottomName != null) tag.putString("BottomCustomName", ITextComponent.Serializer.toJson(this.bottomName));
        return super.write(tag);
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return null;//new ComputerCaseContainer(i, this, playerEntity, playerInventory);
    }

    public void setModule(ComputingModuleType type, DyeColor color, Direction facing, ITextComponent customName, SlabType module) {
        switch (module) {
            case TOP:
                this.topModule = type;
                this.topColor = color;
                this.topDirection = facing;
                this.topName = customName;
                break;
            case BOTTOM:
                this.bottomModule = type;
                this.bottomColor = color;
                this.bottomDirection = facing;
                this.bottomName = customName;
                break;
            default:
                break;
        }
    }

    public void clearModule(SlabType module) {
        this.setModule(null, null, null, null, module);
    }

    public ComputingModuleType getModuleType(SlabType module) {
        switch (module) {
            case TOP: return this.topModule;
            case BOTTOM: return this.bottomModule;
            default: return null;
        }
    }

    public DyeColor getModuleColor(SlabType module) {
        switch (module) {
            case TOP: return this.topColor;
            case BOTTOM: return this.bottomColor;
            default: return null;
        }
    }

    public Direction getModuleDirection(SlabType module) {
        switch (module) {
            case TOP: return this.topDirection;
            case BOTTOM: return this.bottomDirection;
            default: return null;
        }
    }

    public ITextComponent getModuleCustomName(SlabType module) {
        switch (module) {
            case TOP: return this.topName;
            case BOTTOM: return this.bottomName;
            default: return null;
        }
    }

    public enum ComputingModuleType {
        CASING(new ResourceLocation(LCC.MODID, "textures/entity/tile/computer_casing.png"));

        private final ResourceLocation tileEntityTexture;

        ComputingModuleType(ResourceLocation tileEntityTexture) {
            this.tileEntityTexture = tileEntityTexture;
        }

        public ResourceLocation getTexture() {
            return this.tileEntityTexture;
        }
    }

}
