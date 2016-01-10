package yam.gen;

import java.util.Random;

import net.minecraft.world.World;

public abstract class GenTree {

	/**The sapling is at x,y,z.
	 * The block below is at x,y-1,z.
	 * 
	 * @param world - The world object.
	 * @param rand - The random object passed down by the sapling.
	 * @param x
	 * @param y
	 * @param z
	 */
	public abstract boolean generateFromSapling(World world, Random rand, int x, int y, int z);

	/**x,y,z is an empty space. The block below must be checked and should be set to dirt.
	 * 
	 * @param world - The world object.
	 * @param rand - Seeded random.
	 * @param x
	 * @param y
	 * @param z
	 */
	public abstract boolean generateFromGen(World world, Random rand, int x, int y, int z);
	
}
