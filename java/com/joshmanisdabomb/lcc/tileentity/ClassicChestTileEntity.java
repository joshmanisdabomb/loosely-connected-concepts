package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.block.ClassicChestBlock;
import com.joshmanisdabomb.lcc.container.ClassicChestContainer;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.state.properties.ChestType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class ClassicChestTileEntity extends TileEntity implements INamedContainerProvider {

    private ITextComponent customName;

    public final LazyOptional<IItemHandler> inventory = LazyOptional.of(() -> new ItemStackHandler(27) {

        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

    });

    public ClassicChestTileEntity() {
        super(LCCTileEntities.classic_chest);
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inventory");
        inventory.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        if (tag.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(tag.getString("CustomName"));
        }
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        inventory.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inventory", compound);
        });
        if (this.customName != null) {
            tag.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        return super.write(tag);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return inventory.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("block.lcc.classic_chest");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new ClassicChestContainer(i, this, playerEntity, playerInventory);
    }

    public void setCustomName(ITextComponent displayName) {
        this.customName = displayName;
    }

    public boolean isPrimary() {
        return this.getWorld().getBlockState(this.getPos()).get(ClassicChestBlock.TYPE) != ChestType.LEFT;
    }

    public ClassicChestTileEntity getAttached() {
        BlockState state = this.getWorld().getBlockState(this.getPos());
        if (state.get(ClassicChestBlock.TYPE) == ChestType.SINGLE) return null;
        return (ClassicChestTileEntity)this.getWorld().getTileEntity(this.getPos().offset(((ClassicChestBlock)state.getBlock()).getDirectionToAttached(state)));
    }

}