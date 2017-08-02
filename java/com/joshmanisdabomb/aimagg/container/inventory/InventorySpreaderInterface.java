package com.joshmanisdabomb.aimagg.container.inventory;

import com.joshmanisdabomb.aimagg.data.world.SpreaderData;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.IInventoryChangedListener;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;

public class InventorySpreaderInterface extends InventoryBasic {

	private final World world;
	
	public InventorySpreaderInterface(World w) {
		super("container.aimagg_spreader_interface", false, 16*8);
		this.world = w;
		this.addInventoryChangeListener(new InventorySpreaderInterfaceChangedListener());
	}
	
	@Override
	public int getSizeInventory() {
		return 16*8;
	}
	
	public void setFromStacks(NonNullList<ItemStack> inv) {
		for (int i = 0; i < this.getSizeInventory(); i++) {
			this.setInventorySlotContents(i, inv.get(i));
		}
	}
	
	private NonNullList<ItemStack> getAsStacks() {
		NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        for (int i = 0; i < this.getSizeInventory(); i++) {
        	inventory.set(i, this.getStackInSlot(i));
        }
		return inventory;
	}
	
	public void updateWorld() {
		SpreaderData.getInstance(this.world).setStacks(this.getAsStacks());
	}
	
	@Override
	public void openInventory(EntityPlayer player) {
		super.openInventory(player);
		SpreaderData.getInstance(player.world);
	}
	
	@Override
	public void closeInventory(EntityPlayer player) {
		super.closeInventory(player);
		this.updateWorld();
	}

	public class InventorySpreaderInterfaceChangedListener implements IInventoryChangedListener {

		@Override
		public void onInventoryChanged(IInventory invBasic) {
			
		}

	}

}
