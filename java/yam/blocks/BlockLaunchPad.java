package yam.blocks;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityLaunchPad;
import yam.blocks.entity.TileEntityTrashCan;
import yam.gui.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BlockLaunchPad extends BlockGeneric implements ITileEntityProvider {

	public BlockLaunchPad(Material material) {
		super(material, "");
		this.setHardness(8.0F);
		this.setResistance(80.0F);
		this.setHarvestLevel("pickaxe", 5);
		this.disableStats();
		this.setCreativeTab(null);
		this.setLightOpacity(0);
		this.setStepSound(soundTypeMetal);
        this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.5F, 0.8125F);
	}
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityLaunchPad();
    }
	
	public void breakBlock(World p_149749_1_, int p_149749_2_, int p_149749_3_, int p_149749_4_, Block p_149749_5_, int p_149749_6_)
    {
        super.breakBlock(p_149749_1_, p_149749_2_, p_149749_3_, p_149749_4_, p_149749_5_, p_149749_6_);
        p_149749_1_.removeTileEntity(p_149749_2_, p_149749_3_, p_149749_4_);
    }

    public boolean onBlockEventReceived(World p_149696_1_, int p_149696_2_, int p_149696_3_, int p_149696_4_, int p_149696_5_, int p_149696_6_)
    {
        super.onBlockEventReceived(p_149696_1_, p_149696_2_, p_149696_3_, p_149696_4_, p_149696_5_, p_149696_6_);
        TileEntity tileentity = p_149696_1_.getTileEntity(p_149696_2_, p_149696_3_, p_149696_4_);
        return tileentity != null ? tileentity.receiveClientEvent(p_149696_5_, p_149696_6_) : false;
    }
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int metadata, float what, float these, float are) {
    	TileEntity tileEntity = world.getTileEntity(x, y, z);
	    if (tileEntity == null || player.isSneaking()) {return false;}
	    player.openGui(YetAnotherMod.instance, GuiHandler.pad, world, x, y, z);
        return true;
    }
    
    public int getRenderType()
    {
        return -1;
    }
    
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
    
    public AxisAlignedBB getSelectionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        final float f = 0.1875f;
        return AxisAlignedBB.getBoundingBox((double)((float)p_149668_2_ + f), (double)p_149668_3_, (double)((float)p_149668_4_ + f), (double)((float)(p_149668_2_ + 1) - f), (double)((float)(p_149668_3_ + 1.5)), (double)((float)(p_149668_4_ + 1) - f));
    }
    
    public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(YetAnotherMod.launchPad, 1);
    }
    
    private boolean isVecInsideYZBounds(Vec3 p_149654_1_)
    {
        return p_149654_1_ == null ? false : p_149654_1_.yCoord >= this.minY && p_149654_1_.yCoord <= this.maxY && p_149654_1_.zCoord >= this.minZ && p_149654_1_.zCoord <= this.maxZ;
    }

}
