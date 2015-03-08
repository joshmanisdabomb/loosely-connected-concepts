package yam.blocks;

import yam.YetAnotherMod;
import yam.blocks.entity.TileEntityComputer;
import yam.blocks.entity.TileEntityTickField;
import yam.gui.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockComputer extends BlockGeneric implements ITileEntityProvider {

	public BlockComputer(Material material, String texture) {
		super(material, texture + "/front", texture + "/side", texture + "/top", texture + "/bottom");
		this.setHardness(7.5f);
		this.setResistance(13.0f);
		this.setStepSound(Block.soundTypeMetal);
		this.setHarvestLevel("pickaxe", 2);
	}
	
	public TileEntity createNewTileEntity(World p_149915_1_, int p_149915_2_)
    {
        return new TileEntityComputer();
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
    	if (!world.isRemote) {
	    	TileEntity tileEntity = world.getTileEntity(x, y, z);
		    if (tileEntity == null || player.isSneaking()) {return false;}
		    player.openGui(YetAnotherMod.instance, GuiHandler.computer, world, x, y, z);
	        return true;
    	}
    	return false;
    }

}
