package yam.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import yam.CustomPotion;
import yam.YetAnotherMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHotCoal extends BlockGeneric {

	private final double height = 0.8D;
	private final double realHeight = 5.5D;
	private Random rand = new Random();

	public BlockHotCoal(Material material, String texture) {
		super(material, texture);
		this.setLightLevel(1.0F);
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
		p_149670_5_.setFire(6);
		p_149670_5_.motionY = height;
		p_149670_1_.playSoundAtEntity(p_149670_5_, "random.fizz", 1.0F, rand.nextFloat()*0.4F+0.8F);
		if (!CustomPotion.isEligible(p_149670_5_, false)) {return;}
		((EntityLivingBase) p_149670_5_).addPotionEffect(new PotionEffect(Potion.moveSpeed.id, 80, 1));
	}
	
	public void getExtraInformation(ItemStack itemstack, EntityPlayer player, List list, boolean par4) {
		list.add("Estimated Height: \2478" + realHeight + " blocks");
	}

}
