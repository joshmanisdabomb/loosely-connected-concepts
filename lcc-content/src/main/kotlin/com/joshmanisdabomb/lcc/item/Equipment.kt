package com.joshmanisdabomb.lcc.item

import com.joshmanisdabomb.lcc.directory.LCCItems
import net.minecraft.entity.EquipmentSlot
import net.minecraft.item.*
import net.minecraft.recipe.Ingredient
import net.minecraft.sound.SoundEvent
import net.minecraft.item.AxeItem as VanillaAxeItem
import net.minecraft.item.HoeItem as VanillaHoeItem
import net.minecraft.item.PickaxeItem as VanillaPickaxeItem

fun SwordItem(toolMaterial: ToolMaterial, settings: Item.Settings, attackDamage: Int = 3, attackSpeed: Float = -2.4F): SwordItem = SwordItem(toolMaterial, attackDamage, attackSpeed, settings)

class PickaxeItem(material: ToolMaterial, settings: Settings, attackDamage: Int = 1, attackSpeed: Float = -2.8F) : VanillaPickaxeItem(material, attackDamage, attackSpeed, settings)

class AxeItem(material: ToolMaterial, settings: Settings, attackDamage: Float = 5.0f, attackSpeed: Float = -3.0F) : VanillaAxeItem(material, attackDamage, attackSpeed, settings)

fun ShovelItem(toolMaterial: ToolMaterial, settings: Item.Settings, attackDamage: Float = 1.5F, attackSpeed: Float = -3.0F): ShovelItem = ShovelItem(toolMaterial, attackDamage, attackSpeed, settings)

class HoeItem(material: ToolMaterial, settings: Settings, attackDamage: Int = -material.attackDamage.toInt(), attackSpeed: Float = 0.0f) : VanillaHoeItem(material, attackDamage, attackSpeed, settings)

enum class LCCToolMaterials(private val durability: Int, private val miningSpeed: Float, private val attackDamage: Float, private val miningLevel: Int, private val enchantability: Int, ingredientFactory: () -> Ingredient) : ToolMaterial {

    RUBY(ToolMaterials.DIAMOND, miningSpeed = ToolMaterials.DIAMOND.miningSpeedMultiplier, ingredientFactory = { Ingredient.ofItems(LCCItems.ruby) }),
    TOPAZ(ToolMaterials.STONE, miningSpeed = ToolMaterials.DIAMOND.miningSpeedMultiplier, ingredientFactory = { Ingredient.ofItems(LCCItems.topaz_shard) }),
    EMERALD(ToolMaterials.IRON, miningSpeed = ToolMaterials.DIAMOND.miningSpeedMultiplier, ingredientFactory = { Ingredient.ofItems(Items.EMERALD) }),
    SAPPHIRE(ToolMaterials.IRON, miningSpeed = ToolMaterials.DIAMOND.miningSpeedMultiplier, ingredientFactory = { Ingredient.ofItems(LCCItems.sapphire) }),
    AMETHYST(ToolMaterials.STONE, miningSpeed = ToolMaterials.DIAMOND.miningSpeedMultiplier, ingredientFactory = { Ingredient.ofItems(Items.AMETHYST_SHARD) });

    constructor(base: ToolMaterial, durability: Int = base.durability, miningSpeed: Float = base.miningSpeedMultiplier, attackDamage: Float = base.attackDamage, miningLevel: Int = base.miningLevel, enchantability: Int = base.enchantability, ingredientFactory: () -> Ingredient = base::getRepairIngredient) : this(durability, miningSpeed, attackDamage, miningLevel, enchantability, ingredientFactory)

    private val ingredient by lazy(ingredientFactory)

    override fun getDurability() = durability
    override fun getMiningSpeedMultiplier() = miningSpeed
    override fun getAttackDamage() = attackDamage
    override fun getMiningLevel() = miningLevel
    override fun getEnchantability() = enchantability
    override fun getRepairIngredient() = ingredient

}

enum class LCCArmorMaterials(durabilityMultiplier: Float, private val protections: IntArray, private val enchantability: Int, private val toughness: Float, private val knockbackResistance: Float, private val equipSound: SoundEvent, ingredientFactory: () -> Ingredient) : ArmorMaterial {

    RUBY(ArmorMaterials.DIAMOND, equipSound = ArmorMaterials.DIAMOND.equipSound, ingredientFactory = { Ingredient.ofItems(LCCItems.ruby) }),
    TOPAZ(ArmorMaterials.LEATHER, equipSound = ArmorMaterials.DIAMOND.equipSound, ingredientFactory = { Ingredient.ofItems(LCCItems.topaz_shard) }),
    EMERALD(ArmorMaterials.IRON, equipSound = ArmorMaterials.DIAMOND.equipSound, ingredientFactory = { Ingredient.ofItems(Items.EMERALD) }),
    SAPPHIRE(ArmorMaterials.IRON, equipSound = ArmorMaterials.DIAMOND.equipSound, ingredientFactory = { Ingredient.ofItems(LCCItems.sapphire) }),
    AMETHYST(ArmorMaterials.LEATHER, equipSound = ArmorMaterials.DIAMOND.equipSound, ingredientFactory = { Ingredient.ofItems(Items.AMETHYST_SHARD) }),
    CLASSIC_LEATHER(ArmorMaterials.LEATHER, enchantability = 17, ingredientFactory = { Ingredient.ofItems(Items.LEATHER) }),
    CLASSIC_STUDDED_LEATHER(ArmorMaterials.IRON, enchantability = 19, durabilityMultiplier = 20f, toughness = 0.2f, knockbackResistance = 0.1f, equipSound = ArmorMaterials.LEATHER.equipSound, ingredientFactory = { Ingredient.ofItems(Items.IRON_INGOT) });

    private val durabilities = intArrayOf(13, 15, 16, 11).map { it.times(durabilityMultiplier).toInt() }
    private val ingredient by lazy(ingredientFactory)

    constructor(base: ArmorMaterial, durabilityMultiplier: Float = intArrayOf(13, 15, 16, 11).mapIndexed { k, v -> base.getDurability(EquipmentSlot.fromTypeIndex(EquipmentSlot.Type.ARMOR, k)).div(v.toDouble()) }.average().toFloat(), protections: IntArray = EquipmentSlot.values().filter { it.type == EquipmentSlot.Type.ARMOR }.map { base.getProtectionAmount(it) }.toIntArray(), enchantability: Int = base.enchantability, toughness: Float = base.toughness, knockbackResistance: Float = base.knockbackResistance, equipSound: SoundEvent = base.equipSound, ingredientFactory: () -> Ingredient = base::getRepairIngredient) : this(durabilityMultiplier, protections, enchantability, toughness, knockbackResistance, equipSound, ingredientFactory)

    override fun getDurability(slot: EquipmentSlot) = durabilities[slot.entitySlotId]

    override fun getProtectionAmount(slot: EquipmentSlot) = protections[slot.entitySlotId]

    override fun getEnchantability() = enchantability

    override fun getEquipSound() = equipSound

    override fun getRepairIngredient() = ingredient

    override fun getName() = name.toLowerCase()

    override fun getToughness() = toughness

    override fun getKnockbackResistance() = knockbackResistance

}