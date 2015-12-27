package yam.blocks;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.UP;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.BlockFire;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import yam.CustomDamage;
import yam.CustomPotion;
import yam.YetAnotherMod;

public class BlockHellFire extends BlockFire {
	
	public BlockHellFire(String texture)
    {
        super();

        this.setBlockTextureName(YetAnotherMod.MODID + ":" + texture);
		this.setLightLevel(1F);
		this.setTickRandomly(false);
    }
	
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
	    super.onBlockAdded(par1World, par2, par3, par4);

	    if (par1World.getBlock(par2, par3, par4) == this) {
	        par1World.scheduleBlockUpdate(par2, par3, par4, this, this.tickRate(par1World));
	    }
	}

	public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random) {
		par1World.scheduleBlockUpdate(par2, par3, par4, this, par5Random.nextInt(10) + 1);
		this.updateTick2(par1World, par2, par3, par4, par5Random);
	}
	
	public void updateTick2(World p_149674_1_, int p_149674_2_, int p_149674_3_, int p_149674_4_, Random p_149674_5_)
    {
        if (p_149674_1_.getGameRules().getGameRuleBooleanValue("doFireTick"))
        {
            boolean flag = p_149674_1_.getBlock(p_149674_2_, p_149674_3_ - 1, p_149674_4_).isFireSource(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, UP);

            if (!this.canPlaceBlockAt(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_))
            {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
            }

            if (!flag && p_149674_1_.isRaining() && (p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ - 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_ + 1, p_149674_3_, p_149674_4_) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ - 1) || p_149674_1_.canLightningStrikeAt(p_149674_2_, p_149674_3_, p_149674_4_ + 1)))
            {
                p_149674_1_.setBlockToAir(p_149674_2_, p_149674_3_, p_149674_4_);
            }
            else
            {
                boolean flag1 = p_149674_1_.isBlockHighHumidity(p_149674_2_, p_149674_3_, p_149674_4_);
                byte b0 = 0;

                if (flag1)
                {
                    b0 = -50;
                }

                this.tryCatchFire(p_149674_1_, p_149674_2_ + 1, p_149674_3_, p_149674_4_, 300 + b0, p_149674_5_, 0, WEST );
                this.tryCatchFire(p_149674_1_, p_149674_2_ - 1, p_149674_3_, p_149674_4_, 300 + b0, p_149674_5_, 0, EAST );
                this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ - 1, p_149674_4_, 250 + b0, p_149674_5_, 0, UP   );
                this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_ + 1, p_149674_4_, 250 + b0, p_149674_5_, 0, DOWN );
                this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ - 1, 300 + b0, p_149674_5_, 0, SOUTH);
                this.tryCatchFire(p_149674_1_, p_149674_2_, p_149674_3_, p_149674_4_ + 1, 300 + b0, p_149674_5_, 0, NORTH);

                for (int i1 = p_149674_2_ - 2; i1 <= p_149674_2_ + 2; ++i1)
                {
                    for (int j1 = p_149674_4_ - 2; j1 <= p_149674_4_ + 2; ++j1)
                    {
                        for (int k1 = p_149674_3_ - 2; k1 <= p_149674_3_ + 4; ++k1)
                        {
                            if (i1 != p_149674_2_ || k1 != p_149674_3_ || j1 != p_149674_4_)
                            {
                                int l1 = 100;

                                if (k1 > p_149674_3_ + 1)
                                {
                                    l1 += (k1 - (p_149674_3_ + 1)) * 100;
                                }

                                int i2 = this.getChanceOfNeighborsEncouragingFire(p_149674_1_, i1, k1, j1);

                                if (i2 > 0)
                                {
                                    int j2 = (i2 + 40 + p_149674_1_.difficultySetting.getDifficultyId() * 7);

                                    if (flag1)
                                    {
                                        j2 /= 2;
                                    }

                                    if (p_149674_5_.nextInt(20) == 0 && j2 > 0 && p_149674_5_.nextInt(l1) <= j2 && (!p_149674_1_.isRaining() || !p_149674_1_.canLightningStrikeAt(i1, k1, j1)) && !p_149674_1_.canLightningStrikeAt(i1 - 1, k1, p_149674_4_) && !p_149674_1_.canLightningStrikeAt(i1 + 1, k1, j1) && !p_149674_1_.canLightningStrikeAt(i1, k1, j1 - 1) && !p_149674_1_.canLightningStrikeAt(i1, k1, j1 + 1))
                                    {
                                        p_149674_1_.setBlock(i1, k1, j1, this, 0, 3);
                                        p_149674_1_.scheduleBlockUpdate(i1, k1, j1, this, p_149674_5_.nextInt(10) + 1);
                                    }
                                }
                            }
                        }
                    }
                }
                
                if (p_149674_5_.nextInt(50) == 0) {
                	p_149674_1_.newExplosion(null, p_149674_2_, p_149674_3_, p_149674_4_, 3, true, true);
                }
            }
        }
    }
	
	private void tryCatchFire(World p_149841_1_, int p_149841_2_, int p_149841_3_, int p_149841_4_, int p_149841_5_, Random p_149841_6_, int p_149841_7_, ForgeDirection face)
    {
        int j1 = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_).getFlammability(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, face);

        if (p_149841_6_.nextInt(p_149841_5_) < j1)
        {
            boolean flag = p_149841_1_.getBlock(p_149841_2_, p_149841_3_, p_149841_4_) == Blocks.tnt;

            if (p_149841_6_.nextInt(p_149841_7_ + 10) < 5 && !p_149841_1_.canLightningStrikeAt(p_149841_2_, p_149841_3_, p_149841_4_))
            {
                int k1 = p_149841_7_ + p_149841_6_.nextInt(5) / 4;

                if (k1 > 15)
                {
                    k1 = 15;
                }

                p_149841_1_.setBlock(p_149841_2_, p_149841_3_, p_149841_4_, this, k1, 3);
            }
            else
            {
                p_149841_1_.setBlockToAir(p_149841_2_, p_149841_3_, p_149841_4_);
            }

            if (flag)
            {
                Blocks.tnt.onBlockDestroyedByPlayer(p_149841_1_, p_149841_2_, p_149841_3_, p_149841_4_, 1);
            }
        }
    }

    /**
     * Returns true if at least one block next to this one can burn.
     */
    private boolean canNeighborBurn(World p_149847_1_, int p_149847_2_, int p_149847_3_, int p_149847_4_)
    {
        return this.canCatchFire(p_149847_1_, p_149847_2_ + 1, p_149847_3_, p_149847_4_, WEST ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_ - 1, p_149847_3_, p_149847_4_, EAST ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_ - 1, p_149847_4_, UP   ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_ + 1, p_149847_4_, DOWN ) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ - 1, SOUTH) ||
               this.canCatchFire(p_149847_1_, p_149847_2_, p_149847_3_, p_149847_4_ + 1, NORTH);
    }

    /**
     * Gets the highest chance of a neighbor block encouraging this block to catch fire
     */
    private int getChanceOfNeighborsEncouragingFire(World p_149845_1_, int p_149845_2_, int p_149845_3_, int p_149845_4_)
    {
        byte b0 = 0;

        if (!p_149845_1_.isAirBlock(p_149845_2_, p_149845_3_, p_149845_4_))
        {
            return 0;
        }
        else
        {
            int l = b0;
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ + 1, p_149845_3_, p_149845_4_, l, WEST );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_ - 1, p_149845_3_, p_149845_4_, l, EAST );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ - 1, p_149845_4_, l, UP   );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_ + 1, p_149845_4_, l, DOWN );
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ - 1, l, SOUTH);
            l = this.getChanceToEncourageFire(p_149845_1_, p_149845_2_, p_149845_3_, p_149845_4_ + 1, l, NORTH);
            return l;
        }
    }
	
	public int tickRate(World p_149738_1_)
    {
        return 20;
    }
	
	public void onEntityCollidedWithBlock(World p_149670_1_, int p_149670_2_, int p_149670_3_, int p_149670_4_, Entity p_149670_5_) {
		p_149670_5_.attackEntityFrom(DamageSource.inFire, 0.1F);
		p_149670_5_.setFire(5);
		p_149670_5_.hurtResistantTime = 0;
	}
	
}
