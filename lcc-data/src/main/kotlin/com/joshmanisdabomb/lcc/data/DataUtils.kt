package com.joshmanisdabomb.lcc.data

import net.minecraft.data.client.TextureKey
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate

object DataUtils {

    val silk_touch = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))
    val no_silk_touch = silk_touch.invert()
    val shears = MatchToolLootCondition.builder(ItemPredicate.Builder.create().items(Items.SHEARS))
    val silk_touch_or_shears = shears.or(silk_touch)
    val not_silk_touch_or_shears = silk_touch_or_shears.invert()

    fun textureKey(name: String, parent: TextureKey?) = TextureKey.of(name, null)

}