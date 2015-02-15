package yam.biome;

import java.util.Random;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenCactus;
import net.minecraft.world.gen.feature.WorldGenClay;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenReed;
import net.minecraft.world.gen.feature.WorldGenSand;
import net.minecraft.world.gen.feature.WorldGenWaterlily;
import net.minecraft.world.gen.feature.WorldGenerator;

import static net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.*;
import static net.minecraftforge.event.terraingen.OreGenEvent.GenerateMinable.EventType.*;
import net.minecraftforge.common.*;
import net.minecraftforge.event.terraingen.*;

public class BiomeDecoratorEmpty extends BiomeDecorator
{
    /** The world the BiomeDecorator is currently decorating */
    public World currentWorld;
    /** The Biome Decorator's random number generator. */
    public Random randomGenerator;
    /** The X-coordinate of the chunk currently being decorated */
    public int chunk_X;
    /** The Z-coordinate of the chunk currently being decorated */
    public int chunk_Z;
    /** The clay generator. */
    public WorldGenerator clayGen = new WorldGenClay(4);
    /** The sand generator. */
    public WorldGenerator sandGen;
    /** The gravel generator. */
    public WorldGenerator gravelAsSandGen;
    /** The dirt generator. */
    public WorldGenerator dirtGen;
    public WorldGenerator gravelGen;
    public WorldGenerator coalGen;
    public WorldGenerator ironGen;
    /** Field that holds gold WorldGenMinable */
    public WorldGenerator goldGen;
    /** Field that holds redstone WorldGenMinable */
    public WorldGenerator redstoneGen;
    /** Field that holds diamond WorldGenMinable */
    public WorldGenerator diamondGen;
    /** Field that holds Lapis WorldGenMinable */
    public WorldGenerator lapisGen;
    public WorldGenFlowers yellowFlowerGen;
    /** Field that holds mushroomBrown WorldGenFlowers */
    public WorldGenerator mushroomBrownGen;
    /** Field that holds mushroomRed WorldGenFlowers */
    public WorldGenerator mushroomRedGen;
    /** Field that holds big mushroom generator */
    public WorldGenerator bigMushroomGen;
    /** Field that holds WorldGenReed */
    public WorldGenerator reedGen;
    /** Field that holds WorldGenCactus */
    public WorldGenerator cactusGen;
    /** The water lily generation! */
    public WorldGenerator waterlilyGen;
    /** Amount of waterlilys per chunk. */
    public int waterlilyPerChunk;
    /**
     * The number of trees to attempt to generate per chunk. Up to 10 in forests, none in deserts.
     */
    public int treesPerChunk;
    /**
     * The number of yellow flower patches to generate per chunk. The game generates much less than this number, since
     * it attempts to generate them at a random altitude.
     */
    public int flowersPerChunk;
    /** The amount of tall grass to generate per chunk. */
    public int grassPerChunk;
    /**
     * The number of dead bushes to generate per chunk. Used in deserts and swamps.
     */
    public int deadBushPerChunk;
    /**
     * The number of extra mushroom patches per chunk. It generates 1/4 this number in brown mushroom patches, and 1/8
     * this number in red mushroom patches. These mushrooms go beyond the default base number of mushrooms.
     */
    public int mushroomsPerChunk;
    /**
     * The number of reeds to generate per chunk. Reeds won't generate if the randomly selected placement is unsuitable.
     */
    public int reedsPerChunk;
    /**
     * The number of cactus plants to generate per chunk. Cacti only work on sand.
     */
    public int cactiPerChunk;
    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater.
     */
    public int sandPerChunk;
    /**
     * The number of sand patches to generate per chunk. Sand patches only generate when part of it is underwater. There
     * appear to be two separate fields for this.
     */
    public int sandPerChunk2;
    /**
     * The number of clay patches to generate per chunk. Only generates when part of it is underwater.
     */
    public int clayPerChunk;
    /** Amount of big mushrooms per chunk */
    public int bigMushroomsPerChunk;
    /** True if decorator should generate surface lava & water */
    public boolean generateLakes;

    public BiomeDecoratorEmpty() {}

    public void decorateChunk(World p_150512_1_, Random p_150512_2_, BiomeGenBase p_150512_3_, int p_150512_4_, int p_150512_5_)
    {
        if (this.currentWorld != null)
        {
            throw new RuntimeException("Already decorating!!");
        }
        else
        {
            this.currentWorld = p_150512_1_;
            this.randomGenerator = p_150512_2_;
            this.chunk_X = p_150512_4_;
            this.chunk_Z = p_150512_5_;
            this.genDecorations(p_150512_3_);
            this.currentWorld = null;
            this.randomGenerator = null;
        }
    }

    protected void genDecorations(BiomeGenBase p_150513_1_)
    {
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Pre(currentWorld, randomGenerator, chunk_X, chunk_Z));
        
        MinecraftForge.EVENT_BUS.post(new DecorateBiomeEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }

    /**
     * Generates ores in the current chunk
     */
    protected void generateOres()
    {
        MinecraftForge.ORE_GEN_BUS.post(new OreGenEvent.Post(currentWorld, randomGenerator, chunk_X, chunk_Z));
    }
}