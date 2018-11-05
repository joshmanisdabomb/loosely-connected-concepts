package com.joshmanisdabomb.aimagg.gen;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.Mirror;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.gen.structure.template.PlacementSettings;
import net.minecraft.world.gen.structure.template.Template;
import net.minecraftforge.common.util.Constants.NBT;

public class AimaggWorldGenHelper {

	public static final ResourceLocation WASTELAND_CITY_HALL_1 = new ResourceLocation(Constants.MOD_ID, "wasteland_city/city_hall_1");
	
	private static Template loadStructure(World world, ResourceLocation rl) {
		return ((WorldServer)world).getStructureTemplateManager().get(world.getMinecraftServer(), rl);
	}
	
	//try to figure out why this bad boy doesnt work
	public static boolean isStructureInsideChunk(BlockPos structurePos, BlockPos size, ChunkPos c) {
		return !(c.getXStart() > structurePos.getX() + size.getX() - 1 || structurePos.getX() > c.getXEnd() || c.getZStart() > structurePos.getZ() + size.getZ() - 1 || structurePos.getZ() > c.getZEnd());
	}
	
	public static boolean isRegionClear(World world, BlockPos one, BlockPos two, int additionalRadius, float percentageNeeded) {
		int clearBlocks = 0, allBlocks = 0;
		BlockPos min = new BlockPos(Math.min(one.getX(), two.getX()), Math.min(one.getY(), two.getY()), Math.min(one.getZ(), two.getZ()));
		BlockPos max = new BlockPos(Math.max(one.getX(), two.getX()), Math.max(one.getY(), two.getY()), Math.max(one.getZ(), two.getZ()));
		one = min; two = max;
		for (int i = one.getX() - additionalRadius; i <= two.getX() + additionalRadius; i++) {
			for (int j = one.getY() - additionalRadius; j <= two.getY() + additionalRadius; j++) {
				for (int k = one.getZ() - additionalRadius; k <= two.getZ() + additionalRadius; k++) {
					if (world.isAirBlock(new BlockPos(i,j,k))) {
						clearBlocks++;
					}
					allBlocks++;
				}
			}
		}
		if ((clearBlocks / (double)(allBlocks)) < percentageNeeded) {
			return false;
		}
		return true;
	}
	
	public static boolean isRegionFullBlocks(World world, BlockPos one, BlockPos two, int additionalRadius, float percentageNeeded) {
		int solidBlocks = 0, allBlocks = 0;
		BlockPos min = new BlockPos(Math.min(one.getX(), two.getX()), Math.min(one.getY(), two.getY()), Math.min(one.getZ(), two.getZ()));
		BlockPos max = new BlockPos(Math.max(one.getX(), two.getX()), Math.max(one.getY(), two.getY()), Math.max(one.getZ(), two.getZ()));
		one = min; two = max;
		for (int i = one.getX() - additionalRadius; i <= two.getX() + additionalRadius; i++) {
			for (int j = one.getY() - additionalRadius; j <= two.getY() + additionalRadius; j++) {
				for (int k = one.getZ() - additionalRadius; k <= two.getZ() + additionalRadius; k++) {
					if (world.isBlockFullCube(new BlockPos(i,j,k))) {
						solidBlocks++;
					}
					allBlocks++;
				}
			}
		}
		if ((solidBlocks / (double)(allBlocks)) < percentageNeeded) {
			return false;
		}
		return true;
	}
	
	public static boolean placeNBTStructure(ResourceLocation rl, World world, BlockPos pos, Mirror m, Rotation r, StructureOffset ax, StructureOffset ay, StructureOffset az, Predicate<IBlockState> foundation) {
		Template t = AimaggWorldGenHelper.loadStructure(world, rl);
		if (t == null) return false;
		
		NBTTagCompound nbt = new NBTTagCompound();
		t.writeToNBT(nbt);
		NBTTagList blocks = nbt.getTagList("blocks", NBT.TAG_COMPOUND);
		NBTTagList palette = nbt.getTagList("palette", NBT.TAG_COMPOUND);
		
		BlockPos size = t.transformedSize(r);
		BlockPos pos2 = t.getZeroPositionWithTransform(pos, m, r).add(ax.getStructureOffset(size.getX()),ay.getStructureOffset(size.getY()),az.getStructureOffset(size.getZ()));
		
		int foundationPaletteID = -1;
		int airPaletteID = -1;
		for (int i = 0; i < palette.tagCount(); i++) {
			IBlockState blockstate = NBTUtil.readBlockState(palette.getCompoundTagAt(i));
			if (foundationPaletteID == -1 && blockstate == AimaggBlocks.structureFoundation.getDefaultState()) {
				foundationPaletteID = i;
			} else if (airPaletteID == -1 && blockstate == Blocks.AIR.getDefaultState()) {
				airPaletteID = i;
			}
		}
		
		if (foundationPaletteID > -1 || airPaletteID > -1) {
			for (int i = 0; i < blocks.tagCount(); i++) {
				NBTTagCompound block = blocks.getCompoundTagAt(i);
				NBTTagList vec = block.getTagList("pos", NBT.TAG_INT);
				if (block.getInteger("state") == foundationPaletteID) {
					BlockPos bp = AimaggWorldGenHelper.transformedBlockPos(new BlockPos(vec.getIntAt(0), vec.getIntAt(1), vec.getIntAt(2)), m, r).add(pos2);
					IBlockState worldBlock = world.getBlockState(bp);
					if (!foundation.apply(worldBlock)) {
						System.out.println("block " + bp.toString() + " is not foundational");
						return false;
					}
				} else if (block.getInteger("state") == airPaletteID) {
					BlockPos bp = AimaggWorldGenHelper.transformedBlockPos(new BlockPos(vec.getIntAt(0), vec.getIntAt(1), vec.getIntAt(2)), m, r).add(pos2);
					IBlockState worldBlock = world.getBlockState(AimaggWorldGenHelper.transformedBlockPos(new BlockPos(vec.getIntAt(0), vec.getIntAt(1), vec.getIntAt(2)), m, r).add(pos2));
					if (worldBlock != Blocks.AIR) {
						System.out.println("block " + bp.toString() + " is not air");
						return false;
					}
				}
			}
		}
	
		/*if (tf != null && tf.tt != TerraformType.NONE) {
			tf.terraform(world, pos2.add(0,-1,0), pos2.add(t.getSize().getX()-1,-1,t.getSize().getZ()-1), genLimit);
		}*/
		
		IBlockState iblockstate = world.getBlockState(pos2);
		world.notifyBlockUpdate(pos2, iblockstate, iblockstate, 3);
		PlacementSettings placementsettings = (new PlacementSettings()).setMirror(m).setRotation(r).setIgnoreEntities(true).setReplacedBlock(AimaggBlocks.structureFoundation).setIgnoreStructureBlock(true);
		t.addBlocksToWorld(world, pos2, placementsettings);
		
		return true;
	}
	
	private static BlockPos transformedBlockPos(BlockPos pos, Mirror mirrorIn, Rotation rotationIn) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        boolean flag = true;

        switch (mirrorIn)
        {
            case LEFT_RIGHT:
                k = -k;
                break;
            case FRONT_BACK:
                i = -i;
                break;
            default:
                flag = false;
        }

        switch (rotationIn)
        {
            case COUNTERCLOCKWISE_90:
                return new BlockPos(k, j, -i);
            case CLOCKWISE_90:
                return new BlockPos(-k, j, i);
            case CLOCKWISE_180:
                return new BlockPos(-i, j, -k);
            default:
                return flag ? new BlockPos(i, j, k) : pos;
        }
    }
	
	public static enum StructureOffset {
		FULL,
		CENTER,
		NONE;
		
		public double getStructureOffset(int length) {
			switch(this) {
				case CENTER:
					return -(length/2D);
				case FULL:
					return -(length-1);
				default:
					return 0;
			}
		}
	}
	
	/*public static class Terraformer {
		
		private final TerraformType tt;
		private final int radiusOffset;
		private final IBlockState[] states;
		
		private final Random rand = new Random();

		public Terraformer(TerraformType tt, int radiusOffset, IBlockState... states) {
			this.tt = tt;
			this.radiusOffset = radiusOffset;
			this.states = states;
		}

		public void terraform(World world, BlockPos one, BlockPos two, ChunkPos genLimit) {
			BlockPos min = new BlockPos(Math.min(one.getX(), two.getX()) - this.radiusOffset, Math.min(one.getY(), two.getY()), Math.min(one.getZ(), two.getZ()) - this.radiusOffset);
			BlockPos max = new BlockPos(Math.max(one.getX(), two.getX()) + this.radiusOffset, Math.max(one.getY(), two.getY()), Math.max(one.getZ(), two.getZ()) + this.radiusOffset);
			one = min; two = max;
			switch(tt) {
				default:
					return;
				case FLOOR:
					for (int i = one.getX(); i <= two.getX(); i++) {
						for (int k = one.getZ(); k <= two.getZ(); k++) {
							int jLimit = 0;
							if (genLimit == null || (i >= genLimit.getXStart() && k >= genLimit.getZStart() && i <= genLimit.getXEnd() && k <= genLimit.getZEnd())) {
								for (int j = two.getY(); j >= one.getY() - jLimit && j >= 0; j--) {
									BlockPos b = new BlockPos(i,j,k);
									if (!world.isBlockFullCube(b)) {
										IBlockState state = states[this.rand.nextInt(states.length)];
										world.setBlockState(b, state);
										if (j <= one.getY() && !world.isBlockFullCube(b.down())) {
											jLimit++;
										}
									}
								}
							}
						}
					}
					return;
				case ROUNDED_FLOOR:
					for (int i = one.getX(); i <= two.getX(); i++) {
						for (int k = one.getZ(); k <= two.getZ(); k++) {
							if (!((i == one.getX() || i == two.getX()) && (k == one.getZ() || k == two.getZ())) && //checking for corners
							(genLimit == null || (i >= genLimit.getXStart() && k >= genLimit.getZStart() && i <= genLimit.getXEnd() && k <= genLimit.getZEnd()))) { //checking if inside chunk
								int jLimit = 0;
								for (int j = two.getY(); j >= one.getY() - jLimit && j >= 0; j--) {
									BlockPos b = new BlockPos(i,j,k);
									if (!world.isBlockFullCube(b)) {
										IBlockState state = states[this.rand.nextInt(states.length)];
										world.setBlockState(b, state);
										if (j <= one.getY() && !world.isBlockFullCube(b.down())) {
											jLimit++;
										}
									}
								}
							}
						}
					}
					return;
			}
			
		}
		
	}
	
	public static enum TerraformType {
		NONE,
		FLOOR,
		ROUNDED_FLOOR;
	}*/
	
}
