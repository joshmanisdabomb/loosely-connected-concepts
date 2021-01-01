package com.joshmanisdabomb.lcc.data

import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import net.minecraft.enchantment.Enchantments
import net.minecraft.item.Items
import net.minecraft.loot.condition.MatchToolLootCondition
import net.minecraft.predicate.NumberRange
import net.minecraft.predicate.item.EnchantmentPredicate
import net.minecraft.predicate.item.ItemPredicate
import org.apache.logging.log4j.LogManager

object DataUtils {

    internal val parser = JsonParser()

    internal val gson = GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create()
    internal val logger = LogManager.getLogger()

    internal val silk_touch = MatchToolLootCondition.builder(ItemPredicate.Builder.create().enchantment(EnchantmentPredicate(Enchantments.SILK_TOUCH, NumberRange.IntRange.atLeast(1))))
    internal val no_silk_touch = silk_touch.invert()
    internal val shears = MatchToolLootCondition.builder(ItemPredicate.Builder.create().item(Items.SHEARS))
    internal val silk_touch_or_shears = shears.or(silk_touch)
    internal val not_silk_touch_or_shears = silk_touch_or_shears.invert()

}