package com.joshmanisdabomb.lcc.container;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface LCCContainerHelper {

    default SlotManager createSlotManager(Function<Slot, Slot> creator) {
        return new SlotManager((Container) this, creator);
    }

    class SlotManager {

        private final Container container;
        private final Function<Slot, Slot> creator;

        public final Map<String, ArrayList<Slot>> allSlots = new HashMap<>();
        public String currentGroup = "default";

        private SlotManager(Container c, Function<Slot, Slot> creator) {
            this.container = c;
            this.creator = creator;
        }

        public int addSlot(IItemHandler handler, int x, int y, int index) {
            return this.addSlots(handler, x, y, index, 1, 1);
        }

        public int addSlots(IItemHandler handler, int x, int y, int index, int columns, int rows) {
            for (int j = 0; j < rows; j++) {
                for (int i = 0; i < columns; i++) {
                    Slot s = new SlotItemHandler(handler, index++, x + (18 * i), y + (18 * j));
                    this.addToMap(currentGroup, s);
                    creator.apply(s);
                }
            }
            return index;
        }

        public void addPlayerSlots(IItemHandler inventory, int x, int y) {
            this.addSlots(inventory, x, y, 9, 9, 3); //Main Inventory
            this.addSlots(inventory, x, y + 58, 0, 9, 1); //Hotbar
        }

        private void addToMap(String group, Slot s) {
            allSlots.computeIfAbsent(group, k -> new ArrayList<>());
            allSlots.get(group).add(s);
        }

        public List<Slot> getAllSlots() {
            return allSlots.values().stream().flatMap(a -> a.stream()).collect(Collectors.toList());
        }

        public List<Slot> getSlotsInGroup(String group) {
            return allSlots.get(group);
        }

        //thank you diesieben07
        public ItemStack transferStackInSlot(PlayerEntity player, int slotID) {
            ItemStack copy = ItemStack.EMPTY;
            SlotItemHandler slot = (SlotItemHandler)this.container.getSlot(slotID);
            if (slot != null && slot.getHasStack()) {
                ItemStack stack = slot.getStack();
                copy = stack.copy();
                boolean fromPlayer = slot.getItemHandler() instanceof InvWrapper && ((InvWrapper)slot.getItemHandler()).getInv() == player.inventory;

                if (fromPlayer) {
                    if (!this.mergeStack(player.inventory, false, slot, container.inventorySlots, false)) {
                        return ItemStack.EMPTY;
                    } else {
                        return copy;
                    }
                } else {
                    boolean machineOutput = !slot.isItemValid(stack);
                    if (!this.mergeStack(player.inventory, true, slot, container.inventorySlots, !machineOutput)) {
                        return ItemStack.EMPTY;
                    } else {
                        return copy;
                    }
                }
            }
            return copy;
        }

        private boolean mergeStack(PlayerInventory playerInv, boolean mergeIntoPlayer, SlotItemHandler sourceSlot, List<Slot> slots, boolean reverse) {
            ItemStack sourceStack = sourceSlot.getStack();

            int originalSize = sourceStack.getCount();

            int len = slots.size();
            int idx;

            if (sourceStack.isStackable()) {
                idx = reverse ? len - 1 : 0;

                while (sourceStack.getCount() > 0 && (reverse ? idx >= 0 : idx < len)) {
                    SlotItemHandler targetSlot = (SlotItemHandler)slots.get(idx);
                    if ((targetSlot.getItemHandler() instanceof InvWrapper && ((InvWrapper)targetSlot.getItemHandler()).getInv() == playerInv) == mergeIntoPlayer) {
                        ItemStack target = targetSlot.getStack();
                        if (ItemStack.areItemsEqual(sourceStack, target)) {
                            int targetMax = Math.min(targetSlot.getSlotStackLimit(), target.getMaxStackSize());
                            int toTransfer = Math.min(sourceStack.getCount(), targetMax - target.getCount());
                            if (toTransfer > 0) {
                                target.grow(toTransfer);
                                sourceStack.shrink(toTransfer);
                                targetSlot.onSlotChanged();
                            }
                        }
                    }

                    if (reverse) {
                        idx--;
                    } else {
                        idx++;
                    }
                }
                if (sourceStack.isEmpty()) {
                    sourceSlot.putStack(ItemStack.EMPTY);
                    return true;
                }
            }

            idx = reverse ? len - 1 : 0;
            while (reverse ? idx >= 0 : idx < len) {
                SlotItemHandler targetSlot = (SlotItemHandler)slots.get(idx);
                if ((targetSlot.getItemHandler() instanceof InvWrapper && ((InvWrapper)targetSlot.getItemHandler()).getInv() == playerInv) == mergeIntoPlayer
                    && !targetSlot.getHasStack() && targetSlot.isItemValid(sourceStack)) {
                    targetSlot.putStack(sourceStack);
                    sourceSlot.putStack(ItemStack.EMPTY);
                    return true;
                }

                if (reverse) {
                    idx--;
                } else {
                    idx++;
                }
            }

            if (sourceStack.getCount() != originalSize) {
                sourceSlot.onSlotChanged();
                return true;
            }
            return false;
        }

    }

}
