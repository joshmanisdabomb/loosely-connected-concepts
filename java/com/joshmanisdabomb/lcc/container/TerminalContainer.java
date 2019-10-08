package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.TerminalTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TerminalContainer extends Container implements LCCContainerHelper {

    private final SlotManager sm;

    public final TerminalTileEntity te;

    private final PlayerEntity player;

    public TerminalContainer(int windowId, TerminalTileEntity te, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.terminal, windowId);
        this.te = te;
        this.player = player;

        this.sm = this.createSlotManager(this::addSlot);

        sm.addPlayerSlots(new InvWrapper(inv), 48, 149);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return LCCContainerHelper.isWithinUsableDistancePredicated(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, state -> LCCBlocks.terminals.containsValue(state.getBlock()));
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
        return sm.transferStackInSlot(player, slotID);
    }

}
