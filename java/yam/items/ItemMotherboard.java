package yam.items;

import java.util.List;

import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemMotherboard extends ItemGeneric {

	private IIcon baseIcon, processorIcon, graphicscardIcon, memoryIcon1, memoryIcon2, memoryIcon3, nullIcon;

	public ItemMotherboard() {
		super("parts/motherboard/base");
		
		this.setMaxStackSize(1);
	}
    
    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	this.baseIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/base");
    	this.processorIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/processor");
    	this.graphicscardIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/graphicscard");
    	this.memoryIcon1 = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/memory1");
    	this.memoryIcon2 = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/memory2");
    	this.memoryIcon3 = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/motherboard/memory3");
    	this.nullIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "null");
    }
    
    public boolean requiresMultipleRenderPasses() {
    	return true;
    }
    
    public int getRenderPasses(int metadata) {
        return 4;
    }
    
    public IIcon getIcon(ItemStack stack, int renderPass)
    {
    	if (renderPass == 0) {return this.baseIcon;}
    	if (renderPass == 1 && getPowerNum(stack) > 0) {return this.processorIcon;}
        if (renderPass == 2 && getGraphicPowerNum(stack) > 0) {return this.graphicscardIcon;}
        if (renderPass == 3 && getMemoryNum(stack) > 0) {
        	switch(getMemoryNum(stack)) {
        		case 0:
                	return this.memoryIcon1;
        		case 1:
                	return this.memoryIcon2;
        		default:
                	return this.memoryIcon3;
        	}
        }
        return this.nullIcon;
    }
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    initNBTData(itemStack);
	}
	
	public static void initNBTData(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {
	    	itemStack.stackTagCompound = new NBTTagCompound();
		    itemStack.stackTagCompound.setInteger("memory", 0);
		    itemStack.stackTagCompound.setInteger("processors", 0);
		    itemStack.stackTagCompound.setInteger("gcards", 0);
		    itemStack.stackTagCompound.setInteger("fans", 0);
	    }
	}
	
	public static void initNBTData(ItemStack itemStack, int memory, int processors, int gcards, int fans) {
		if (itemStack.stackTagCompound == null) {
	    	itemStack.stackTagCompound = new NBTTagCompound();
		    itemStack.stackTagCompound.setInteger("memory", memory);
		    itemStack.stackTagCompound.setInteger("processors", processors);
		    itemStack.stackTagCompound.setInteger("gcards", gcards);
		    itemStack.stackTagCompound.setInteger("fans", fans);
	    }
	}

	public static int getMemoryNum(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return itemStack.stackTagCompound.getInteger("memory");
	}

	public static String getMemory(ItemStack itemStack) {
		int memoryInstalled = getMemoryNum(itemStack);
		if (memoryInstalled > 0) {
		    return EnumChatFormatting.GOLD + "" + (memoryInstalled * 512) + " KB";
		} else {
		    return EnumChatFormatting.DARK_GRAY + "0 KB";
		}
	}
	
	public static int getPowerNum(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return itemStack.stackTagCompound.getInteger("processors");
	}
	
	public static String getPower(ItemStack itemStack) {
		int processorsInstalled = getPowerNum(itemStack);
		if (processorsInstalled > 0) {
		    return EnumChatFormatting.GOLD + "" + (processorsInstalled * 128) + " MHz";
		} else {
		    return EnumChatFormatting.DARK_GRAY + "0 MHz";
		}
	}
	
	public static int getGraphicPowerNum(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return itemStack.stackTagCompound.getInteger("gcards");
	}
	
	public static String getGraphicPower(ItemStack itemStack) {
		int cardsInstalled = getGraphicPowerNum(itemStack);
		if (cardsInstalled > 0) {
		    return EnumChatFormatting.GOLD + "" + (cardsInstalled * 128) + " MHz";
		} else {
		    return EnumChatFormatting.DARK_GRAY + "0 MHz";
		}
	}
	
	public static int getHeat(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return (itemStack.stackTagCompound.getInteger("memory") * 50) + (itemStack.stackTagCompound.getInteger("processors") * 300) + (itemStack.stackTagCompound.getInteger("gcards") * 250);
	}

	public static int getCoolants(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return itemStack.stackTagCompound.getInteger("fans");
	}
	
	public static boolean willRun(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return false;}
		return itemStack.stackTagCompound.getInteger("memory") > 0 && itemStack.stackTagCompound.getInteger("processors") > 0;
	}
	
	public static double getExplosionChanceNum(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		if (getHeat(itemStack) <= (getCoolants(itemStack) * 600)) {return 0;}
		return Math.min(((getHeat(itemStack) - (getCoolants(itemStack) * 600)) / 8.4D), 100);
	}
	
	public static String getExplosionChance(ItemStack itemStack) {
		if (getExplosionChanceNum(itemStack) <= 0) {return EnumChatFormatting.GREEN + "0%";}
		return EnumChatFormatting.RED + String.format("%.2f", getExplosionChanceNum(itemStack)) + "%";
	}
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Memory: " + getMemory(itemstack));
		list.add(EnumChatFormatting.GRAY + "Processing Power: " + getPower(itemstack));
		list.add(EnumChatFormatting.GRAY + "Graphics Power: " + getGraphicPower(itemstack));
		list.add("");
		list.add(EnumChatFormatting.GRAY + "Works: " + (willRun(itemstack) ? (EnumChatFormatting.GREEN + "Yes") : (EnumChatFormatting.RED + "No")));
		list.add("");
		list.add(EnumChatFormatting.GRAY + "Heat Generated: " + ((getExplosionChanceNum(itemstack) <= 0) ? EnumChatFormatting.DARK_RED : EnumChatFormatting.RED) + getHeat(itemstack));
		list.add(EnumChatFormatting.GRAY + "Cooling: " + ((getExplosionChanceNum(itemstack) <= 0) ? EnumChatFormatting.AQUA : EnumChatFormatting.DARK_AQUA) + (getCoolants(itemstack) * 600));
		list.add(EnumChatFormatting.GRAY + "Explosion Chance on Action: " + getExplosionChance(itemstack));
	}

}
