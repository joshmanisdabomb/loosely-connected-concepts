package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.enchantment.InfestedEnchantment
import net.minecraft.enchantment.Enchantment
import net.minecraft.enchantment.EnchantmentTarget
import net.minecraft.entity.EquipmentSlot
import net.minecraft.util.registry.Registry

object LCCEnchants : BasicDirectory<Enchantment, Unit>(), RegistryDirectory<Enchantment, Unit, Unit> {

    override val registry by lazy { Registry.ENCHANTMENT }

    override fun regId(name: String) = LCC.id(name)

    val infested by entry(::initialiser) { InfestedEnchantment(Enchantment.Rarity.RARE, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND) }

    override fun defaultProperties(name: String) = Unit

}