package yam.auraconv;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.world.World;
import yam.YetAnotherMod;

public enum AuraConversion {

	//to, from...
	//   or
	//to, conv class
	
	LIGHTGRASS(1, YetAnotherMod.lightGrass, Blocks.grass, Blocks.mycelium),
	LIGHTDIRT(1, YetAnotherMod.lightDirt, Blocks.dirt, YetAnotherMod.mud),
	LIGHTSTONE(1, YetAnotherMod.lightStone, Blocks.stone),
	LIGHTCOBBLESTONE(1, YetAnotherMod.lightCobblestone, Blocks.cobblestone, Blocks.sandstone),
	LIGHTSAND(1, YetAnotherMod.lightSand, Blocks.sand, YetAnotherMod.quicksand),
	LIGHTGRAVEL(1, YetAnotherMod.lightGravel, Blocks.gravel),
	LIGHTMOSSYCOBBLESTONE(1, YetAnotherMod.lightMossyCobblestone, Blocks.mossy_cobblestone),
	LIGHTPOLISHEDSTONE(1, YetAnotherMod.lightPolishedStone, Blocks.obsidian),
	LIGHTLOG(1, YetAnotherMod.lightWood, Blocks.log, Blocks.log2),
	LIGHTPLANKS(1, YetAnotherMod.lightPlanks, Blocks.planks),
	LIGHTLEAVES(1, YetAnotherMod.lightLeaves, Blocks.leaves, Blocks.leaves2),
	LIGHTSAPLING(1, YetAnotherMod.lightSapling, Blocks.sapling),
	LIGHTBRICKS(1, YetAnotherMod.lightBricks, Blocks.brick_block),
	WISHSTONEORE(1, YetAnotherMod.wishstoneOre, Blocks.coal_ore, Blocks.iron_ore, YetAnotherMod.saltOre),
	HOPESTONEORE(1, YetAnotherMod.hopestoneOre, Blocks.gold_ore, Blocks.emerald_ore, Blocks.redstone_ore, Blocks.lapis_ore, YetAnotherMod.bigxOre),
	DREAMSTONEORE(1, YetAnotherMod.dreamstoneOre, Blocks.diamond_ore, YetAnotherMod.rubyOre, YetAnotherMod.rustOre, YetAnotherMod.uraniumOre),

	MUSHROOM(1, new ConvMushroom()),
	CRACKEDMUD(1, new ConvCrackedMud()),
	RANDOMBRICK(1, new ConvRandomBricks());

	private static ArrayList<AuraConversion> list1 = new ArrayList<AuraConversion>();
	private static ArrayList<AuraConversion> list2 = new ArrayList<AuraConversion>();
	
	private int aura;
	private Block[] from; private Block to;
	private Conv conversion;
	
	private AuraConversion(int aura, Block to, Block... from) {
		this.aura = aura;
		this.to = to;
		this.from = from;
	}
	
	private AuraConversion(int aura, Conv conversion) {
		this.aura = aura;
		this.conversion = conversion;
	}
	
	public static boolean convertBlockWithAura(int aura, World w, Random r, int x, int y, int z) {
		for (AuraConversion ac : AuraConversion.values()) {
			if (ac.conversion == null) {
				for (Block b : ac.from) {
					if (w.getBlock(x, y, z) == b) {
						w.setBlock(x, y, z, ac.to);
						return true;
					}
				}
			} else {
				if (ac.conversion.isBlockApplicable(w.getBlock(x, y, z))) {
					ac.conversion.convertBlockWithAura(aura, w, r, x, y, z);
					return true;
				}
			}
		}
		return false;
	}
	
}
