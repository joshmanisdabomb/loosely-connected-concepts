package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.ComputingTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.state.properties.SlabType;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ComputingContainer extends Container implements LCCContainerHelper {

    private final SlotManager sm;

    public final ComputingTileEntity te;

    private final PlayerEntity player;

    public final ComputingTileEntity.ComputingModule module;
    public final SlabType moduleLocation;

    public ComputingContainer(int windowId, ComputingTileEntity te, SlabType module, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.computing, windowId);
        this.te = te;
        this.player = player;

        this.module = te.getModule(module);
        this.moduleLocation = module;

        this.sm = this.createSlotManager(this::addSlot);

        this.module.inventory.ifPresent(h -> {
            this.module.addSlots(sm, h);
        });
        sm.addPlayerSlots(new InvWrapper(inv), this.module.type.playerInvX, this.module.type.playerInvY);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.computing);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
        return sm.transferStackInSlot(player, slotID);
    }

}
