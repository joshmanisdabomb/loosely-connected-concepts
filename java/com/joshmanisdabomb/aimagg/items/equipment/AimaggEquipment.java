package com.joshmanisdabomb.aimagg.items.equipment;

import java.util.ArrayList;

import com.google.common.collect.Multimap;
import com.joshmanisdabomb.aimagg.AimaggItems;
import com.joshmanisdabomb.aimagg.AimlessAgglomeration;
import com.joshmanisdabomb.aimagg.Constants;
import com.joshmanisdabomb.aimagg.items.AimaggItemBasic;
import com.joshmanisdabomb.aimagg.util.OreIngotStorage;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class AimaggEquipment {

	public static final ToolMaterial RUBY_TM = EnumHelper.addToolMaterial("aimagg_ruby", 3, 1952, 10.0F, 4.0F, 16).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.RUBY.getIngotMetadata()));
	public static final ArmorMaterial RUBY_AM = EnumHelper.addArmorMaterial("aimagg_ruby", "aimagg:ruby", 39, new int[] { 3, 7, 9, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.25F).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.RUBY.getIngotMetadata()));
	public static final ToolMaterial TOPAZ_TM = EnumHelper.addToolMaterial("aimagg_topaz", 3, 1952, 10.0F, 4.0F, 16).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.TOPAZ.getIngotMetadata()));
	public static final ArmorMaterial TOPAZ_AM = EnumHelper.addArmorMaterial("aimagg_topaz", "aimagg:topaz", 39, new int[] { 3, 7, 9, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.25F).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.TOPAZ.getIngotMetadata()));
	public static final ToolMaterial EMERALD_TM = EnumHelper.addToolMaterial("aimagg_emerald", 3, ToolMaterial.DIAMOND.getMaxUses(), ToolMaterial.DIAMOND.getEfficiencyOnProperMaterial(), ToolMaterial.DIAMOND.getDamageVsEntity(), ToolMaterial.DIAMOND.getEnchantability()).setRepairItem(new ItemStack(Items.EMERALD, 1, 0));
	public static final ArmorMaterial EMERALD_AM = EnumHelper.addArmorMaterial("aimagg_emerald", "aimagg:emerald", 33, new int[] { 3, 6, 8, 3 }, ArmorMaterial.DIAMOND.getEnchantability(), SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, ArmorMaterial.DIAMOND.getToughness()).setRepairItem(new ItemStack(Items.EMERALD, 1, 0));
	public static final ToolMaterial SAPPHIRE_TM = EnumHelper.addToolMaterial("aimagg_sapphire", 3, 1952, 10.0F, 4.0F, 16).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.SAPPHIRE.getIngotMetadata()));
	public static final ArmorMaterial SAPPHIRE_AM = EnumHelper.addArmorMaterial("aimagg_sapphire", "aimagg:sapphire", 39, new int[] { 3, 7, 9, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.25F).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.SAPPHIRE.getIngotMetadata()));
	public static final ToolMaterial AMETHYST_TM = EnumHelper.addToolMaterial("aimagg_amethyst", 3, 1952, 10.0F, 4.0F, 16).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.AMETHYST.getIngotMetadata()));
	public static final ArmorMaterial AMETHYST_AM = EnumHelper.addArmorMaterial("aimagg_amethyst", "aimagg:amethyst", 39, new int[] { 3, 7, 9, 3 }, 16, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 2.25F).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.AMETHYST.getIngotMetadata()));

	public static final ToolMaterial NEON_TM = EnumHelper.addToolMaterial("aimagg_neon", 3, 382, 13.0F, 5.0F, 32).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.NEON.getIngotMetadata()));
	public static final ArmorMaterial NEON_AM = EnumHelper.addArmorMaterial("aimagg_neon", "aimagg:neon", 30, new int[] { 4, 8, 10, 4 }, 32, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 1.0F).setRepairItem(new ItemStack(AimaggItems.ingot, 1, OreIngotStorage.NEON.getIngotMetadata()));

	public static final ArmorMaterial CLASSIC_LEATHER_AM = EnumHelper.addArmorMaterial("aimagg_classic_leather", "aimagg:classic_leather", 5, new int[] { 1, 2, 3, 1 }, ArmorMaterial.LEATHER.getEnchantability(), SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, ArmorMaterial.LEATHER.getToughness()).setRepairItem(new ItemStack(Items.LEATHER, 1, 0));
	public static final ArmorMaterial CLASSIC_STUDDED_AM = EnumHelper.addArmorMaterial("aimagg_classic_studded", "aimagg:classic_studded", 20, new int[] { 2, 3, 4, 2 }, 22, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.25F).setRepairItem(new ItemStack(Items.LEATHER, 1, 0));

	public static final ArrayList<Item> registry = new ArrayList<Item>();
	
	static final float DURABILITY_SHEARS_RATIO = Items.SHEARS.getMaxDamage() / ToolMaterial.IRON.getMaxUses();
	
	private final String type;
	private final ToolMaterial toolMaterial;
	private final ArmorMaterial armorMaterial;
	
	public final Item sword, pickaxe, shovel, axe, hoe, shears;
	public final Item helmet, chestplate, leggings, boots;
	
	private static int id;
	
	public AimaggEquipment(ToolMaterial tm, ArmorMaterial am, ToolType sword, ToolType pickaxe, ToolType shovel, ToolType axe, ToolType hoe, ToolType shears, ArmorType helmet, ArmorType chestplate, ArmorType leggings, ArmorType boots) {
		this.armorMaterial = am;
		if ((this.toolMaterial = tm) != null) {
			this.type = tm.name().toLowerCase();
		} else {
			this.type = am.name().toLowerCase();
		}
		String typeWithoutDomain = this.type.substring("aimagg_".length());
		String currentInternalName;
		
		currentInternalName = typeWithoutDomain + "_sword";
		if (sword == ToolType.NORMAL) {
			this.sword = new ItemSword(tm).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.sword);
		} else if (sword == ToolType.SPECIALISED) {
			this.sword = new AimaggItemCustomSword(currentInternalName, tm);
		} else {
			this.sword = null;
		}

		currentInternalName = typeWithoutDomain + "_pickaxe";
		if (pickaxe == ToolType.NORMAL) {
			this.pickaxe = new ItemUnnecessarySubclassPickaxe(tm).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.pickaxe);
		} else if (pickaxe == ToolType.SPECIALISED) {
			this.pickaxe = new AimaggItemCustomPickaxe(currentInternalName, tm);
		} else {
			this.pickaxe = null;
		}

		currentInternalName = typeWithoutDomain + "_shovel";
		if (shovel == ToolType.NORMAL) {
			this.shovel = new ItemSpade(tm).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.shovel);
		} else if (shovel == ToolType.SPECIALISED) {
			this.shovel = new AimaggItemCustomShovel(currentInternalName, tm);
		} else {
			this.shovel = null;
		}

		currentInternalName = typeWithoutDomain + "_axe";
		if (axe == ToolType.NORMAL) {
			this.axe = new ItemUnnecessarySubclassAxe(tm).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.axe);
		} else if (axe == ToolType.SPECIALISED) {
			this.axe = new AimaggItemCustomAxe(currentInternalName, tm);
		} else {
			this.axe = null;
		}

		currentInternalName = typeWithoutDomain + "_hoe";
		if (hoe == ToolType.NORMAL) {
			this.hoe = new ItemHoe(tm).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.hoe);
		} else if (hoe == ToolType.SPECIALISED) {
			this.hoe = new AimaggItemCustomHoe(currentInternalName, tm);
		} else {
			this.hoe = null;
		}

		currentInternalName = typeWithoutDomain + "_shears";
		if (shears == ToolType.NORMAL) {
			this.shears = new ItemShears().setMaxDamage(MathHelper.floor(tm.getMaxUses() * DURABILITY_SHEARS_RATIO)).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.shears);
		} else if (shears == ToolType.SPECIALISED) {
			this.shears = new AimaggItemCustomShears(currentInternalName, tm);
		} else {
			this.shears = null;
		}

		currentInternalName = typeWithoutDomain + "_helmet";
		if (helmet == ArmorType.NORMAL) {
			this.helmet = (new ItemArmor(am, id, EntityEquipmentSlot.HEAD)).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.helmet);
		} else {
			this.helmet = null;
		}

		currentInternalName = typeWithoutDomain + "_chestplate";
		if (chestplate == ArmorType.NORMAL) {
			this.chestplate = (new ItemArmor(am, id, EntityEquipmentSlot.CHEST)).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.chestplate);
		} else {
			this.chestplate = null;
		}

		currentInternalName = typeWithoutDomain + "_leggings";
		if (leggings == ArmorType.NORMAL) {
			this.leggings = (new ItemArmor(am, id, EntityEquipmentSlot.LEGS)).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.leggings);
		} else {
			this.leggings = null;
		}

		currentInternalName = typeWithoutDomain + "_boots";
		if (boots == ArmorType.NORMAL) {
			this.boots = (new ItemArmor(am, id, EntityEquipmentSlot.FEET)).setUnlocalizedName(Constants.MOD_ID + ":" + currentInternalName).setRegistryName(currentInternalName).setCreativeTab(AimlessAgglomeration.tab);
			AimaggEquipment.registry.add(this.boots);
		} else {
			this.boots = null;
		}
		
		id += 1;
	}

	public Item[] getAllItems() {
		return new Item[]{this.sword, this.pickaxe, this.shovel, this.axe, this.hoe, this.shears, this.helmet, this.chestplate, this.leggings, this.boots};
	}
	
	private static class ItemUnnecessarySubclassPickaxe extends ItemPickaxe { public ItemUnnecessarySubclassPickaxe(ToolMaterial material) {super(material);} }
	private static class ItemUnnecessarySubclassAxe extends ItemAxe { public ItemUnnecessarySubclassAxe(ToolMaterial material) {super(material, (material.getDamageVsEntity() + 3.0F) / 0.75F, -3.3F + (material.getDamageVsEntity() * 0.1F));} }
	
	static abstract class AimaggItemCustomTool extends AimaggItemBasic {

		final ToolMaterial toolMaterial;
		
		public AimaggItemCustomTool(String internalName, ToolMaterial tm) {
			super(internalName);
			this.toolMaterial = tm;
			this.setMaxStackSize(1);
	        this.setMaxDamage(tm.getMaxUses());
		}
		
		public abstract double getAttackDamage();
		
		public abstract double getAttackSpeed();
		
		public abstract int getHitDurabilityLoss();
		
		public abstract int getBreakDurabilityLoss();

		@Override
		public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
	        stack.damageItem(this.getHitDurabilityLoss(), attacker);
	        return true;
	    }

		@Override
		public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
			if (state.getBlockHardness(worldIn, pos) != 0.0F) {
				stack.damageItem(this.getBreakDurabilityLoss(), entityLiving);
			}
			return true;
		}

		@Override
		public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot equipmentSlot, ItemStack stack) {
			Multimap<String, AttributeModifier> multimap = super.getAttributeModifiers(equipmentSlot, stack);

			if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
				multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.getAttackDamage(), 0));
				multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Weapon modifier", this.getAttackSpeed(), 0));
			}

			return multimap;
		}

		@Override
		public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
			ItemStack mat = this.toolMaterial.getRepairItemStack();
			if (!mat.isEmpty() && net.minecraftforge.oredict.OreDictionary.itemMatches(mat, repair, false))
				return true;
			return super.getIsRepairable(toRepair, repair);
		}
		
		@Override
		public int getItemEnchantability() {
	        return this.toolMaterial.getEnchantability();
	    }

		public String getToolMaterialName() {
	        return this.toolMaterial.toString();
	    }
		
		@SideOnly(Side.CLIENT)
	    public boolean isFull3D() {
	        return true;
	    }
		
	}

	public static void registerRenders(ModelRegistryEvent event) {
		for (Item i : AimaggEquipment.registry) {
			ModelLoader.setCustomModelResourceLocation(i, 0, new ModelResourceLocation(Constants.MOD_ID + ":equipment/" + i.getRegistryName().getResourcePath(), "inventory"));
		}
	}
	
	public static enum ToolType {
		NONE,
		NORMAL,
		SPECIALISED;
	}
	
	public static enum ArmorType {
		NONE,
		NORMAL;
	}
	
}
