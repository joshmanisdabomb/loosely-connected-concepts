package com.joshmanisdabomb.aimagg.gen.structure.wasteland_city;

import java.util.Random;

import com.google.common.base.Predicate;
import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockWastelandWorld;
import com.joshmanisdabomb.aimagg.gen.AimaggWorldGenHelper;
import com.joshmanisdabomb.aimagg.gen.AimaggWorldGenHelper.StructureOffset;
import com.joshmanisdabomb.aimagg.gen.structure.AimaggGenStructure;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.Mirror;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;

public class AimaggGenStructureWastelandCity extends AimaggGenStructure {

	public static final long SEED = 6137815294L;
	
	public static final Predicate<IBlockState> FOUNDATION = new Predicate<IBlockState>() {

		@Override
		public boolean apply(IBlockState input) {
			return input == AimaggBlocks.wastelandWorld.getDefaultState().withProperty(AimaggBlockWastelandWorld.TYPE, AimaggBlockWastelandWorld.WastelandWorldType.CRACKED_MUD);
		}
		
	};

	@Override
	public int getChunkLookupRadius() {
		return 2;
	}

	@Override
	public long getStructureSeed() {
		return SEED;
	}
	
	@Override
	public int getRarityPerChunk() {
		return 50;
	}

	@Override
	public AimaggStructure getStructureWithSeed(long chunkSeed) {
		AimaggStructure as = structureMap.get(chunkSeed);
		if (as == null) {
			structureMap.put(chunkSeed, as = new AimaggStructureWastelandCity(chunkSeed));
		}
		return as;
	}

	public class AimaggStructureWastelandCity extends AimaggStructure {

		private int posX;
		private int posY;
		private int posZ;
		private Rotation rot;
		private Mirror mir;
		
		public AimaggStructureWastelandCity(long chunkSeed) {
			super(chunkSeed);
		}
		
		@Override
		public void preGenerate(Random random, ChunkPos structurePoint, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
			this.posX = (structurePoint.getXStart() + 8) + (this.getRandom().nextInt(8) - 4);
			this.posZ = (structurePoint.getZStart() + 8) + (this.getRandom().nextInt(8) - 4);
			this.posY = world.getHeight(this.posX, this.posZ);
			this.rot = Rotation.values()[this.getRandom().nextInt(Rotation.values().length)];
			this.mir = Mirror.values()[this.getRandom().nextInt(Mirror.values().length)];
		}

		@Override
		public void generate(Random random, ChunkPos structurePoint, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
			if (this.posY != 0) {
				AimaggWorldGenHelper.placeNBTStructure(AimaggWorldGenHelper.WASTELAND_CITY_HALL_1, world, new BlockPos(this.posX, this.posY - 1, this.posZ), this.mir, this.rot, StructureOffset.CENTER, StructureOffset.NONE, StructureOffset.CENTER, FOUNDATION);
			}
		}

	}
	
}