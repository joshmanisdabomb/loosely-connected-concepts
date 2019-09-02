package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.ClassicChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ClassicChestContainer extends Container implements LCCContainerHelper {

    private final SlotManager sm;

    private final ClassicChestTileEntity te;

    public final boolean doubleChest;

    private final PlayerEntity player;

    public ClassicChestContainer(int windowId, ClassicChestTileEntity te, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.classic_chest, windowId);
        this.te = te;
        this.player = player;

        this.sm = this.createSlotManager(this::addSlot);

        ClassicChestTileEntity te2 = te.getAttached();
        this.doubleChest = te2 != null;

        (te.isPrimary() ? te : te2).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            sm.addSlots(h, 8, 18, 0, 9, 3);
        });
        if (doubleChest) {
            (te.isPrimary() ? te2 : te).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
                sm.addSlots(h, 8, 72, 0, 9, 3);
            });
        }
        sm.addPlayerSlots(new InvWrapper(inv), 8, 85 + (doubleChest ? 54 : 0));
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.classic_chest);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
        return sm.transferStackInSlot(player, slotID);
    }

}
