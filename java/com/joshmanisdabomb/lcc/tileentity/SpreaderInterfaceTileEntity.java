package com.joshmanisdabomb.lcc.tileentity;

import com.joshmanisdabomb.lcc.container.SpreaderInterfaceContainer;
import com.joshmanisdabomb.lcc.registry.LCCTileEntities;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.LockCode;

import javax.annotation.Nullable;

public class SpreaderInterfaceTileEntity extends TileEntity implements INamedContainerProvider {

    private ITextComponent customName;

    public SpreaderInterfaceTileEntity() {
        super(LCCTileEntities.spreader_interface);
    }

    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains("CustomName", 8)) {
            this.customName = ITextComponent.Serializer.fromJson(compound.getString("CustomName"));
        }
    }

    public CompoundNBT write(CompoundNBT compound) {
        super.write(compound);
        if (this.customName != null) {
            compound.putString("CustomName", ITextComponent.Serializer.toJson(this.customName));
        }
        return compound;
    }

    @Override
    public ITextComponent getDisplayName() {
        return this.customName != null ? this.customName : new TranslationTextComponent("container.lcc.spreader_interface");
    }

    @Nullable
    @Override
    public Container createMenu(int i, PlayerInventory playerInventory, PlayerEntity playerEntity) {
        return new SpreaderInterfaceContainer(i, this, playerEntity, playerInventory);
    }

    public void setCustomName(ITextComponent displayName) {
        this.customName = displayName;
    }

}
