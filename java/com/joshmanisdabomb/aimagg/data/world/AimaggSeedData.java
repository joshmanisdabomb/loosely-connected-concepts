package com.joshmanisdabomb.aimagg.data.world;

import com.joshmanisdabomb.aimagg.Constants;

import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

public class AimaggSeedData extends WorldSavedData {

	private static AimaggSeedData instance = null;
	private final static String key = Constants.MOD_ID + "_seed";
	
	private long seed;
	
	private World world;
	
	public AimaggSeedData() {
		this(key);
	}

	public AimaggSeedData(String name) {
		super(name);
	}

	public static AimaggSeedData getInstance(World world) {
        if (instance != null) {
            return instance;
        }
        instance = (AimaggSeedData)world.getMapStorage().getOrLoadData(AimaggSeedData.class, key);
        if (instance == null) {
            instance = new AimaggSeedData(key);
        }
        instance.setWorld(world);
        return instance;
    }
	
	public static long getAimaggSeed(World world) {
		return AimaggSeedData.getInstance(world).getAimaggSeed();
	}
	
	private void setWorld(World world) {
		this.world = world;
	}
	
	public void save(World world) {
        world.getMapStorage().setData(key, this);
        markDirty();
    }
	
	@Override
	public void readFromNBT(NBTTagCompound nbt) {
		this.seed = nbt.getLong("world_seed");
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
		nbt.setLong("world_seed", this.seed);
	    return nbt;
	}

	public long getAimaggSeed() {
		return this.seed;
	}

	public void setAimaggSeed(long seed) {
		this.seed = seed;
	}

}
