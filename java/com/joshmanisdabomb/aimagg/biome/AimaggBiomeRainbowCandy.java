package com.joshmanisdabomb.aimagg.biome;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockRainbowGrass.RainbowGrassType;
import com.joshmanisdabomb.aimagg.gen.rainbow.WorldGenCandyCane;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;

public class AimaggBiomeRainbowCandy extends AimaggBiomeRainbow {
	
    public static final WorldGenAbstractTree TREE_FEATURE = new WorldGenCandyCane(false, false);
    public static final WorldGenAbstractTree BIG_TREE_FEATURE = new WorldGenCandyCane(true, false);

	public AimaggBiomeRainbowCandy(BiomeProperties properties) {
		super(properties);
        this.decorator = new BiomeRainbowCandyDecorator();
        this.topBlock = AimaggBlocks.rainbowGrass.getDefaultState().withProperty(AimaggBlockRainbowGrass.TYPE, RainbowGrassType.SUGAR);
	}
	
	@Override
	public WorldGenAbstractTree getRandomTreeFeature(Random rand) {
		return (WorldGenAbstractTree) (rand.nextInt(10) == 0 ? BIG_TREE_FEATURE : TREE_FEATURE);
	}

	public static class BiomeRainbowCandyDecorator extends BiomeDecorator {

	    public int treesPerChunk = 1;
	    
		@Override
		public void genDecorations(Biome biome, World world, Random random) {
			if (net.minecraftforge.event.terraingen.TerrainGen.decorate(world, random, chunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE)) {
				for (int i = 0; i < this.treesPerChunk; i++) {
		            int x = random.nextInt(16) + 8;
		            int z = random.nextInt(16) + 8;
		            WorldGenAbstractTree worldgenabstracttree = biome.getRandomTreeFeature(random);
		            worldgenabstracttree.setDecorationDefaults();
		            BlockPos blockpos = world.getHeight(chunkPos.add(x, 0, z));

		            if (worldgenabstracttree.generate(world, random, blockpos)) {
		                worldgenabstracttree.generateSaplings(world, random, blockpos);
		            }
		        }
			}
	    }
		
	}

}
