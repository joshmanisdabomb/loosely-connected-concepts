package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.AtomicBombTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class AtomicBombContainer extends Container implements LCCContainerHelper {

    private final SlotManager sm;

    public final AtomicBombTileEntity te;

    private final PlayerEntity player;

    public AtomicBombContainer(int windowId, AtomicBombTileEntity te, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.atomic_bomb, windowId);
        this.te = te;
        this.player = player;

        this.sm = this.createSlotManager(this::addSlot);

        te.inventory.ifPresent(h -> {
            sm.addSlot(h, 16, 22, 0);
            sm.addSlot(h, 44, 22, 1);
            sm.addSlots(h, 72, 22, 2, 5, 1);
        });

        sm.addPlayerSlots(new InvWrapper(inv), 8, 91);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.atomic_bomb);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
        System.out.println("hello");
        return sm.transferStackInSlot(player, slotID);
    }

}
