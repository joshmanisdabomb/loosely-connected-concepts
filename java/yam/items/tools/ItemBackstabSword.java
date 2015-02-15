package yam.items.tools;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class ItemBackstabSword extends ItemCustomSword {

	private ParticleType particles;

	public ItemBackstabSword(String texture, ParticleType particles, ToolMaterial p_i45327_1_) {
		super(texture, p_i45327_1_);
		this.particles = particles;
	}
	
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		float difference = MathHelper.wrapAngleTo180_float(par3EntityLivingBase.rotationYaw) - MathHelper.wrapAngleTo180_float(par2EntityLivingBase.rotationYaw);
		System.out.println(difference);
		if (difference > -45 && difference < 45) {
			ParticleHandler.particleSpark(par2EntityLivingBase.worldObj, this.particles, par2EntityLivingBase.posX, par2EntityLivingBase.posY+(par2EntityLivingBase.height/2), par2EntityLivingBase.posZ, 0.12, 50);
			//sound
			//extra damage
		}
		return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }

}
