package com.joshmanisdabomb.aimagg.util;

import java.util.ArrayList;
import java.util.Random;

import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockOre;
import com.joshmanisdabomb.aimagg.blocks.AimaggBlockStorage;
import com.joshmanisdabomb.aimagg.items.AimaggItemIngot;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;

public enum OreIngotStorage implements IStringSerializable {
	
	RUBY(2, 3.0F, MapColor.STONE, MapColor.TNT, OreType.DROP_INGOT, IngotType.NORMAL, StorageType.NORMAL),
	SAPPHIRE(2, 3.0F, MapColor.STONE, MapColor.WATER, OreType.DROP_INGOT, IngotType.NORMAL, StorageType.NORMAL),
	TOPAZ(2, 3.0F, MapColor.STONE, MapColor.ADOBE, OreType.DROP_INGOT, IngotType.NORMAL, StorageType.NORMAL),
	AMETHYST(2, 3.0F, MapColor.STONE, MapColor.PURPLE, OreType.DROP_INGOT, IngotType.NORMAL, StorageType.NORMAL),
	URANIUM(3, 4.0F, MapColor.STONE, MapColor.LIME, OreType.DROP_SELF, IngotType.NORMAL, StorageType.NORMAL, 0, 0, 48, 6, 1);

	public static OreIngotStorage[] oreArray;
	public static OreIngotStorage[] oreWGArray;
	public static OreIngotStorage[] ingotArray;
	public static OreIngotStorage[] storageArray;
	
	public final static String[] oreIds = new String[]{"ore"};
	public final static String[] ingotIds = new String[]{"ingot"};
	public final static String[] storageIds = new String[]{"storage"};
	
	private final int harvestLevel;
	private final float hardness;
	
	private final OreType ore;
	private final IngotType ingot;
	private final StorageType storage;
	
	private final MapColor oreMapColor;
	private final MapColor storageMapColor;
	
	private final ModelResourceLocation mrlOre;
	private final ModelResourceLocation mrlIngot;
	private final ModelResourceLocation mrlStorage;

	private boolean builtInWorldGen = false;
	private int wg_dimensionID;
	private int wg_minHeight;
	private int wg_maxHeight;
	private int wg_blockCount;
	private int wg_commonality;

	OreIngotStorage(int harvestLevel, float hardness, MapColor oreMapColor, MapColor storageMapColor, OreType ore, IngotType ingot, StorageType storage) {
		this.harvestLevel = harvestLevel;
		this.hardness = hardness;
		
		this.oreMapColor = oreMapColor;
		this.storageMapColor = storageMapColor;
		
		this.mrlOre = ((this.ore = ore) != OreType.NONE) ? new ModelResourceLocation(Constants.MOD_ID + ":ore/" + this.name().toLowerCase()) : null;
		this.mrlIngot = ((this.ingot = ingot) != IngotType.NONE) ? new ModelResourceLocation(Constants.MOD_ID + ":ingot/" + this.name().toLowerCase()) : null;
		this.mrlStorage = ((this.storage = storage) != StorageType.NONE) ? new ModelResourceLocation(Constants.MOD_ID + ":storage/" + this.name().toLowerCase()) : null;
	}

	OreIngotStorage(int harvestLevel, float hardness, MapColor oreMapColor, MapColor storageMapColor, OreType ore, IngotType ingot, StorageType storage, int wg_dimensionID, int wg_minHeight, int wg_maxHeight, int wg_blockCount, int wg_commonality) {
		this(harvestLevel, hardness, oreMapColor, storageMapColor, ore, ingot, storage);
		
		this.builtInWorldGen = ore != OreType.NONE;
		this.wg_dimensionID = wg_dimensionID;
		this.wg_minHeight = wg_minHeight;
		this.wg_maxHeight = wg_maxHeight;
		this.wg_blockCount = wg_blockCount;
		this.wg_commonality = wg_commonality;
	}

	public float getOreHardness() {
		return this.hardness;
	}

	public float getOreResistance() {
		return this.getOreHardness() * (5.0F/3.0F);
	}

	public SoundType getOreSoundType() {
		return SoundType.STONE;
	}

	public String getOreHarvestTool() {
		return "pickaxe";
	}

	public int getOreHarvestLevel() {
		return this.harvestLevel;
	}
	
	public MapColor getOreMapColor() {
		return this.oreMapColor;
	}

	public float getStorageHardness() {
		return this.hardness * (5.0F/3.0F);
	}

	public float getStorageResistance() {
		return this.getStorageHardness() * (5.0F/3.0F);
	}

	public SoundType getStorageSoundType() {
		return SoundType.METAL;
	}

	public String getStorageHarvestTool() {
		return "pickaxe";
	}

	public int getStorageHarvestLevel() {
		return this.harvestLevel;
	}
	
	public MapColor getStorageMapColor() {
		return this.storageMapColor;
	}

	public boolean hasOreForm() {
		return ore != OreType.NONE;
	}
	
	public boolean hasIngotForm() {
		return ingot != IngotType.NONE;
	}
	
	public boolean hasStorageForm() {
		return storage != StorageType.NONE;
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
	
	public static OreIngotStorage[] getAllWithOreForm(Block b) {
		ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
		for (OreIngotStorage ois : OreIngotStorage.values()) {
			if (ois.hasOreForm() && ois.getOreBlock() == b) {
				list.add(ois);
			}
		}
		return list.toArray(new OreIngotStorage[list.size()]);
	}

	public static OreIngotStorage[] getAllWithIngotForm(Item i) {
		ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
		for (OreIngotStorage ois : OreIngotStorage.values()) {
			if (ois.hasIngotForm() && ois.getIngotItem() == i) {
				list.add(ois);
			}
		}
		return list.toArray(new OreIngotStorage[list.size()]);
	}
	
	public static OreIngotStorage[] getAllWithStorageForm(Block b) {
		ArrayList<OreIngotStorage> list = new ArrayList<OreIngotStorage>();
		for (OreIngotStorage ois : OreIngotStorage.values()) {
			if (ois.hasStorageForm() && ois.getStorageBlock() == b) {
				list.add(ois);
			}
		}
		return list.toArray(new OreIngotStorage[list.size()]);
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
	
	public static OreIngotStorage getFromID(int id) {
		return OreIngotStorage.values()[id];
	}

	public static OreIngotStorage getFromMetadata(AimaggBlockOre b, int meta) {
		return OreIngotStorage.getFromID(meta + (b.getOISid() * 16));
	}

	public static OreIngotStorage getFromMetadata(AimaggItemIngot i, int meta) {
		return OreIngotStorage.getFromID(meta + (i.getOISid() * 16));
	}

	public static OreIngotStorage getFromMetadata(AimaggBlockStorage b, int meta) {
		return OreIngotStorage.getFromID(meta + (b.getOISid() * 16));
	}

	public ModelResourceLocation getOreModel() {
		return mrlOre;
	}

	public ModelResourceLocation getIngotModel() {
		return mrlIngot;
	}

	public ModelResourceLocation getStorageModel() {
		return mrlStorage;
	}

	public static void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) {
		for (OreIngotStorage ois : getAllWithOreFormPlusWorldGen()) {
			if (world.provider.getDimension() == ois.wg_dimensionID) {
				if (ois.wg_commonality > 0) {
					for (int i = 0; i < ois.wg_commonality; i++) {
						int x = (chunkX * 16) + random.nextInt(16);
						int z = (chunkZ * 16) + random.nextInt(16);
						int y = ois.wg_minHeight + random.nextInt((ois.wg_maxHeight - ois.wg_minHeight) + 1);
						new WorldGenMinable(ois.getOreBlockState(), ois.wg_blockCount)
						.generate(world, random, new BlockPos(x,y,z));
					}
				} else if (random.nextInt(1-ois.wg_commonality) == 0) {
					int x = (chunkX * 16) + random.nextInt(16);
					int z = (chunkZ * 16) + random.nextInt(16);
					int y = ois.wg_minHeight + random.nextInt((ois.wg_maxHeight - ois.wg_minHeight) + 1);
					new WorldGenMinable(ois.getOreBlockState(), ois.wg_blockCount)
					.generate(world, random, new BlockPos(x,y,z));
				}
			}
		}
	}

	public Block getOreBlock() {
		if (!this.hasOreForm()) {return null;}
		return Block.getBlockFromName(Constants.MOD_ID + ":" + oreIds[this.ordinal() / 16]);
	}
	
	public Item getIngotItem() {
		if (!this.hasIngotForm()) {return null;}
		return Item.getByNameOrId(Constants.MOD_ID + ":" + ingotIds[0]);
	}
	
	public Block getStorageBlock() {
		if (!this.hasStorageForm()) {return null;}
		return Block.getBlockFromName(Constants.MOD_ID + ":" + storageIds[this.ordinal() / 16]);
	}
	
	public IBlockState getOreBlockState() {
		Block b = this.getOreBlock();
		return b != null ? b.getDefaultState().withProperty(AimaggBlockOre.TYPE, this) : null;
	}
	
	public IBlockState getStorageBlockState() {
		Block b = this.getStorageBlock();
		return b != null ? b.getDefaultState().withProperty(AimaggBlockStorage.TYPE, this) : null;
	}
	
	public int getOreMetadata() {
		if (!this.hasOreForm()) {return 0;}
		return this.ordinal() % 16;
	}
	
	public int getIngotMetadata() {
		if (!this.hasIngotForm()) {return 0;}
		return this.ordinal();
	}
	
	public int getStorageMetadata() {
		if (!this.hasStorageForm()) {return 0;}
		return this.ordinal() % 16;
	}
	
	public static enum OreType {
		NONE,
		DROP_SELF,
		DROP_INGOT,
		DROP_INGOTS_LIKE_REDSTONE,
		DROP_INGOTS_LIKE_LAPIS;
	}

	public OreType getOreType() {
		return ore;
	}
	
	public static enum IngotType {
		NONE,
		NORMAL;
	}

	public IngotType getIngotType() {
		return ingot;
	}
	
	public static enum StorageType {
		NONE,
		NORMAL;
	}

	public StorageType getStorageType() {
		return storage;
	}
	
}
