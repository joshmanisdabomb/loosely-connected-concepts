package yam.items.tools;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import yam.particle.ParticleHandler;
import yam.particle.ParticleHandler.ParticleType;

public class ItemBackstabSword extends ItemCustomSword {

	private ParticleType particles;
	private final float bonusdamage;

	public ItemBackstabSword(String texture, ParticleType particles, ToolMaterial p_i45327_1_, float bonusdamage) {
		super(texture, p_i45327_1_);
		this.particles = particles;
		this.bonusdamage = bonusdamage;
	}
	
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase) {
		float difference = MathHelper.wrapAngleTo180_float(par3EntityLivingBase.rotationYaw) - MathHelper.wrapAngleTo180_float(par2EntityLivingBase.rotationYaw);
		if (difference > -45 && difference < 45) {
			ParticleHandler.particleSpark(par2EntityLivingBase.worldObj, this.particles, par2EntityLivingBase.posX, par2EntityLivingBase.posY+(par2EntityLivingBase.height/2), par2EntityLivingBase.posZ, 0.12, 50);
			//sound
			par2EntityLivingBase.setHealth(par2EntityLivingBase.getHealth()-bonusdamage);
		}
		return super.hitEntity(par1ItemStack, par2EntityLivingBase, par3EntityLivingBase);
    }
	
	public void addInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Backstab Damage: " + EnumChatFormatting.YELLOW + "+" + bonusdamage + " hearts");
	}

}
