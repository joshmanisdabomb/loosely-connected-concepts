package yam.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import yam.YetAnotherMod;
import yam.entity.EntityFly;

public class BlockPoop extends BlockGeneric {

	public static final Item[] pills = {YetAnotherMod.pillBlack, YetAnotherMod.pillBlue, YetAnotherMod.pillCyanWhite, YetAnotherMod.pillGreen, YetAnotherMod.pillOrangeCyan, YetAnotherMod.pillOrangeYellow, YetAnotherMod.pillRed, YetAnotherMod.pillRedBlue, YetAnotherMod.pillWhite};
	public final Random rand = new Random();
	
	public BlockPoop(String texture) {
		super(Material.ground, texture);
		this.setBlockBounds(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);
	}

	@Override
	public void onBlockDestroyedByPlayer(World p_149664_1_, int p_149664_2_, int p_149664_3_, int p_149664_4_, int p_149664_5_) {
		int spawnMin, spawnMax;
		switch (p_149664_1_.difficultySetting) {
			default:
				return;
			case EASY: {
				spawnMin = 1;
				spawnMax = 3;
			}
			case NORMAL: {
				spawnMin = 1;
				spawnMax = 5;
			}
			case HARD: {
				spawnMin = 3;
				spawnMax = 7;
			}
		}
		if (!p_149664_1_.isRemote && rand.nextInt(4) == 0) {
			int times = rand.nextInt((spawnMax - spawnMin) + 1) + spawnMin;
			for (int i = 0; i < times; i++) {
				Entity fly = new EntityFly(p_149664_1_);
				fly.setPosition(p_149664_2_ + rand.nextDouble(), p_149664_3_, p_149664_4_ + rand.nextDouble());
				p_149664_1_.spawnEntityInWorld(fly);
			}
		}
	}
	
	public Item getItemDropped(int par1, Random par2Random, int par3) {
		return this.getReward(par2Random);
    }
	
	public int quantityDropped(Random par1Random) {
		return par1Random.nextInt(4) != 0 ? 1 : par1Random.nextInt(3) + 1;
	}

	private Item getReward(Random par2Random) {
		if (par2Random.nextInt(4) == 0) {
			switch (par2Random.nextInt(10)) {
				default: {
					return YetAnotherMod.poopHarvester;
				}
				case 1: {
					return Items.gold_ingot;
				}
				case 2: {
					return Items.iron_ingot;
				}
				case 3: {
					return YetAnotherMod.rust;
				}
				case 4: {
					return pills[par2Random.nextInt(pills.length)];
				}
				case 5: {
					return YetAnotherMod.hearthalf;
				}
				case 6: {
					return YetAnotherMod.heart;
				}
				case 7: {
					return YetAnotherMod.flySwat;
				}
			}
		}
		return null;
	}
	
	public boolean canPlaceBlockAt(World p_149742_1_, int p_149742_2_, int p_149742_3_, int p_149742_4_)
    {
        return p_149742_1_.isSideSolid(p_149742_2_, p_149742_3_ - 1, p_149742_4_, ForgeDirection.UP);
    }
	
	public boolean canBlockStay(World p_149718_1_, int p_149718_2_, int p_149718_3_, int p_149718_4_)
    {
        return this.canPlaceBlockAt(p_149718_1_, p_149718_2_, p_149718_3_, p_149718_4_);
    }

}
