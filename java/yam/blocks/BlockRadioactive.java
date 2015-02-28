package yam.blocks;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yam.CustomPotion;
import yam.YetAnotherMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class BlockRadioactive extends BlockGeneric {

	public int radioactivity;

	public BlockRadioactive(Material material, String texture, int radioactivity) {
		super(material, texture);

		this.setTickRandomly(true);
		this.radioactivity = radioactivity;
	}
	
	/**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        final float f = YetAnotherMod.bbConstant;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f), (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)((float)(p_149668_2_ + 1) - f), (double)((float)(p_149668_3_ + 1) - f), (double)((float)(p_149668_4_ + 1) - f));
    }
    
    public void updateTick(World world, int x, int y, int z, Random rand) {
    	if (!world.isRemote) {
    		if (rand.nextInt(400) == 0) {
    			//TODO Spawn radiation.
    		}
    	}
    }

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        float f = 1.0F;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149633_2_ + f), (double)p_149633_3_, (double)((float)p_149633_4_ + f), (double)((float)(p_149633_2_ + 1) - f), (double)(p_149633_3_ + 1), (double)((float)(p_149633_4_ + 1) - f));
    }
	
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
		if (!CustomPotion.isEligible(p_149670_5_, false)) {return;}
		((EntityLivingBase) p_149670_5_).addPotionEffect(new PotionEffect(CustomPotion.radiation.id, 160, radioactivity));
	}
	
	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add(EnumChatFormatting.GRAY + "Debuffs: " + EnumChatFormatting.DARK_GREEN + "Radiation " + (radioactivity == 0 ? "I" : StatCollector.translateToLocal("potion.potency." + radioactivity).trim()));
	}

}
