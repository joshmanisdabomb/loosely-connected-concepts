package com.joshmanisdabomb.lcc.container;

import net.minecraft.block.BlockState;
import net.minecraft.inventory.container.Slot;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public interface LCCContainerHelper {

    Slot addSlotFromHelper(Slot slot);

    default int traitAddSlots(IItemHandler handler, int x, int y, int index, int columns, int rows) {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < columns; i++) {
                addSlotFromHelper(new SlotItemHandler(handler, index++, x + (18 * i), y + (18 * j)));
            }
        }
        return index;
    }

    default void traitAddPlayerSlots(IItemHandler inventory, int x, int y) {
        //Main inventory
        this.traitAddSlots(inventory, x, y, 9, 9, 3);

        //Hotbar
        this.traitAddSlots(inventory, x, y + 58, 0, 9, 18);
    }

}
