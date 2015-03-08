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
import yam.entity.EntityAmplislime;
import yam.entity.EntityLollipopper;
import yam.entity.EntityRainbot;
import yam.entity.EntityUnicorn;

public class BiomeRainbow extends BiomeGenBase {

	public static final Block[] topBlocks = {YetAnotherMod.rainbowGrassWhite, YetAnotherMod.rainbowGrassLGray, YetAnotherMod.rainbowGrassGray, YetAnotherMod.rainbowGrassBlack, YetAnotherMod.rainbowGrassRed, YetAnotherMod.rainbowGrassOrange, YetAnotherMod.rainbowGrassYellow, YetAnotherMod.rainbowGrassLime, YetAnotherMod.rainbowGrassGreen, YetAnotherMod.rainbowGrassLBlue, YetAnotherMod.rainbowGrassCyan, YetAnotherMod.rainbowGrassBlue, YetAnotherMod.rainbowGrassPurple, YetAnotherMod.rainbowGrassMagenta, YetAnotherMod.rainbowGrassPink, YetAnotherMod.rainbowGrassBrown};
	public Block stoneBlock; public Block seaBlock; public Block bedrockBlock;
	public static final BiomeGenBase.Height height = new BiomeGenBase.Height(0.5F, 0.3F);
	
	public BiomeRainbow(int par1) {
		super(par1, true);
        this.setColor(7361843);
        this.setBiomeName("Rainbow");
        
        this.topBlock = YetAnotherMod.rainbowGrassPink;
        this.fillerBlock = YetAnotherMod.rainbowDirt;
        this.stoneBlock = YetAnotherMod.rainbowStone;
        this.seaBlock = Blocks.air;
        this.bedrockBlock = YetAnotherMod.rainbowStream;
        
        this.field_76754_C = Integer.parseInt("00FF00",16);
        this.waterColorMultiplier = Integer.parseInt("FFFFFF",16);
        
        this.rootHeight = height.rootHeight;
        this.heightVariation = height.variation;
        this.temperature = 1.0F;
        this.rainfall = 0.0F;
        this.enableRain = false;
        
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityLollipopper.class, 20, 1, 4));
        this.spawnableCreatureList.add(new BiomeGenBase.SpawnListEntry(EntityUnicorn.class, 10, 1, 4));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityRainbot.class, 10, 1, 1));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityAmplislime.class, 10, 1, 1));
        
        this.theBiomeDecorator = this.createBiomeDecorator();
	}

    public int getSkyColorByTemp(float par1) {
		return Integer.parseInt("FF00FF",16);
    }
    
    public void genTerrainBlocks(World p_150573_1_, Random p_150573_2_, Block[] p_150573_3_, byte[] p_150573_4_, int p_150573_5_, int p_150573_6_, double p_150573_7_) {
        boolean flag = true;
        Block block = this.topBlocks[p_150573_2_.nextInt(16)];
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
            
            if (l1 <= 0)
            {
                p_150573_3_[i2] = this.bedrockBlock;
            }
            else
            {
	            Block block2 = p_150573_3_[i2];
	
	            if (block2 != null && block2.getMaterial() != Material.air)
	            {
	                if (block2 == this.stoneBlock)
	                {
	                    if (k == -1)
	                    {
	                    	if (l <= 0)
                            {
                                block = null;
                                b0 = 0;
                                block1 = this.stoneBlock;
                            }
	                        else if (l1 >= 59 && l1 <= 64)
	                        {
	                            block = this.topBlocks[p_150573_2_.nextInt(16)];
	                            b0 = (byte)(this.field_150604_aj & 255);
	                            block1 = this.fillerBlock;
	                        }
	
	                        if (l1 < 63 && (block == null || block.getMaterial() == Material.air))
	                        {
	                            block = this.seaBlock;
	                            b0 = 0;
	                        }
	
	                        k = l;
	
	                        if (l1 >= 62)
	                        {
	                            p_150573_3_[i2] = block;
	                            p_150573_4_[i2] = b0;
	                        }
	                        else
	                        {
	                            p_150573_3_[i2] = (l1 >= 30 + p_150573_2_.nextInt(5)) ? block1 : null;
	                        }
	                    }
	                    else if (k > 0)
	                    {
	                        --k;
	                        p_150573_3_[i2] = block1;
	                    }
	                }
	            }
	            else
	            {
	                k = -1;
	            }
            }
        }
    }
    
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorEmpty());
    }
    
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
    }

}
