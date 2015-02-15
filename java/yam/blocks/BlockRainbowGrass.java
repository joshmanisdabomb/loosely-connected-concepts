package yam.blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import yam.YetAnotherMod;
import yam.biome.BiomeRainbow;

public class BlockRainbowGrass extends BlockCustomGrass {

	public BlockRainbowGrass(String texture) {
		super("rainbow/grass/tops/" + texture, "rainbow/grass/sides/" + texture, "rainbow/dirt", YetAnotherMod.rainbowDirt);
		this.setTickRandomly(true);
	}
	
	public void updateTick(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (!p_149674_1_.isRemote)
        {
            if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) < 4 && p_149674_1_.getBlockLightOpacity(p_149674_2_, p_149674_3_ + 1, p_149674_4_) > 2)
            {
                p_149674_1_.setBlock(p_149674_2_, p_149674_3_, p_149674_4_, YetAnotherMod.rainbowDirt);
            }
            else if (p_149674_1_.getBlockLightValue(p_149674_2_, p_149674_3_ + 1, p_149674_4_) >= 9)
            {
                for (int l = 0; l < 4; ++l)
                {
                    int i1 = p_149674_2_ + p_149674_5_.nextInt(3) - 1;
                    int j1 = p_149674_3_ + p_149674_5_.nextInt(5) - 3;
                    int k1 = p_149674_4_ + p_149674_5_.nextInt(3) - 1;
                    Block block = p_149674_1_.getBlock(i1, j1 + 1, k1);

                    if (p_149674_1_.getBlock(i1, j1, k1) == YetAnotherMod.rainbowDirt && p_149674_1_.getBlockMetadata(i1, j1, k1) == 0 && p_149674_1_.getBlockLightValue(i1, j1 + 1, k1) >= 4 && p_149674_1_.getBlockLightOpacity(i1, j1 + 1, k1) <= 2)
                    {
	                	p_149674_1_.setBlock(i1, j1, k1, BiomeRainbow.topBlocks[p_149674_5_.nextInt(16)]);
                    }
                }
            }
        }
    }
	
	public void dropBlockAsItemWithChance(World p_149690_1_, int p_149690_2_, int p_149690_3_, int p_149690_4_, int p_149690_5_, float p_149690_6_, int p_149690_7_)
    {
        if (!p_149690_1_.isRemote)
        {
            if (p_149690_1_.rand.nextFloat() <= p_149690_6_)
            {
                this.dropBlockAsItem(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(YetAnotherMod.rainbowDirt, 1));
            }
            if (p_149690_1_.rand.nextFloat() <= (p_149690_6_ / 2F))
            {
                this.dropBlockAsItem(p_149690_1_, p_149690_2_, p_149690_3_, p_149690_4_, new ItemStack(YetAnotherMod.rainbowSeeds, 1));
            }
        }
    }

}
