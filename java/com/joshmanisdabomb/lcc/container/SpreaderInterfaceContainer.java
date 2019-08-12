package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SpreaderInterfaceContainer extends Container implements LCCContainerHelper {

    private final SpreaderInterfaceTileEntity te;

    private final PlayerEntity player;
    private final IItemHandler playerInv;

    public SpreaderInterfaceContainer(int windowId, SpreaderInterfaceTileEntity te, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.spreader_interface, windowId);
        this.te = te;
        this.player = player;
        this.playerInv = new InvWrapper(inv);

        this.traitAddPlayerSlots(this.playerInv, 36, 137);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.spreader_interface);
    }

    @Override
    public Slot addSlotFromHelper(Slot slot) {
        return this.addSlot(slot);
    }

}
