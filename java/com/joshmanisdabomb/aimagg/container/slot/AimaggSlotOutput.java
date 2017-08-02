package com.joshmanisdabomb.aimagg.container.slot;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class AimaggSlotOutput extends Slot {

	private final EntityPlayer thePlayer;
	private int removeCount;

	private Item item;
	private int stackSize;

	public AimaggSlotOutput(IInventory inventory, int index, int xPosition, int yPosition, EntityPlayer player) {
		super(inventory, index, xPosition, yPosition);
        this.thePlayer = player;
	}
	
	@Override
	public boolean isItemValid(ItemStack stack) {
        return false;
    }
	
	/**
     * Decrease the size of the stack in slot (first int arg) by the amount of the second int arg. Returns the new
     * stack.
     */
    @Override
    public ItemStack decrStackSize(int amount)
    {
        if (this.getHasStack())
        {
            this.removeCount += Math.min(amount, this.getStack().getCount());
        }

        return super.decrStackSize(amount);
    }

    @Override
    public ItemStack onTake(EntityPlayer playerIn, ItemStack stack)  {
        this.onCrafting(stack);
        return super.onTake(playerIn, stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood. Typically increases an
     * internal count then calls onCrafting(item).
     */
    @Override
    protected void onCrafting(ItemStack stack, int amount)
    {
        this.removeCount += amount;
        this.onCrafting(stack);
    }

    /**
     * the itemStack passed in is the output - ie, iron ingots, and pickaxes, not ore and wood.
     */
    @Override
    protected void onCrafting(ItemStack stack)
    {
        stack.onCrafting(this.thePlayer.world, this.thePlayer, this.removeCount);

        this.removeCount = 0;
    }
	

}