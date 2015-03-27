package yam.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import yam.YetAnotherMod;
import net.minecraft.block.BlockCake;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSlices extends BlockCake {
	@SideOnly(Side.CLIENT)
    private IIcon field_150038_a;
    @SideOnly(Side.CLIENT)
    private IIcon field_150037_b;
    @SideOnly(Side.CLIENT)
    private IIcon field_150039_M;
    
	private String texture;
	private Item item;

    public BlockSlices(String texture)
    {
        super();
        
        this.texture = texture;
        
    	this.setBlockTextureName(YetAnotherMod.MODID + ":" + texture);
    	this.setHardness(0.5F);
    	this.setStepSound(soundTypeCloth);
        this.setTickRandomly(true);
    }
    
    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
    {
        int l = p_149719_1_.getBlockMetadata(p_149719_2_, p_149719_3_, p_149719_4_);
        float f = 0.0625F;
        float f1 = (float)(1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        this.setBlockBounds(f1, 0.0F, f, 1.0F - f, f2, 1.0F - f);
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float f = 0.0625F;
        float f1 = 0.25F;
        this.setBlockBounds(f, 0.0F, f, 1.0F - f, f1, 1.0F - f);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        int l = p_149668_1_.getBlockMetadata(p_149668_2_, p_149668_3_, p_149668_4_);
        float f = 0.0625F;
        float f1 = (float)(1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f1), (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)((float)(p_149668_2_ + 1) - f), (double)((float)p_149668_3_ + f2 - f), (double)((float)(p_149668_4_ + 1) - f));
    }
    
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World p_149633_1_, int p_149633_2_, int p_149633_3_, int p_149633_4_)
    {
        int l = p_149633_1_.getBlockMetadata(p_149633_2_, p_149633_3_, p_149633_4_);
        float f = 0.0625F;
        float f1 = (float)(1 + l * 2) / 16.0F;
        float f2 = 0.25F;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149633_2_ + f1), (double)p_149633_3_, (double)((float)p_149633_4_ + f), (double)((float)(p_149633_2_ + 1) - f), (double)((float)p_149633_3_ + f2), (double)((float)(p_149633_4_ + 1) - f));
    }
    
    @SideOnly(Side.CLIENT)
    public void registerBlockIcons(IIconRegister p_149651_1_)
    {
        this.blockIcon = p_149651_1_.registerIcon(YetAnotherMod.MODID + ":" + texture + "/side");
        this.field_150039_M = p_149651_1_.registerIcon(YetAnotherMod.MODID + ":" + texture + "/inner");
        this.field_150038_a = p_149651_1_.registerIcon(YetAnotherMod.MODID + ":" + texture + "/top");
        this.field_150037_b = p_149651_1_.registerIcon(YetAnotherMod.MODID + ":" + texture + "/bottom");
    }
    
    @SideOnly(Side.CLIENT)
    public IIcon getIcon(int p_149691_1_, int p_149691_2_)
    {
        return p_149691_1_ == 1 ? this.field_150038_a : (p_149691_1_ == 0 ? this.field_150037_b : (p_149691_2_ > 0 && p_149691_1_ == 4 ? this.field_150039_M : this.blockIcon));
    }
    
    @SideOnly(Side.CLIENT)
    public Item getItem(World p_149694_1_, int p_149694_2_, int p_149694_3_, int p_149694_4_)
    {
        return null;
    }
}
