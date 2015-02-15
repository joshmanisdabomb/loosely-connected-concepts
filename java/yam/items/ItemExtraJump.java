package yam.items;

import java.util.Random;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class ItemExtraJump extends ItemGeneric {

	private ParticleType particleType;
	private double height;
	
	Random rand = new Random();
	
	public ItemExtraJump(String texture, ParticleType particle, double height) {
		super(texture);
		this.setMaxDamage(1);
		this.particleType = particle;
		this.height = height;
	}

	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		if (!par3EntityPlayer.isWet()) {
			if (par2World.isRemote) { 
				for (int i = 0; i < 50; i++) {
					ParticleHandler.spawnParticle(par2World, particleType, par3EntityPlayer.posX + (rand.nextDouble() - 0.5), par3EntityPlayer.posY - 1, par3EntityPlayer.posZ + (rand.nextDouble() - 0.5), (rand.nextDouble() - 0.5) / 2, (rand.nextDouble() - 0.5) / 2, (rand.nextDouble() - 0.5) / 2);
				}
			}
			par3EntityPlayer.motionY = height;
			par3EntityPlayer.fallDistance = 0;
			par2World.playSoundAtEntity(par3EntityPlayer, "yam:items.extrajump", 0.5F, (rand.nextFloat()*0.4F)+0.8F);
			par1ItemStack.damageItem(2, par3EntityPlayer);
		}
		return par1ItemStack;
	}
	
}