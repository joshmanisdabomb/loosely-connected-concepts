package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.directory.tags.LCCItemTags
import com.joshmanisdabomb.lcc.extensions.forEachInt
import com.joshmanisdabomb.lcc.recipe.imbuing.ImbuingRecipe
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
import net.fabricmc.fabric.api.event.Event
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.entity.effect.StatusEffectUtil
import net.minecraft.text.Text
import net.minecraft.util.Formatting
import net.minecraft.util.Identifier

object LCCEventsClient : BasicDirectory<Any, Unit>() {

    val imbueTooltip by entry(ItemTooltipCallback.EVENT.initialiser) { ItemTooltipCallback { stack, context, lines ->
        val world = MinecraftClient.getInstance().world ?: return@ItemTooltipCallback
        if (!stack.isIn(LCCItemTags.imbuable)) return@ItemTooltipCallback
        stack.getSubNbt("lcc-imbue")?.forEachInt { k, v ->
            val recipe = world.recipeManager.get(Identifier(k)).orElse(null) as? ImbuingRecipe ?: return@forEachInt
            lines.add(Text.translatable("tooltip.lcc.imbue.${k.replace(":", ".")}", v, recipe.getMaxHits(stack)).formatted(Formatting.DARK_GREEN))
            if (Screen.hasShiftDown()) {
                for (effect in recipe.getEffects()) {
                    var text = effect.effectType.name
                    if (effect.amplifier > 0) text = Text.translatable("potion.withAmplifier", text, Text.translatable("potion.potency.${effect.amplifier}"))
                    val duration = StatusEffectUtil.durationToString(effect, 1.0f)
                    lines.add(Text.literal(" ").append(Text.translatable("potion.withDuration", text, duration)).formatted(Formatting.GREEN))
                }
            }
        }
    } }

    val <E> Event<E>.initialiser get() = { i: E, c: DirectoryContext<Unit>, p: Any -> i.also { this.register(i) } }

    override fun defaultProperties(name: String) = Unit

}