package yam.items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import yam.YetAnotherMod;
import yam.entity.EntityFly;

public class ItemFlyswat extends ItemGeneric {

	public ItemFlyswat(String texture) {
		super(texture);
		this.setMaxDamage(9);
		this.setMaxStackSize(1);
	}
	
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if (!par2World.isRemote) {
			boolean flag = false;
			AxisAlignedBB aabb = par3EntityPlayer.boundingBox;
			if (aabb != null) {
				List entities = par2World.getEntitiesWithinAABBExcludingEntity(par3EntityPlayer, aabb.expand(8.0D, 8.0D, 8.0D));
				for (Object e : entities) {
					if (e instanceof EntityFly) {
						flag = true;
						((EntityFly)e).attackEntityFrom(DamageSource.causePlayerDamage(par3EntityPlayer), 10.0F);
					}
				}
			}
			if (flag) {
				par1ItemStack.damageItem(1, par3EntityPlayer);
				par2World.playSoundAtEntity(par3EntityPlayer, "note.snare", 1, 2);
			}
		}

		return par1ItemStack;
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Targets: " + EnumChatFormatting.WHITE + "Flies");
		list.add(EnumChatFormatting.GRAY + "Effect: " + EnumChatFormatting.RED + "Kill");
		list.add(EnumChatFormatting.GRAY + "Radius: " + EnumChatFormatting.DARK_AQUA + "4.0 blocks");
	}

}
