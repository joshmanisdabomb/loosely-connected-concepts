package yam.biome;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import yam.YetAnotherMod;

public class BiomeLightAura extends BiomeGenBase {

	public BiomeLightAura(int par1) {
		super(par1, true);
        this.setColor(Integer.parseInt("66FFFF", 16));
        this.setBiomeName("Light Aura");
        
        this.topBlock = YetAnotherMod.lightGrass;
        this.fillerBlock = YetAnotherMod.lightDirt;
        
        this.field_76754_C = Integer.parseInt("66FFFF", 16);
        this.waterColorMultiplier = Integer.parseInt("0000FF", 16);
        
        this.rootHeight = height_LowPlains.rootHeight;
        this.heightVariation = height_MidPlains.variation;
        this.temperature = 0.9F;
        this.rainfall = 0.0F;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.enableRain = false;
        
        this.theBiomeDecorator = this.createBiomeDecorator();

        /*
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPsychoPig.class, 1, 1, 8));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityDerekJr.class, 1, 1, 8));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityTick.class, 1, 1, 8));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityTheRotting.class, 1, 1, 1));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityHiddenReptile.class, 25, 1, 8));
        */
	}

    public int getSkyColorByTemp(float par1) {
		return Integer.parseInt("66FFFF", 16);
    }
    
    public int getBiomeGrassColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
    	return Integer.parseInt("FFFF00", 16);
    }
    
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
    	return Integer.parseInt("FFFF00", 16);
    }
    
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorEmpty());
    }
    
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_)
    {
        boolean flag = true;
        Block block = this.topBlock;
        byte b0 = (byte)(this.field_150604_aj & 255);
        Block block1 = this.fillerBlock;
        int k = -1;
        int l = (int)(p_150573_7_ / 3.0D + 3.0D + p_150573_2_.nextDouble() * 0.25D);
        int i1 = p_150573_5_ & 15;
        int j1 = p_150573_6_ & 15;
        int k1 = p_150573_3_.length / 256;

        for (int l1 = 255; l1 >= 0; --l1)
        {
            int i2 = (j1 * 16 + i1) * k1 + l1;

            if (l1 <= 0 + p_150573_2_.nextInt(5))
            {
                p_150573_3_[i2] = Blocks.bedrock;
            }
            else
            {
                Block block2 = p_150573_3_[i2];

                if (block2 != null && block2.getMaterial() != Material.air)
                {
                    if (block2 == Blocks.stone)
                    {
                        if (k == -1)
                        {
                            if (l <= 0)
                            {
                                block = null;
                                b0 = 0;
                                block1 = Blocks.stone;
                            }
                            else if (l1 >= 59 && l1 <= 64)
                            {
                                block = this.topBlock;
                                b0 = (byte)(this.field_150604_aj & 255);
                                block1 = this.fillerBlock;
                            }

                            if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
                            {
                                if (this.getFloatTemperature(p_150573_5_, l1, p_150573_6_) < 0.15F)
                                {
                                    block = Blocks.ice;
                                    b0 = 0;
                                }
                                else
                                {
                                    block = Blocks.water;
                                    b0 = 0;
                                }
                            }

                            k = l;

                            if (l1 >= 62)
                            {
                                p_150573_3_[i2] = block;
                                p_150573_4_[i2] = b0;
                            }
                            else if (l1 < 56 - l)
                            {
                                block = null;
                                block1 = Blocks.stone;
                                p_150573_3_[i2] = Blocks.gravel;
                            }
                            else
                            {
                                p_150573_3_[i2] = block1;
                            }
                        }
                        else if (k > 0)
                        {
                            --k;
                            p_150573_3_[i2] = block1;

                            if (k == 0 && block1 == Blocks.sand)
                            {
                                k = p_150573_2_.nextInt(4) + Math.max(0, l1 - 63);
                                block1 = Blocks.sandstone;
                            }
                        }
                    }
                }
                else
                {
                    k = -1;
                }
            }
            
            if (p_150573_3_[i2] == Blocks.stone) {
            	p_150573_3_[i2] = YetAnotherMod.lightStone;
            }
        }
    }
    
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
    }

}
