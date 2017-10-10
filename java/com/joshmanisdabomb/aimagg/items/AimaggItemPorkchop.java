package com.joshmanisdabomb.aimagg.items;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemUpgradeCard.UpgradeCardType;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.SoundCategory;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggItemPorkchop extends AimaggItemBasic {

	public AimaggItemPorkchop(String internalName) {
		super(internalName);
		this.setMaxStackSize(1);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack itemstack = playerIn.getHeldItem(handIn);

        if (playerIn.getFoodStats().needFood()) {
    		worldIn.playSound((EntityPlayer) null, playerIn.posX, playerIn.posY, playerIn.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
    		
    		if (itemstack.getMetadata() == 0) {
    			playerIn.getFoodStats().addStats(3, 0.3F);
    		} else {
    			playerIn.getFoodStats().addStats(8, 0.8F);
    		}
    		
    		playerIn.addStat(StatList.getObjectUseStats(this));
			if (playerIn instanceof EntityPlayerMP) {
				CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)playerIn, itemstack);
			}
			if (!playerIn.isCreative()) {
				itemstack.shrink(1);
			}
            return new ActionResult<ItemStack>(EnumActionResult.SUCCESS, itemstack);
        } else {
            return new ActionResult<ItemStack>(EnumActionResult.FAIL, itemstack);
        }
    }
	
	@Override
	public void registerRender() {		
		ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName() + "_raw", "inventory"));
		ModelLoader.setCustomModelResourceLocation(this, 1, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName() + "_cooked", "inventory"));
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName() + "." + (stack.getMetadata() == 1 ? "cooked" : "raw");
    }
	
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		if (tab.getTabIndex() == AimlessAgglomeration.tab.getTabIndex()) {
            items.add(new ItemStack(this, 1, 0));
            items.add(new ItemStack(this, 1, 1));
		}
	}

}
