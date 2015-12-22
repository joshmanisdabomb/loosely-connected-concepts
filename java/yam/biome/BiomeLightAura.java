package yam.biome;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import yam.YetAnotherMod;
import yam.entity.EntityDerek;
import yam.entity.EntityDerekJr;
import yam.entity.EntityHiddenReptile;
import yam.entity.EntityPsychoPig;
import yam.entity.EntityTheRotting;
import yam.entity.EntityTick;

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
    
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
    }

}
