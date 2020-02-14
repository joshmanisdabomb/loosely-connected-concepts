package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.block.AtomicBombBlock;
import com.joshmanisdabomb.lcc.container.AtomicBombContainer;
import com.joshmanisdabomb.lcc.entity.AtomicBombEntity;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCItems;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AtomicBombTileEntity extends TileEntity implements INamedContainerProvider {

    public ITextComponent customName;
    public final LazyOptional<IItemHandlerModifiable> inventory = LazyOptional.of(() -> new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            markDirty();
        }

        @Override
        public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
            switch (slot) {
                case 0: return stack.getItem() == Items.TNT;
                case 1: return stack.getItem() == LCCItems.enriched_uranium_nugget;
                default: return stack.getItem() == LCCBlocks.enriched_uranium_storage.asItem();
            }
        }

        @Override
        protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
            return 1;
        }

        @Override
        public int getSlotLimit(int slot) {
            return 1;
        }
    });

    public AtomicBombTileEntity() {
        super(LCCTileEntities.atomic_bomb);
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

    @Override
    public CompoundNBT getUpdateTag() {
        return this.write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        super.handleUpdateTag(tag);
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
        return this.customName != null ? this.customName : new TranslationTextComponent("block.lcc.atomic_bomb");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new AtomicBombContainer(i, this, playerEntity, playerInventory);
    }

    public boolean canDetonate() {
        IItemHandlerModifiable handler = inventory.orElseThrow(RuntimeException::new);
        if (handler.getStackInSlot(0).isEmpty() || handler.getStackInSlot(1).isEmpty()) return false;
        for (int i = 2; i <= 6; i++) {
            if (!handler.getStackInSlot(i).isEmpty()) return true;
        }
        return false;
    }

    public void detonate(PlayerEntity player) {
        if (this.canDetonate() && !this.getWorld().isRemote) {
            Direction facing = this.getBlockState().get(AtomicBombBlock.FACING);
            AtomicBombEntity e = new AtomicBombEntity(this.getWorld(), (double)((float)pos.getX() + 0.5F), (double)pos.getY(), (double)((float)pos.getZ() + 0.5F), facing, this, player);
            this.getWorld().addEntity(e);
            this.getWorld().removeTileEntity(this.getPos());
            this.getWorld().setBlockState(this.getPos().offset(facing), Blocks.AIR.getDefaultState(), 18);
            this.getWorld().setBlockState(this.getPos().offset(facing.getOpposite()), Blocks.AIR.getDefaultState(), 18);
            this.getWorld().setBlockState(this.getPos(), Blocks.AIR.getDefaultState(), 18);
        }
    }

}