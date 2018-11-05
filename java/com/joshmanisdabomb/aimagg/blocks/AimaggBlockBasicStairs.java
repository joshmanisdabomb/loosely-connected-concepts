package com.joshmanisdabomb.aimagg.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockBasicHalf.HalfType;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockWastelandWorld.WastelandWorldType;

import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggBlockBasicStairs extends AimaggBlockBasic {
	public static final PropertyDirection HORIZONTAL_FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
	public static final PropertyEnum<HalfType> HALF = PropertyEnum.<HalfType>create("half", HalfType.class);
	public static final PropertyEnum<ShapeType> SHAPE = PropertyEnum.<ShapeType>create("shape", ShapeType.class);

	protected static final AxisAlignedBB AABB_SLAB_TOP = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_TOP_WEST = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_TOP_EAST = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_TOP_NORTH = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
	protected static final AxisAlignedBB AABB_QTR_TOP_SOUTH = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_OCT_TOP_NW = new AxisAlignedBB(0.0D, 0.5D, 0.0D, 0.5D, 1.0D, 0.5D);
	protected static final AxisAlignedBB AABB_OCT_TOP_NE = new AxisAlignedBB(0.5D, 0.5D, 0.0D, 1.0D, 1.0D, 0.5D);
	protected static final AxisAlignedBB AABB_OCT_TOP_SW = new AxisAlignedBB(0.0D, 0.5D, 0.5D, 0.5D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_OCT_TOP_SE = new AxisAlignedBB(0.5D, 0.5D, 0.5D, 1.0D, 1.0D, 1.0D);
	protected static final AxisAlignedBB AABB_SLAB_BOTTOM = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_BOT_WEST = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_BOT_EAST = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_QTR_BOT_NORTH = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
	protected static final AxisAlignedBB AABB_QTR_BOT_SOUTH = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_OCT_BOT_NW = new AxisAlignedBB(0.0D, 0.0D, 0.0D, 0.5D, 0.5D, 0.5D);
	protected static final AxisAlignedBB AABB_OCT_BOT_NE = new AxisAlignedBB(0.5D, 0.0D, 0.0D, 1.0D, 0.5D, 0.5D);
	protected static final AxisAlignedBB AABB_OCT_BOT_SW = new AxisAlignedBB(0.0D, 0.0D, 0.5D, 0.5D, 0.5D, 1.0D);
	protected static final AxisAlignedBB AABB_OCT_BOT_SE = new AxisAlignedBB(0.5D, 0.0D, 0.5D, 1.0D, 0.5D, 1.0D);

	private final IBlockState modelState;

	public AimaggBlockBasicStairs(String internalName, IBlockState modelState) {
		super(internalName, modelState.getMaterial(), MapColor.AIR);
		this.setDefaultState(this.blockState.getBaseState().withProperty(HORIZONTAL_FACING, EnumFacing.NORTH).withProperty(HALF, HalfType.BOTTOM).withProperty(SHAPE, ShapeType.STRAIGHT));
		this.modelState = modelState;
		this.setLightOpacity(255);
	}

	public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean p_185477_7_) {
		if (!p_185477_7_) {
			state = this.getActualState(state, worldIn, pos);
		}

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(state)) {
			addCollisionBoxToList(pos, entityBox, collidingBoxes, axisalignedbb);
		}
	}

	private static List<AxisAlignedBB> getCollisionBoxList(IBlockState bstate) {
		List<AxisAlignedBB> list = Lists.<AxisAlignedBB>newArrayList();
		boolean flag = bstate.getValue(HALF) == HalfType.TOP;
		list.add(flag ? AABB_SLAB_TOP : AABB_SLAB_BOTTOM);
		ShapeType shape = bstate.getValue(SHAPE);

		if (shape == ShapeType.STRAIGHT || shape == ShapeType.INNER_LEFT || shape == ShapeType.INNER_RIGHT) {
			list.add(getCollQuarterBlock(bstate));
		}

		if (shape != ShapeType.STRAIGHT) {
			list.add(getCollEighthBlock(bstate));
		}

		return list;
	}

	/**
	 * Returns a bounding box representing a quarter of a block (two eight-size
	 * cubes back to back). Used in all stair shapes except OUTER.
	 */
	private static AxisAlignedBB getCollQuarterBlock(IBlockState bstate) {
		boolean flag = bstate.getValue(HALF) == HalfType.TOP;

		switch (bstate.getValue(HORIZONTAL_FACING)) {
			case NORTH:
			default:
				return flag ? AABB_QTR_BOT_NORTH : AABB_QTR_TOP_NORTH;
			case SOUTH:
				return flag ? AABB_QTR_BOT_SOUTH : AABB_QTR_TOP_SOUTH;
			case WEST:
				return flag ? AABB_QTR_BOT_WEST : AABB_QTR_TOP_WEST;
			case EAST:
				return flag ? AABB_QTR_BOT_EAST : AABB_QTR_TOP_EAST;
		}
	}

	/**
	 * Returns a bounding box representing an eighth of a block (a block whose
	 * three dimensions are halved). Used in all stair shapes except STRAIGHT
	 * (gets added alone in the case of OUTER; alone with a quarter block in
	 * case of INSIDE).
	 */
	private static AxisAlignedBB getCollEighthBlock(IBlockState bstate) {
		EnumFacing enumfacing = bstate.getValue(HORIZONTAL_FACING);
		EnumFacing enumfacing1;

		switch (bstate.getValue(SHAPE)) {
			case OUTER_LEFT:
			default:
				enumfacing1 = enumfacing;
				break;
			case OUTER_RIGHT:
				enumfacing1 = enumfacing.rotateY();
				break;
			case INNER_RIGHT:
				enumfacing1 = enumfacing.getOpposite();
				break;
			case INNER_LEFT:
				enumfacing1 = enumfacing.rotateYCCW();
		}

		boolean flag = bstate.getValue(HALF) == HalfType.TOP;

		switch (enumfacing1) {
			case NORTH:
			default:
				return flag ? AABB_OCT_BOT_NW : AABB_OCT_TOP_NW;
			case SOUTH:
				return flag ? AABB_OCT_BOT_SE : AABB_OCT_TOP_SE;
			case WEST:
				return flag ? AABB_OCT_BOT_SW : AABB_OCT_TOP_SW;
			case EAST:
				return flag ? AABB_OCT_BOT_NE : AABB_OCT_TOP_NE;
		}
	}

	public BlockFaceShape getBlockFaceShape(IBlockAccess p_193383_1_, IBlockState p_193383_2_, BlockPos p_193383_3_, EnumFacing p_193383_4_) {
		p_193383_2_ = this.getActualState(p_193383_2_, p_193383_1_, p_193383_3_);

		if (p_193383_4_.getAxis() == EnumFacing.Axis.Y) {
			return p_193383_4_ == EnumFacing.UP == (p_193383_2_.getValue(HALF) == HalfType.TOP) ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
		} else {
			ShapeType shape  = p_193383_2_.getValue(SHAPE);

			if (shape != ShapeType.OUTER_LEFT && shape != ShapeType.OUTER_RIGHT) {
				EnumFacing enumfacing = (EnumFacing) p_193383_2_.getValue(HORIZONTAL_FACING);

				switch (shape) {
					case INNER_RIGHT:
						return enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateYCCW() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
					case INNER_LEFT:
						return enumfacing != p_193383_4_ && enumfacing != p_193383_4_.rotateY() ? BlockFaceShape.UNDEFINED : BlockFaceShape.SOLID;
					case STRAIGHT:
						return enumfacing == p_193383_4_ ? BlockFaceShape.SOLID : BlockFaceShape.UNDEFINED;
					default:
						return BlockFaceShape.UNDEFINED;
				}
			} else {
				return BlockFaceShape.UNDEFINED;
			}
		}
	}

	/**
	 * Used to determine ambient occlusion and culling when rebuilding chunks
	 * for render
	 */
	public boolean isOpaqueCube(IBlockState state) {
		return false;
	}

	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
		this.modelState.getBlock().randomDisplayTick(stateIn, worldIn, pos, rand);
	}

	public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
		this.modelState.getBlock().onBlockClicked(worldIn, pos, playerIn);
	}

	/**
	 * Called after a player destroys this Block - the posiiton pos may no
	 * longer hold the state indicated.
	 */
	public void onBlockDestroyedByPlayer(World worldIn, BlockPos pos, IBlockState state) {
		this.modelState.getBlock().onBlockDestroyedByPlayer(worldIn, pos, state);
	}

	@SideOnly(Side.CLIENT)
	public int getPackedLightmapCoords(IBlockState state, IBlockAccess source, BlockPos pos) {
		return this.modelState.getPackedLightmapCoords(source, pos);
	}

	/**
	 * Returns how much this block can resist explosions from the passed in
	 * entity.
	 */
	public float getExplosionResistance(Entity exploder) {
		return this.modelState.getBlock().getExplosionResistance(exploder);
	}

	/**
	 * How many world ticks before ticking
	 */
	public int tickRate(World worldIn) {
		return this.modelState.getBlock().tickRate(worldIn);
	}

	public Vec3d modifyAcceleration(World worldIn, BlockPos pos, Entity entityIn, Vec3d motion) {
		return this.modelState.getBlock().modifyAcceleration(worldIn, pos, entityIn, motion);
	}

	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return this.modelState.getBlock().getBlockLayer();
	}

	/**
	 * Return an AABB (in world coords!) that should be highlighted when the
	 * player is targeting this Block
	 */
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World worldIn, BlockPos pos) {
		return this.modelState.getSelectedBoundingBox(worldIn, pos);
	}

	/**
	 * Returns if this block is collidable. Only used by fire, although stairs
	 * return that of the block that the stair is made of (though nobody's going
	 * to make fire stairs, right?)
	 */
	public boolean isCollidable() {
		return this.modelState.getBlock().isCollidable();
	}

	public boolean canCollideCheck(IBlockState state, boolean hitIfLiquid) {
		return this.modelState.getBlock().canCollideCheck(state, hitIfLiquid);
	}

	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		return this.modelState.getBlock().canPlaceBlockAt(worldIn, pos);
	}

	/**
	 * Called after the block is set in the Chunk data, but before the Tile
	 * Entity is set
	 */
	public void onBlockAdded(World worldIn, BlockPos pos, IBlockState state) {
		this.modelState.neighborChanged(worldIn, pos, Blocks.AIR, pos);
		this.modelState.getBlock().onBlockAdded(worldIn, pos, this.modelState);
	}

	/**
	 * Called serverside after this block is replaced with another in Chunk, but
	 * before the Tile Entity is updated
	 */
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		this.modelState.getBlock().breakBlock(worldIn, pos, this.modelState);
	}

	/**
	 * Called when the given entity walks on this Block
	 */
	public void onEntityWalk(World worldIn, BlockPos pos, Entity entityIn) {
		this.modelState.getBlock().onEntityWalk(worldIn, pos, entityIn);
	}

	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		this.modelState.getBlock().updateTick(worldIn, pos, state, rand);
	}

	/**
	 * Called when the block is right clicked by a player.
	 */
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		return this.modelState.getBlock().onBlockActivated(worldIn, pos, this.modelState, playerIn, hand, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
	}

	/**
	 * Called when this Block is destroyed by an Explosion
	 */
	public void onBlockDestroyedByExplosion(World worldIn, BlockPos pos, Explosion explosionIn) {
		this.modelState.getBlock().onBlockDestroyedByExplosion(worldIn, pos, explosionIn);
	}

	/**
	 * Determines if the block is solid enough on the top side to support other
	 * blocks, like redstone components.
	 */
	public boolean isTopSolid(IBlockState state) {
		return state.getValue(HALF) == HalfType.TOP;
	}

	/**
	 * Get the MapColor for this Block and the given BlockState
	 */
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return this.modelState.getBlock().getMapColor(this.modelState, worldIn, pos);
	}

	/**
	 * Called by ItemBlocks just before a block is actually set in the world, to
	 * allow for adjustments to the IBlockstate
	 */
	public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
		IBlockState iblockstate = super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer);
		iblockstate = iblockstate.withProperty(HORIZONTAL_FACING, placer.getHorizontalFacing()).withProperty(SHAPE, ShapeType.STRAIGHT);
		return facing != EnumFacing.DOWN && (facing == EnumFacing.UP || (double) hitY <= 0.5D) ? iblockstate.withProperty(HALF, HalfType.BOTTOM) : iblockstate.withProperty(HALF, HalfType.TOP);
	}

	/**
	 * Ray traces through the blocks collision from start vector to end vector
	 * returning a ray trace hit.
	 */
	@Nullable
	public RayTraceResult collisionRayTrace(IBlockState blockState, World worldIn, BlockPos pos, Vec3d start, Vec3d end) {
		List<RayTraceResult> list = Lists.<RayTraceResult>newArrayList();

		for (AxisAlignedBB axisalignedbb : getCollisionBoxList(this.getActualState(blockState, worldIn, pos))) {
			list.add(this.rayTrace(pos, start, end, axisalignedbb));
		}

		RayTraceResult raytraceresult1 = null;
		double d1 = 0.0D;

		for (RayTraceResult raytraceresult : list) {
			if (raytraceresult != null) {
				double d0 = raytraceresult.hitVec.squareDistanceTo(end);

				if (d0 > d1) {
					raytraceresult1 = raytraceresult;
					d1 = d0;
				}
			}
		}

		return raytraceresult1;
	}

	/**
	 * Convert the given metadata into a BlockState for this Block
	 */
	public IBlockState getStateFromMeta(int meta) {
		IBlockState iblockstate = this.getDefaultState().withProperty(HALF, (meta & 4) > 0 ? HalfType.TOP : HalfType.BOTTOM);
		iblockstate = iblockstate.withProperty(HORIZONTAL_FACING, EnumFacing.getFront(5 - (meta & 3)));
		return iblockstate;
	}

	/**
	 * Convert the BlockState into the correct metadata value
	 */
	public int getMetaFromState(IBlockState state) {
		int i = 0;

		if (state.getValue(HALF) == HalfType.TOP) {
			i |= 4;
		}

		i = i | 5 - ((EnumFacing) state.getValue(HORIZONTAL_FACING)).getIndex();
		return i;
	}

	/**
	 * Get the actual Block state of this Block at the given position. This
	 * applies properties not visible in the metadata, such as fence
	 * connections.
	 */
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return state.withProperty(SHAPE, getStairsShape(state, worldIn, pos));
	}

	private static ShapeType getStairsShape(IBlockState p_185706_0_, IBlockAccess p_185706_1_, BlockPos p_185706_2_) {
		EnumFacing enumfacing = (EnumFacing) p_185706_0_.getValue(HORIZONTAL_FACING);
		IBlockState iblockstate = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing));

		if (isBlockStairs(iblockstate) && p_185706_0_.getValue(HALF) == iblockstate.getValue(HALF)) {
			EnumFacing enumfacing1 = (EnumFacing) iblockstate.getValue(HORIZONTAL_FACING);

			if (enumfacing1.getAxis() != ((EnumFacing) p_185706_0_.getValue(HORIZONTAL_FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing1.getOpposite())) {
				if (enumfacing1 == enumfacing.rotateYCCW()) {
					return ShapeType.OUTER_LEFT;
				}

				return ShapeType.OUTER_RIGHT;
			}
		}

		IBlockState iblockstate1 = p_185706_1_.getBlockState(p_185706_2_.offset(enumfacing.getOpposite()));

		if (isBlockStairs(iblockstate1) && p_185706_0_.getValue(HALF) == iblockstate1.getValue(HALF)) {
			EnumFacing enumfacing2 = (EnumFacing) iblockstate1.getValue(HORIZONTAL_FACING);

			if (enumfacing2.getAxis() != ((EnumFacing) p_185706_0_.getValue(HORIZONTAL_FACING)).getAxis() && isDifferentStairs(p_185706_0_, p_185706_1_, p_185706_2_, enumfacing2)) {
				if (enumfacing2 == enumfacing.rotateYCCW()) {
					return ShapeType.INNER_LEFT;
				}

				return ShapeType.INNER_RIGHT;
			}
		}

		return ShapeType.STRAIGHT;
	}

	private static boolean isDifferentStairs(IBlockState p_185704_0_, IBlockAccess p_185704_1_, BlockPos p_185704_2_, EnumFacing p_185704_3_) {
		IBlockState iblockstate = p_185704_1_.getBlockState(p_185704_2_.offset(p_185704_3_));
		return !isBlockStairs(iblockstate) || iblockstate.getValue(HORIZONTAL_FACING) != p_185704_0_.getValue(HORIZONTAL_FACING) || iblockstate.getValue(HALF) != p_185704_0_.getValue(HALF);
	}

	public static boolean isBlockStairs(IBlockState state) {
		return state.getBlock() instanceof AimaggBlockBasicStairs || state.getBlock() instanceof BlockStairs;
	}

	/**
	 * Returns the blockstate with the given rotation from the passed
	 * blockstate. If inapplicable, returns the passed blockstate.
	 */
	public IBlockState withRotation(IBlockState state, Rotation rot) {
		return state.withProperty(HORIZONTAL_FACING, rot.rotate((EnumFacing) state.getValue(HORIZONTAL_FACING)));
	}

	/**
	 * Returns the blockstate with the given mirror of the passed blockstate. If
	 * inapplicable, returns the passed blockstate.
	 */
	@SuppressWarnings("incomplete-switch")
	public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
		EnumFacing enumfacing = (EnumFacing) state.getValue(HORIZONTAL_FACING);
		ShapeType shape = state.getValue(SHAPE);

		switch (mirrorIn) {
			case LEFT_RIGHT:

				if (enumfacing.getAxis() == EnumFacing.Axis.Z) {
					switch (shape) {
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.INNER_LEFT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.INNER_RIGHT);
						default:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}

				break;
			case FRONT_BACK:

				if (enumfacing.getAxis() == EnumFacing.Axis.X) {
					switch (shape) {
						case OUTER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.OUTER_RIGHT);
						case OUTER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.OUTER_LEFT);
						case INNER_RIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.INNER_RIGHT);
						case INNER_LEFT:
							return state.withRotation(Rotation.CLOCKWISE_180).withProperty(SHAPE, ShapeType.INNER_LEFT);
						case STRAIGHT:
							return state.withRotation(Rotation.CLOCKWISE_180);
					}
				}
		}

		return super.withMirror(state, mirrorIn);
	}

	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, new IProperty[] { HORIZONTAL_FACING, HALF, SHAPE });
	}

	@Override
	public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face) {
		if (net.minecraftforge.common.ForgeModContainer.disableStairSlabCulling)
			return super.doesSideBlockRendering(state, world, pos, face);

		if (state.isOpaqueCube())
			return true;

		state = this.getActualState(state, world, pos);

		HalfType half = state.getValue(HALF);
		EnumFacing side = state.getValue(HORIZONTAL_FACING);
		ShapeType shape = state.getValue(SHAPE);
		if (face == EnumFacing.UP)
			return half == HalfType.TOP;
		if (face == EnumFacing.DOWN)
			return half == HalfType.BOTTOM;
		if (shape == ShapeType.OUTER_LEFT || shape == ShapeType.OUTER_RIGHT)
			return false;
		if (face == side)
			return true;
		if (shape == ShapeType.INNER_LEFT && face.rotateY() == side)
			return true;
		if (shape == ShapeType.INNER_RIGHT && face.rotateYCCW() == side)
			return true;
		return false;
	}
	
	@Override
	public void registerInventoryRender() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Constants.MOD_ID + ":shaped/" + this.getInternalName()));
	}

	public static enum HalfType implements IStringSerializable {
		TOP("top"), BOTTOM("bottom");

		private final String name;

		private HalfType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}

	public static enum ShapeType implements IStringSerializable {
		STRAIGHT("straight"), INNER_LEFT("inner_left"), INNER_RIGHT("inner_right"), OUTER_LEFT("outer_left"), OUTER_RIGHT("outer_right");

		private final String name;

		private ShapeType(String name) {
			this.name = name;
		}

		public String toString() {
			return this.name;
		}

		public String getName() {
			return this.name;
		}
	}
}