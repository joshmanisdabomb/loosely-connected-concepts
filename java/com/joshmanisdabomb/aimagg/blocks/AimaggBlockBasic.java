package com.joshmanisdabomb.aimagg.blocks;

import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggTab.AimaggCategory;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.util.AimaggHarvestLevel.Specialization;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggBlockBasic extends Block {

	private final String internalName;

	private final ItemBlock itemBlock;

	private Item drops_item = null;
	private Block drops_block = this;
	private int drops_minAmount = 1;
	private int drops_maxAmount = 1;
	private int drops_damage = -1;
	private float drops_chance = 1.0F;
	private int drops_minAmountAddPerFortune = 0;
	private int drops_maxAmountAddPerFortune = 0;
	private float drops_chanceAddPerFortune = 0.0F;

	private Item dropsST_item = null;
	private Block dropsST_block = this;
	private int dropsST_minAmount = 1;
	private int dropsST_maxAmount = 1;
	private int dropsST_damage = -1;
	private float dropsST_chance = 1.0F;
	
	public AimaggBlockBasic(String internalName, Material material, MapColor mcolor) {
		super(material, mcolor);
		this.setUnlocalizedName(Constants.MOD_ID + ":" + (this.internalName = internalName));
		this.setRegistryName(this.internalName);
		this.setCreativeTab(AimlessAgglomeration.tab);
		
		AimaggBlocks.registry.add(this);
		AimaggBlocks.ibRegistry.add(this.itemBlock = this.createItemBlock());
		
		this.init();
	}

	//Internals.
	
	public String getInternalName() {
		return internalName;
	}
	
	public AimaggCategory getCategoryOverride(ItemStack is) {
		return null;
	}

	public int getLowerSortValue(ItemStack is) {
		return is.getMetadata();
	}
	
	//Itemblocks.

	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}

	public ItemBlock getItemBlock() {
		return itemBlock;
	}

	//Setting handling drops.
	
	public void setDrops(Item i, int amount, int damage) {
		this.setDrops(i, amount, amount, damage, 1.0F, 0, 0, 0);
	}
	
	public void setDrops(Item i, int minAmount, int maxAmount, int damage, float chance, int minAmountAddPerFortune, int maxAmountAddPerFortune, float chanceAddPerFortune) {
		this.drops_item = i;
		this.drops_minAmount = minAmount;
		this.drops_maxAmount = maxAmount;
		this.drops_damage = damage;
		this.drops_chance = chance;
		this.drops_minAmountAddPerFortune = minAmountAddPerFortune;
		this.drops_maxAmountAddPerFortune = maxAmountAddPerFortune;
		this.drops_chanceAddPerFortune = chanceAddPerFortune;
	}
	
	public void setSilkTouchDrops(Item i, int minAmount, int maxAmount, int damage, float chance) {
		this.dropsST_item = i;
		this.dropsST_minAmount = minAmount;
		this.dropsST_maxAmount = maxAmount;
		this.dropsST_damage = damage;
		this.dropsST_chance = chance;
	}
	
	public void setDrops(Block b, int amount, int damage) {
		this.setDrops(Item.getItemFromBlock(b), amount, amount, damage, 1.0F, 0, 0, 0);
		this.drops_block = b;
	}
	
	public void setDrops(Block b, int minAmount, int maxAmount, int damage, float chance, int minAmountAddPerFortune, int maxAmountAddPerFortune, float chanceAddPerFortune) {
		this.setDrops(Item.getItemFromBlock(b), minAmount, maxAmount, damage, chance, minAmountAddPerFortune, maxAmountAddPerFortune, chanceAddPerFortune);
		this.drops_block = b;
	}
	
	public void setSilkTouchDrops(Block b, int minAmount, int maxAmount, int damage, float chance) {
		this.setSilkTouchDrops(Item.getItemFromBlock(b), minAmount, maxAmount, damage, chance);
		this.dropsST_block = b;
	}
	
	//Getting handling drops.	
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return (this.drops_item != null && this.drops_item != Items.AIR) ? this.drops_item : Item.getItemFromBlock(this.drops_block);
    }

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return (this.dropsST_item != null && this.dropsST_item != Items.AIR) || (this.dropsST_block != null && this.dropsST_block != Blocks.AIR);
	}

	@Override
	public ItemStack getSilkTouchDrop(IBlockState state) {
		Random rand = new Random();
        ItemStack is = new ItemStack((this.drops_item != null && this.drops_item != Items.AIR) ? this.drops_item : Item.getItemFromBlock(this.drops_block),
        			   (rand.nextFloat() < this.dropsST_chance) ? this.drops_minAmount + rand.nextInt((this.drops_maxAmount - this.drops_minAmount) + 1) : 0,
        			   this.drops_damage != -1 ? this.drops_damage : this.getMetaFromState(state));
        return is;
    }
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		float chance = this.drops_chance + (this.drops_chanceAddPerFortune * fortune);
		int min = this.drops_minAmount + (this.drops_minAmountAddPerFortune * fortune);
		int max = this.drops_maxAmount + (this.drops_maxAmountAddPerFortune * fortune);
		return (random.nextFloat() < chance) ? min + random.nextInt((max - min) + 1) : 0;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
		return this.drops_damage != -1 ? this.drops_damage : this.getMetaFromState(state);
    }
	
	//Misc.

	public boolean isCustomToolEffective(ToolMaterial toolMaterial, Specialization s, ItemStack stack, IBlockState state) {
		return false;
	}

	@Override
	public Block setSoundType(SoundType st) {
		super.setSoundType(st);
		return this;
	}
	
	//Init and loading.
	public final void init() {
		if (this instanceof AimaggBlockColored) {
			AimaggBlocks.colorRegistry.add(this);
			AimaggBlocks.ibColorRegistry.add(this.getItemBlock());
		}
		if (this instanceof AimaggBlockAdvancedRendering) {
			AimaggBlocks.advancedRenderRegistry.add(this);
		}
		this.setDrops(this, 1, -1);
		this.initialise();
	}
	
	public void initialise() {
		
	}

	public void registerInventoryRender() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
	}

}
