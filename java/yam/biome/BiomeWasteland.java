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
import yam.entity.EntityPsychoPig;
import yam.entity.EntityTick;

public class BiomeWasteland extends BiomeGenBase {

	public BiomeWasteland(int par1) {
		super(par1, true);
        this.setColor(7361843);
        this.setBiomeName("Wasteland");
        
        this.topBlock = YetAnotherMod.crackedMud;
        this.fillerBlock = YetAnotherMod.crackedMud;
        
        this.field_76754_C = 7033670;
        this.waterColorMultiplier = 0;
        
        this.rootHeight = height_LowPlains.rootHeight;
        this.heightVariation = height_LowPlains.variation;
        this.temperature = 2.0F;
        this.rainfall = 0.0F;
        this.spawnableMonsterList = new ArrayList();
        this.spawnableCreatureList = new ArrayList();
        this.spawnableWaterCreatureList = new ArrayList();
        this.spawnableCaveCreatureList = new ArrayList();
        this.enableRain = false;
        
        this.theBiomeDecorator = this.createBiomeDecorator();

        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityPsychoPig.class, 1, 1, 8));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityDerekJr.class, 1, 1, 8));
        this.spawnableMonsterList.add(new BiomeGenBase.SpawnListEntry(EntityTick.class, 1, 1, 8));
	}

    public int getSkyColorByTemp(float par1) {
		return 0;
    }
    
    public int getBiomeGrassColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
    	return Integer.parseInt("333333", 16);
    }
    
    public int getBiomeFoliageColor(int p_150571_1_, int p_150571_2_, int p_150571_3_) {
    	return Integer.parseInt("333333", 16);
    }
    
    public BiomeDecorator createBiomeDecorator()
    {
        return getModdedBiomeDecorator(new BiomeDecoratorEmpty());
    }
    
    public void decorate(World par1World, Random par2Random, int par3, int par4)
    {
        this.theBiomeDecorator.decorateChunk(par1World, par2Random, this, par3, par4);
    }
    
    public boolean isWastelandMonster(Entity e) {
    	for (Object o : this.spawnableMonsterList) {
    		if (((BiomeGenBase.SpawnListEntry)o).entityClass == e.getClass()) {
    			return true;
    		}
    	}
    	for (Object o : this.spawnableCreatureList) {
    		if (((BiomeGenBase.SpawnListEntry)o).entityClass == e.getClass()) {
    			return true;
    		}
    	}
    	for (Object o : this.spawnableWaterCreatureList) {
    		if (((BiomeGenBase.SpawnListEntry)o).entityClass == e.getClass()) {
    			return true;
    		}
    	}
    	for (Object o : this.spawnableCaveCreatureList) {
    		if (((BiomeGenBase.SpawnListEntry)o).entityClass == e.getClass()) {
    			return true;
    		}
    	}
    	return e.getClass() == EntityDerek.class;
    }

}
