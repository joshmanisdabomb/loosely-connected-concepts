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
	LIGHTDIRT(1, YetAnotherMod.lightDirt, Blocks.dirt, YetAnotherMod.mud, Blocks.farmland, YetAnotherMod.rainbowDirt),
	LIGHTSTONE(1, YetAnotherMod.lightStone, Blocks.stone),
	LIGHTCOBBLESTONE(1, YetAnotherMod.lightCobblestone, Blocks.cobblestone),
	LIGHTSAND(1, YetAnotherMod.lightSand, Blocks.sand, YetAnotherMod.quicksand),
	LIGHTSANDSTONE(1, YetAnotherMod.lightSandstone, Blocks.sandstone),
	LIGHTGRAVEL(1, YetAnotherMod.lightGravel, Blocks.gravel),
	LIGHTMOSSYCOBBLESTONE(1, YetAnotherMod.lightMossyCobblestone, Blocks.mossy_cobblestone),
	LIGHTPOLISHEDSTONE(1, YetAnotherMod.lightPolishedStone, Blocks.obsidian),
	LIGHTLOG(1, YetAnotherMod.lightWood, Blocks.log, Blocks.log2),
	LIGHTPLANKS(1, YetAnotherMod.lightPlanks, Blocks.planks),
	LIGHTLEAVES(1, YetAnotherMod.lightLeaves, Blocks.leaves, Blocks.leaves2),
	LIGHTSAPLING(1, YetAnotherMod.lightSapling, Blocks.sapling),
	LIGHTBRICKS(1, YetAnotherMod.lightBricks, Blocks.brick_block, Blocks.nether_brick),
	LIGHTCLAY(1, YetAnotherMod.lightClay, Blocks.clay),
	LIGHTWOOL(1, YetAnotherMod.lightWool, Blocks.wool),
	LIGHTGLASS(1, YetAnotherMod.lightGlass, Blocks.glass),
	LIGHTNETHERRACK(1, YetAnotherMod.lightNetherrack, Blocks.netherrack),
	LIGHTSOULSAND(1, YetAnotherMod.lightSoulsand, Blocks.soul_sand),
	LIGHTGLOWSTONE(1, YetAnotherMod.lightGlowstone, Blocks.glowstone, Blocks.redstone_lamp, Blocks.lit_redstone_lamp),
	LIGHTENDSTONE(1, YetAnotherMod.lightEndstone, Blocks.end_stone, Blocks.end_portal_frame),
	WISHSTONEORE(1, YetAnotherMod.wishstoneOre, Blocks.coal_ore, Blocks.iron_ore, YetAnotherMod.saltOre),
	HOPESTONEORE(1, YetAnotherMod.hopestoneOre, Blocks.gold_ore, Blocks.emerald_ore, Blocks.redstone_ore, Blocks.lapis_ore, YetAnotherMod.bigxOre),
	HOPESTONENETHERORE(1, YetAnotherMod.hopestoneNetherOre, Blocks.quartz_ore),
	DREAMSTONEORE(1, YetAnotherMod.dreamstoneOre, Blocks.diamond_ore, YetAnotherMod.rubyOre, YetAnotherMod.rustOre, YetAnotherMod.uraniumOre),
	WISHSTONEBLOCK(1, YetAnotherMod.wishstoneBlock, Blocks.coal_block, Blocks.iron_block),
	HOPESTONEBLOCK(1, YetAnotherMod.hopestoneBlock, Blocks.gold_block, Blocks.emerald_block, Blocks.redstone_block, Blocks.lapis_block, YetAnotherMod.bigxBlock),
	DREAMSTONEBLOCK(1, YetAnotherMod.dreamstoneBlock, Blocks.diamond_block, YetAnotherMod.rubyBlock, YetAnotherMod.rustBlock, YetAnotherMod.uraniumBlock),

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
