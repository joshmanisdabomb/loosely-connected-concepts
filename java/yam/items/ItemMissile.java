package yam.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class ItemMissile extends ItemGeneric {

	private MissileType m;

	public ItemMissile(String texture, MissileType m) {
		super(texture);
		
		this.m = m;
		
		this.setMaxStackSize(1);
	}
	
	public void onCreated(ItemStack itemStack, World world, EntityPlayer player) {
	    /*itemStack.stackTagCompound = new NBTTagCompound();
	    itemStack.stackTagCompound.setInteger("x", 0);
	    itemStack.stackTagCompound.setInteger("y", 0);
	    itemStack.stackTagCompound.setInteger("z", 0);*/
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer) {
	    if (par1ItemStack.stackTagCompound == null) {par1ItemStack.stackTagCompound = new NBTTagCompound();}
	    par1ItemStack.stackTagCompound.setInteger("x", (int)Math.round(par3EntityPlayer.posX + 0.5));
	    par1ItemStack.stackTagCompound.setInteger("y", (int)Math.round(par3EntityPlayer.posY));
	    par1ItemStack.stackTagCompound.setInteger("z", (int)Math.round(par3EntityPlayer.posX + 0.5));
		return par1ItemStack;
    }
	
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
		if (itemStack.stackTagCompound != null) {
            list.add(EnumChatFormatting.GRAY + "Destination: " + EnumChatFormatting.DARK_GRAY + itemStack.stackTagCompound.getInteger("x") + "," + itemStack.stackTagCompound.getInteger("y") + "," + itemStack.stackTagCompound.getInteger("z"));
		} else {
			list.add(EnumChatFormatting.GRAY + "Destination: " + EnumChatFormatting.DARK_RED + "Right Click to Set");
		}
	}
	
	public MissileType getMissileType() {
		return m;
	}
	
	public static enum MissileType {
		TNT(false, "missileRed"),
		NUCLEAR(true, "missilegiantnuclear");
		
		private boolean giant;
		private String texture;
		
		MissileType(boolean giant, String texture) {
			this.giant = giant;
			this.texture = texture;
		}
		
		public boolean isGiantMissile() {
			return giant;
		}
		
		public String getTexture() {
			return texture;
		}
	}

}
