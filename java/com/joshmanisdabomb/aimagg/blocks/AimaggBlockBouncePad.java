package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;

import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.gui.AimaggGUIHandler;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketBouncePadExtensionClient;
import com.joshmanisdabomb.aimagg.packets.AimaggPacketHandler;
import com.joshmanisdabomb.aimagg.te.AimaggTEBouncePad;

import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketEntityVelocity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockBouncePad extends AimaggBlockBasic implements ITileEntityProvider {

	public static final PropertyEnum<EnumFacing> FACING = PropertyEnum.<EnumFacing>create("facing", EnumFacing.class);

    public static final AxisAlignedBB BOUNCE_PAD_AABB_NORTH = new AxisAlignedBB(0.0D, 0.0D, 10/16D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB BOUNCE_PAD_AABB_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 6/16D);
    public static final AxisAlignedBB BOUNCE_PAD_AABB_WEST = new AxisAlignedBB(10/16D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
    public static final AxisAlignedBB BOUNCE_PAD_AABB_EAST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 6/16D, 1.0D, 1.0D);
    public static final AxisAlignedBB BOUNCE_PAD_AABB_UP = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 6/16D, 1.0D);
    public static final AxisAlignedBB BOUNCE_PAD_AABB_DOWN = new AxisAlignedBB(0.0D, 10/16D, 0.0D, 1.0D, 1.0D, 1.0D);
    
    public static final double MINIMUM_STRENGTH = 0.3D;
    public static final double MOTION_MODIFIER = 0.106D;
	
	public AimaggBlockBouncePad(String internalName, Material material, MapColor mcolor) {
		super(internalName, material, mcolor);
		this.isBlockContainer = true;
		this.setLightOpacity(0);
		this.alwaysDropWithDamage(0);
		this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.UP));
	}
	
	@Override
	protected BlockStateContainer createBlockState() {
	    return new BlockStateContainer(this, FACING);
	}
	
	@Override
	public int getMetaFromState(IBlockState state) {
    	return state.getValue(FACING).getIndex();
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getFront(meta));
	}
	
	@Override
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
    	return this.getStateFromMeta(meta).withProperty(FACING, facing);
    }
	
	@Override
	public int getLowerSortValue(ItemStack is) {
		return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new AimaggTEBouncePad();
	}
	
	@Override
	public void breakBlock(World world, BlockPos pos, IBlockState state) {
		InventoryHelper.dropInventoryItems(world, pos, (AimaggTEBouncePad)world.getTileEntity(pos));
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
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (!worldIn.isRemote) {
			playerIn.openGui(AimlessAgglomeration.instance, AimaggGUIHandler.BouncePadID, worldIn, pos.getX(), pos.getY(), pos.getZ());
	    }
	    return true;
	}
	
	@Override
	public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
		AimaggTEBouncePad te = (AimaggTEBouncePad)worldIn.getTileEntity(pos);
		if (te.shouldBounce() && !worldIn.isRemote) {
			double mx = te.getMotionX() * te.getSpringMotionModifier() * MOTION_MODIFIER;
			if (mx > 0.0D) {
				entityIn.motionX = (mx / MINIMUM_STRENGTH) + MINIMUM_STRENGTH;
			} else if (mx < 0.0D) {
				entityIn.motionX = (mx / MINIMUM_STRENGTH) - MINIMUM_STRENGTH;
			}
			double my = te.getMotionY() * te.getSpringMotionModifier() * MOTION_MODIFIER;
			if (my > 0.0D) {
				entityIn.motionY = (my / MINIMUM_STRENGTH) + MINIMUM_STRENGTH;
			} else if (my < 0.0D) {
				entityIn.motionY = (my / MINIMUM_STRENGTH) - MINIMUM_STRENGTH;
			}
			double mz = te.getMotionZ() * te.getSpringMotionModifier() * MOTION_MODIFIER;
			if (mz > 0.0D) {
				entityIn.motionZ = (mz / MINIMUM_STRENGTH) + MINIMUM_STRENGTH;
			} else if (mz < 0.0D) {
				entityIn.motionZ = (mz / MINIMUM_STRENGTH) - MINIMUM_STRENGTH;
			}
			if (entityIn instanceof EntityPlayerMP) {
				((EntityPlayerMP)entityIn).connection.sendPacket(new SPacketEntityVelocity(entityIn));
			}
			te.setExtension(12F);

			if (!worldIn.isRemote) {
	        	AimaggPacketBouncePadExtensionClient packetC = new AimaggPacketBouncePadExtensionClient();
	    		packetC.setTileEntityPosition(te.getPos());
	    		packetC.setExtension(12F);
	    		AimaggPacketHandler.INSTANCE.sendToAllAround(packetC, new TargetPoint(te.getWorld().provider.getDimension(), te.getPos().getX() + 0.5D, te.getPos().getY() + 0.5D, te.getPos().getZ() + 0.5D, Math.sqrt(te.getPacketDistanceSquared())));
			}
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return false;
    }

    @Override
    public boolean isBlockNormalCube(IBlockState blockState) {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState blockState) {
        return false;
    }
    
    @Override
    public boolean isNormalCube(IBlockState state, IBlockAccess world, BlockPos pos) {
        return false;
    }
    
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        switch(state.getValue(FACING)) {
			case DOWN:
				return BOUNCE_PAD_AABB_DOWN;
			case EAST:
				return BOUNCE_PAD_AABB_EAST;
			case NORTH:
				return BOUNCE_PAD_AABB_NORTH;
			case SOUTH:
				return BOUNCE_PAD_AABB_SOUTH;
			case WEST:
				return BOUNCE_PAD_AABB_WEST;
			default:
				return BOUNCE_PAD_AABB_UP;
        }
    }
    
    @Override
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, Entity entityIn, boolean p_185477_7_) {
        switch(state.getValue(FACING)) {
			case DOWN:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_DOWN);
				return;
			case EAST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_EAST);
				return;
			case NORTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_NORTH);
				return;
			case SOUTH:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_SOUTH);
				return;
			case WEST:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_WEST);
				return;
			default:
				addCollisionBoxToList(pos, entityBox, collidingBoxes, BOUNCE_PAD_AABB_UP);
				return;
        }
    }

}
