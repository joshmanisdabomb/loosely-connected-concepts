package com.joshmanisdabomb.aimagg.gen.structure;

import java.util.HashMap;
import java.util.Random;

import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.structure.template.Template;

public abstract class AimaggGenStructure {
	
	protected HashMap<Long, AimaggStructure> structureMap = new HashMap<Long, AimaggStructure>();

	public abstract AimaggStructure getStructureWithSeed(long chunkSeed);
	
	public abstract int getChunkLookupRadius();
	
	public abstract long getStructureSeed();
	
	public abstract int getRarityPerChunk();

	public final void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (int i = -this.getChunkLookupRadius(); i <= this.getChunkLookupRadius(); i++) {
			for (int k = -this.getChunkLookupRadius(); k <= this.getChunkLookupRadius(); k++) {
				ChunkPos c = new ChunkPos(chunkX + i, chunkZ + k);
				long seed = this.getChunkSeed(c, world.getSeed());
				if (this.isChunkPosStructurePoint(seed)) {
					for (int i2 = -this.getChunkLookupRadius(); i2 <= this.getChunkLookupRadius(); i2++) {
						for (int k2 = -this.getChunkLookupRadius(); k2 <= this.getChunkLookupRadius(); k2++) {
							if (!(c.x == chunkX && c.z == chunkZ) && !chunkProvider.isChunkGeneratedAt(c.x + i2, c.z + k2)) {
								return;
							}
						}
					}
					this.getStructureWithSeed(seed).gen(random, c, world, chunkGenerator, chunkProvider);
					return;
				}
			}
		}
	}
	
	public boolean isChunkPosStructurePoint(long seed) {
		return new Random(seed).nextInt(this.getRarityPerChunk()) == 0;
	}
	
	protected long getChunkSeed(ChunkPos c, long worldSeed) {
		return worldSeed + (long)(c.x * c.x * 4987142) + (long)(c.x * 5947611) + (long)(c.z * c.z) * 4392871L + (long)(c.z * 389711) ^ this.getStructureSeed();
	}
	
	public abstract class AimaggStructure {
		
		private boolean pregenerated = false;
		
		protected final long chunkSeed;
		protected Random rand;
		
		public AimaggStructure(long chunkSeed) {
			this.chunkSeed = chunkSeed;
			this.resetRandom();
		}

		protected Random getRandom() {
			return this.rand;
		}
		
		protected void resetRandom() {
			this.rand = new Random(this.chunkSeed);
		}

		public abstract void preGenerate(Random random, ChunkPos structurePoint, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider);
		
		public abstract void generate(Random random, ChunkPos structurePoint, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider);
		
		public final void gen(Random random, ChunkPos structurePoint, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
			if (!this.pregenerated) {
				this.resetRandom();
				this.preGenerate(random, structurePoint, world, chunkGenerator, chunkProvider);
				this.pregenerated = true;
			}
			this.generate(random, structurePoint, world, chunkGenerator, chunkProvider);
		}
		
	}
	
}