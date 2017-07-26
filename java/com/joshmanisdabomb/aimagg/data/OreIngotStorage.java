package com.joshmanisdabomb.aimagg.data;

import java.util.ArrayList;
import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockOre;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public enum OreIngotStorage implements IStringSerializable {
	
	RUBY(0,true,true,true),
	SAPPHIRE(1,true,true,true),
	TOPAZ(2,true,true,true),
	AMETHYST(3,true,true,true),
	URANIUM(4,true,true,true,0,0,48,5,1);

	public static OreIngotStorage[] oreArray;
	public static OreIngotStorage[] oreWGArray;
	public static OreIngotStorage[] ingotArray;
	public static OreIngotStorage[] storageArray;
	
	private int id;
	
	private boolean hasOreForm;
	private boolean hasIngotForm;
	private boolean hasStorageForm;
	
	private final ModelResourceLocation mrl;

	private boolean builtInWorldGen = false;
	private int wg_dimensionID;
	private int wg_minHeight;
	private int wg_maxHeight;
	private int wg_blockCount;
	private int wg_commonality;

	OreIngotStorage(int id, boolean oreForm, boolean ingotForm, boolean storageForm) {
		this.id = id;
		this.hasOreForm = oreForm;
		if (this.hasIngotForm = ingotForm) {
			mrl = new ModelResourceLocation(Constants.MOD_ID + ":ingot/" + this.name());
		} else {
			mrl = null;
		}
		this.hasStorageForm = storageForm;
	}

	OreIngotStorage(int id, boolean oreForm, boolean ingotForm, boolean storageForm, int wg_dimensionID, int wg_minHeight, int wg_maxHeight, int wg_blockCount, int wg_commonality) {
		this(id, oreForm, ingotForm, storageForm);
		
		this.builtInWorldGen = oreForm;
		this.wg_dimensionID = wg_dimensionID;
		this.wg_minHeight = wg_minHeight;
		this.wg_maxHeight = wg_maxHeight;
		this.wg_blockCount = wg_blockCount;
		this.wg_commonality = wg_commonality;
	}

	public boolean hasOreForm() {
		return hasOreForm;
	}
	
	public boolean hasIngotForm() {
		return hasIngotForm;
	}
	
	public boolean hasStorageForm() {
		return hasStorageForm;
	}

	public boolean usesBuiltInWorldGen() {
		return builtInWorldGen;
	}
	
	public static OreIngotStorage[] getAllWithOreForm() {
		if (oreArray == null) {
			ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
			for (OreIngotStorage ois : OreIngotStorage.values()) {
				if (ois.hasOreForm()) {
					list.add(ois);
				}
			}
			oreArray = list.toArray(new OreIngotStorage[list.size()]);
		}
		return oreArray;
	}
	
	public static OreIngotStorage[] getAllWithIngotForm() {
		if (ingotArray == null) {
			ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
			for (OreIngotStorage ois : OreIngotStorage.values()) {
				if (ois.hasIngotForm()) {
					list.add(ois);
				}
			}
			ingotArray = list.toArray(new OreIngotStorage[list.size()]);
		}
		return ingotArray;
	}
	
	public static OreIngotStorage[] getAllWithStorageForm() {
		if (storageArray == null) {
			ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
			for (OreIngotStorage ois : OreIngotStorage.values()) {
				if (ois.hasStorageForm()) {
					list.add(ois);
				}
			}
			storageArray = list.toArray(new OreIngotStorage[list.size()]);
		}
		return storageArray;
	}
	
	public static OreIngotStorage[] getAllWithOreFormPlusWorldGen() {
		if (oreWGArray == null) {
			ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
			for (OreIngotStorage ois : OreIngotStorage.values()) {
				if (ois.hasOreForm() && ois.usesBuiltInWorldGen()) {
					list.add(ois);
				}
			}
			oreWGArray = list.toArray(new OreIngotStorage[list.size()]);
		}
		return oreWGArray;
	}

	@Override
	public String getName() {
		return this.name().toLowerCase();
	}
	
	public int getMetadata() {
		return this.id;
	}
	
	public static OreIngotStorage getFromMetadata(int metadata) {
		for (OreIngotStorage ois : values()) {
			if (metadata == ois.id) {
				return ois;
			}
		}
		return null;
	}

	public ModelResourceLocation getItemModel() {
		return mrl;
	}

	public static void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (OreIngotStorage ois : getAllWithOreForm()) {
			if (ois.wg_commonality > 0) {
				for (int i = 0; i < ois.wg_commonality; i++) {
					int x = (chunkX * 16) + random.nextInt(16);
					int z = (chunkZ * 16) + random.nextInt(16);
					int y = ois.wg_minHeight + random.nextInt((ois.wg_maxHeight - ois.wg_minHeight) + 1);
					new WorldGenMinable(ois.getOreBlock().getDefaultState().withProperty(AimaggBlockOre.TYPE, ois), ois.wg_blockCount)
					.generate(world, random, new BlockPos(x,y,z));
				}
			} else if (random.nextInt(1-ois.wg_commonality) == 0) {
				int x = (chunkX * 16) + random.nextInt(16);
				int z = (chunkZ * 16) + random.nextInt(16);
				int y = ois.wg_minHeight + random.nextInt((ois.wg_maxHeight - ois.wg_minHeight) + 1);
				new WorldGenMinable(ois.getOreBlock().getDefaultState().withProperty(AimaggBlockOre.TYPE, ois), ois.wg_blockCount)
				.generate(world, random, new BlockPos(x,y,z));
			}
		}
	}

	private Block getOreBlock() {
		if (!this.hasOreForm()) {return null;}
		if (this.getMetadata() < 16) {return AimaggBlocks.ore;}
		return null;
	}
	
	private Block getStorageBlock() {
		if (!this.hasStorageForm()) {return null;}
		if (this.getMetadata() < 16) {return AimaggBlocks.storage;}
		return null;
	}
	
}
