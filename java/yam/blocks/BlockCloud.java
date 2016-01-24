package yam.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCloud extends BlockGeneric {

	public BlockCloud(String texture) {
		super(Material.cloth, texture);
		this.setHardness(1.0F);
		this.setResistance(0.0F);
		this.setStepSound(Block.soundTypeCloth);
		this.setLightOpacity(0);
		this.setLightLevel(0.1F);
		this.setSlipperiness(1.06F);
		this.setTransparency();
	}
	
	@SideOnly(Side.CLIENT)
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        return AxisAlignedBB.getBoundingBox(p_149633_2_, p_149633_3_, p_149633_4_, p_149633_2_ + 1, p_149633_3_ + 0.01, p_149633_4_ + 1);
    }
	
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
		p_149670_5_.fallDistance = 0.0F;
	}
	
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return super.shouldSideBeRendered(p_149646_1_, p_149646_2_, p_149646_3_, p_149646_4_, 1 - p_149646_5_);
    }

}
