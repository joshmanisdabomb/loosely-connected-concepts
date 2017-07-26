package com.joshmanisdabomb.aimagg.blocks;

public class AimaggBlockMicrophone /*extends AimaggBlockBasic implements ITileEntityProvider*/ {

	/*public AimaggBlockMicrophone(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(internalName, sortVal, material, mcolor);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTEMicrophone();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTESpreaderConstructor)world.getTileEntity(pos));
		world.removeTileEntity(pos);
		super.breakBlock(world, pos, state);
	}
	
	@Override
	public boolean eventReceived(IBlockState state, World world, BlockPos pos, int id, int param) {
        super.eventReceived(state, world, pos, id, param);
        TileEntity te = world.getTileEntity(pos);
        return te == null ? false : te.receiveClientEvent(id, param);
    }
	
	@Override
	public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
	    if (stack.hasDisplayName()) {
	        ((AimaggTEMicrophone)world.getTileEntity(pos)).setCustomName((stack.getDisplayName()));
	    }
	}
	
	@Override
	public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, ItemStack heldItem, EnumFacing side, float hitX, float hitY, float hitZ) {
	    if (!world.isRemote) {
	        player.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.MicrophoneID, world, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}*/

}
