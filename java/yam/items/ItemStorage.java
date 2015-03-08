package yam.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemStorage extends ItemGeneric {
	
	private int size,stacks;
	private String textureStr;
	private IIcon icon,label,nullIcon;

	public ItemStorage(String texture, int size) {
		super("parts/storage/" + texture);
		this.textureStr = texture;
		this.size = size;
		this.stacks = (int)Math.ceil(size/64D);

		this.setMaxStackSize(1);
	}
	
	public boolean showDurabilityBar(ItemStack stack)
    {
        return true;
    }
	
	public double getDurabilityForDisplay(ItemStack stack)
    {
        return ((double)(this.getSpaceUsed(stack))/(double)this.getSpace());
    }
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    initNBTData(itemStack);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister par1IconRegister)
    {
    	this.icon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/storage/" + textureStr);
    	this.label = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "parts/storage/label/" + textureStr);
    	this.nullIcon = par1IconRegister.registerIcon(YetAnotherMod.MODID + ":" + "null");
    }
	
	public boolean requiresMultipleRenderPasses() {
    	return true;
    }
	
	public IIcon getIcon(ItemStack stack, int renderPass) {
    	if (renderPass == 0) {return this.icon;}
    	if (renderPass == 1 && getLabelColor(stack) > -1) {return this.label;}
        return this.nullIcon;
    }
	
	@SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack par1ItemStack, int par2)
    {
        return (par2 == 1) ? Math.max(getLabelColor(par1ItemStack), 0) : 16777215;
    }
	
	public void initNBTData(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {
	    	itemStack.stackTagCompound = new NBTTagCompound();
		    itemStack.stackTagCompound.setInteger("label", -1);
		    
			NBTTagList filesystem = new NBTTagList();
	    	
			for (int i = 0; i < getSizeInStacks(); ++i)
	        {
                NBTTagCompound item = new NBTTagCompound();
                (new ItemStack(YetAnotherMod.neon, 1)).writeToNBT(item);
                filesystem.appendTag(item);
	        }
	    	
		    itemStack.stackTagCompound.setTag("filesystem", filesystem);
	    }
	}
	
	public static int getLabelColor(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}
		return itemStack.stackTagCompound.getInteger("label");
	}
	
	public int getSpaceUsed(ItemStack itemStack) {
		if (itemStack.stackTagCompound == null) {return 0;}		    
			
		NBTTagList filesystem = itemStack.stackTagCompound.getTagList("filesystem", Constants.NBT.TAG_COMPOUND);
		int spaceUsed = 0;
		
		for (int i = 0; i < getSizeInStacks(); ++i)
        {
            ItemStack is = ItemStack.loadItemStackFromNBT(filesystem.getCompoundTagAt(i));
            if (is != null) {
            	spaceUsed += is.stackSize;
            }
        }
    	
	    return spaceUsed;
	}

	public int getSpace() {
		return size;
	}
	
	public int getSizeInStacks() {
		return stacks;
	}

	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Total Space on Disk: " + EnumChatFormatting.GOLD + this.getSpace() + " MB");
		list.add(EnumChatFormatting.GRAY + "Used Space: " + EnumChatFormatting.BLUE + this.getSpaceUsed(itemstack) + " MB");
		list.add(EnumChatFormatting.GRAY + "Free Space: " + EnumChatFormatting.LIGHT_PURPLE + (this.getSpace()-this.getSpaceUsed(itemstack)) + " MB");
		list.add(EnumChatFormatting.GRAY + "Percentage Used: " + EnumChatFormatting.DARK_AQUA + String.format("%.2f", this.getDurabilityForDisplay(itemstack)*100D) + "%");
	}

}
