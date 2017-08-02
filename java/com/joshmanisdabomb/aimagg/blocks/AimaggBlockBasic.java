package com.joshmanisdabomb.aimagg.blocks;

import java.util.Comparator;
import java.util.Random;

import com.joshmanisdabomb.aimagg.AimaggBlocks;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemColored;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Rotation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

public class AimaggBlockBasic extends Block {

	private final String internalName;
	
	private final int sortValue;

	private final ItemBlock itemBlock;

	private Item drops_item;
	private int drops_minAmount = 1;
	private int drops_maxAmount = 1;
	private float drops_chance = 1.0F;
	private int drops_minAmountAddPerFortune = 0;
	private int drops_maxAmountAddPerFortune = 0;
	private float drops_chanceAddPerFortune = 0.0F;

	private boolean dropsST_on = false;
	private Item dropsST_item;
	private int dropsST_amount = 0;

	private int drops_damage = -1;
	
	public AimaggBlockBasic(String internalName, int sortVal, Material material, MapColor mcolor) {
		super(material, mcolor);
		this.setUnlocalizedName(this.internalName = internalName);
		this.setRegistryName(this.internalName);
		this.setCreativeTab(AimlessAgglomeration.tab);
		this.sortValue = sortVal;
		
		AimaggBlocks.registry.add(this);
		AimaggBlocks.ibRegistry.add(this.itemBlock = this.createItemBlock());
		
		this.initialise();
	}

	public String getInternalName() {
		return internalName;
	}

	public int getSortValue(ItemStack is) {
		return sortValue;
	}

	public ItemBlock createItemBlock() {
		ItemBlock ib = new ItemBlock(this);
		ib.setRegistryName(this.getRegistryName());
		return ib;
	}

	public ItemBlock getItemBlock() {
		return itemBlock;
	}

	@Override
	public Block setSoundType(SoundType st) {
		super.setSoundType(st);
		return this;
	}

	//Handling drops.
	public void setDrops(Block b, int minAmount, int maxAmount, float chance, int minAmountAddPerFortune, int maxAmountAddPerFortune, float chanceAddPerFortune) {
		this.setDrops(Item.getItemFromBlock(b), minAmount, maxAmount, chance, minAmountAddPerFortune, maxAmountAddPerFortune, chanceAddPerFortune);
	}
	
	public void setDrops(Item i, int minAmount, int maxAmount, float chance, int minAmountAddPerFortune, int maxAmountAddPerFortune, float chanceAddPerFortune) {
		this.drops_item = i;
		this.drops_minAmount = minAmount;
		this.drops_maxAmount = maxAmount;
		this.drops_chance = chance;
		this.drops_minAmountAddPerFortune = minAmountAddPerFortune;
		this.drops_maxAmountAddPerFortune = maxAmountAddPerFortune;
		this.drops_chanceAddPerFortune = chanceAddPerFortune;
	}
	
	public void setSilkTouchDrops(Block b, int amount) {
		this.setSilkTouchDrops(Item.getItemFromBlock(b), amount);
	}
	
	public void setSilkTouchDrops(Item i, int amount) {
		this.dropsST_on = true;
		this.dropsST_item = i;
		this.dropsST_amount = amount;
	}
	
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return this.drops_item == null ? Item.getItemFromBlock(this) : this.drops_item;
    }

	@Override
	public boolean canSilkHarvest(World world, BlockPos pos, IBlockState state, EntityPlayer player) {
		return this.dropsST_on;
	}

	@Override
	public ItemStack getSilkTouchDrop(IBlockState state) {
        return new ItemStack(dropsST_item, dropsST_amount, this.damageDropped(state));
    }
	
	@Override
	public int quantityDropped(IBlockState state, int fortune, Random random) {
		float chance = this.drops_chance + (this.drops_chanceAddPerFortune * fortune);
		int min = this.drops_minAmount + (this.drops_minAmountAddPerFortune * fortune);
		int max = this.drops_maxAmount + (this.drops_maxAmountAddPerFortune * fortune);
		return (random.nextFloat() < chance) ? min + random.nextInt((max - min) + 1) : 0;
	}

	public void alwaysDropWithDamage(int damage) {
		this.drops_damage = damage;
	}
	
	@Override
	public int damageDropped(IBlockState state) {
        return this.drops_damage > 0 ? this.drops_damage : this.getMetaFromState(state);
    }
	
	public void initialise() {
	}

	public void registerInventoryRender() {
		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(Constants.MOD_ID + ":" + this.getInternalName(), "inventory"));
	}

}
