package com.joshmanisdabomb.lcc.container;

import com.joshmanisdabomb.lcc.registry.LCCBlocks;
import com.joshmanisdabomb.lcc.registry.LCCContainers;
import com.joshmanisdabomb.lcc.tileentity.ClassicChestTileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.IWorldPosCallable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ClassicChestContainer extends Container implements LCCContainerHelper {

    private final ClassicChestTileEntity te;

    private final PlayerEntity player;
    private final IItemHandler playerInv;

    public ClassicChestContainer(int windowId, ClassicChestTileEntity te, PlayerEntity player, PlayerInventory inv) {
        super(LCCContainers.classic_chest, windowId);
        this.te = te;

        this.player = player;
        this.playerInv = new InvWrapper(inv);

        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(h -> {
            this.traitAddSlots(h, 8, 18, 0, 9, 3);
        });
        this.traitAddPlayerSlots(this.playerInv, 8, 85);
    }

    @Override
    public boolean canInteractWith(PlayerEntity playerIn) {
        return isWithinUsableDistance(IWorldPosCallable.of(te.getWorld(), te.getPos()), player, LCCBlocks.classic_chest);
    }

    @Override
    public Slot addSlotFromHelper(Slot slot) {
        return this.addSlot(slot);
    }

}
