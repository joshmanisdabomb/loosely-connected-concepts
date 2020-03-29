package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.capability.SpreaderCapability;
import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.SpreaderInterfaceTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.wrapper.InvWrapper;

public class SpreaderInterfaceContainer extends Container implements LCCContainerHelper {

    private final SlotManager sm;

    private final SpreaderInterfaceTileEntity te;

    public final SpreaderCapability oldSettings;

    private final PlayerEntity player;

    public SpreaderInterfaceContainer(int windowId, SpreaderInterfaceTileEntity te, SpreaderCapability oldSettings, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.spreader_interface, windowId);
        this.te = te;
        this.oldSettings = oldSettings;

        this.sm = this.createSlotManager(this::addSlot);

        this.player = player;

        sm.addPlayerSlots(new InvWrapper(inv), 36, 149);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.spreader_interface);
    }

    @Override
    public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
        return sm.transferStackInSlot(player, slotID);
    }
}
