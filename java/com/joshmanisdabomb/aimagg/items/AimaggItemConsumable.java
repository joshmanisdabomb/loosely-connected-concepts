package com.joshmanisdabomb.aimagg.items;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class AimaggItemConsumable extends AimaggItemBasic {

	public AimaggItemConsumable(String internalName) {
		super(internalName);
		this.addPropertyOverride(new ResourceLocation("consumable_use_percent"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return entityIn.getActiveItemStack().getItem() instanceof AimaggItemConsumable ? 1.0F - (float)(entityIn.getItemInUseCount())/(float)(stack.getMaxItemUseDuration()) : 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("consumable_in_use"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (this.canUse(worldIn, playerIn, handIn, itemstack)) {
            playerIn.setActiveHand(handIn);
			this.playStartSound(worldIn, itemstack, playerIn);
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }
	
	@Override
	public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		if (entityLiving instanceof EntityPlayer) {
			EntityPlayer entityplayer = (EntityPlayer) entityLiving;
			entityplayer.getFoodStats().addStats(this.getFoodAmount(stack, worldIn, entityLiving), this.getSaturationAmount(stack, worldIn, entityLiving));
			this.playFinishSound(worldIn, stack, (EntityPlayer)entityLiving);
			this.addEffects(worldIn, stack, entityplayer);
			entityplayer.addStat(StatList.getObjectUseStats(this));

			if (entityplayer instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) entityplayer, stack);
			}
		}

		if (!(entityLiving instanceof EntityPlayer && ((EntityPlayer)entityLiving).isCreative())) {
			stack.shrink(1);
		}
		return stack;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
        return 32;
    }
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.EAT;
    }

	public int getFoodAmount(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return 8;
	}

	public float getSaturationAmount(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
		return 0.8F;
	}

	public void playStartSound(World worldIn, ItemStack stack, EntityPlayer entityLiving) {
		worldIn.playSound((EntityPlayer) null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.BLOCK_ANVIL_FALL, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
	}

	public void playFinishSound(World worldIn, ItemStack stack, EntityPlayer entityLiving) {
		worldIn.playSound((EntityPlayer) null, entityLiving.posX, entityLiving.posY, entityLiving.posZ, SoundEvents.ENTITY_VEX_CHARGE, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
	}

	public void addEffects(World worldIn, ItemStack stack, EntityPlayer entityplayer) {
		
	}
	
	public boolean canUse(World worldIn, EntityPlayer playerIn, EnumHand handIn, ItemStack itemstack) {
		return playerIn.canEat(false);
	}

}
