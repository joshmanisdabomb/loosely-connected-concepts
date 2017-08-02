package com.joshmanisdabomb.aimagg.data.world;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.container.inventory.InventorySpreaderInterface;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class SpreaderData extends WorldSavedData {

	private static SpreaderData instance = null;
	private final static String key = Constants.MOD_ID + "_spreader_colors";

	public NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
	
	private InventorySpreaderInterface isi = null;
	
	private World world;
	
	public SpreaderData() {
		this(key);
	}

	public SpreaderData(String name) {
		super(name);
	}

	public static SpreaderData getInstance(World world) {
        if (instance != null) {
            return instance;
        }
        instance = (SpreaderData)world.getMapStorage().getOrLoadData(SpreaderData.class, key);
        if (instance == null) {
            instance = new SpreaderData(key);
        }
        instance.setWorld(world);
        return instance;
    }
	
	private void setWorld(World world) {
		this.world = world;
	}

	public void save(World world) {
        world.getMapStorage().setData(key, this);
        markDirty();
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.inventory = NonNullList.<ItemStack>withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(nbt, this.inventory);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        ItemStackHelper.saveAllItems(nbt, this.inventory);
	    return nbt;
	}

	public int getSpeed(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+0);
		return is.getCount();
	}

	public int getDamage(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+1);
		return is.getCount();
	}

	public int getRange(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+2);
		return is.getMetadata() == AimaggItemUpgradeCard.UpgradeCardType.SI_INFINITE_RANGE.getMetadata() ? 65 : is.getCount();
	}
	
	public boolean hasInfiniteRange(EnumDyeColor dyeColor) {
		return getRange(dyeColor) == 65;
	}

	public int getSpread(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+1);
		return is.getCount();
	}

	public boolean isEating(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+4);
		return is.getCount() > 0;
	}

	public boolean spreadsInGround(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+5);
		return is.getCount() > 0;
	}

	public boolean spreadsInLiquid(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+6);
		return is.getCount() > 0;
	}

	public boolean spreadsInAir(EnumDyeColor dyeColor) {
		ItemStack is = this.inventory.get((dyeColor.getMetadata()*8)+7);
		return is.getCount() > 0;
	}

	public InventorySpreaderInterface getInventory() {
        if (isi == null) {
        	isi = new InventorySpreaderInterface(world);
            isi.setFromStacks(this.inventory);
            isi.markDirty();
        }
        return isi;
	}

	public void setStacks(NonNullList<ItemStack> nonNullList) {
		this.inventory = nonNullList;
        this.save(world);
	}
	
	public int getSizeInventory() {
		return 16*8;
	}
	
}