package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.text.Text
import net.minecraft.text.TranslatableTextContent

object KnowledgeConstants {

    val me = "joshmanisdabomb"

    val introduction = Text.translatable("knowledge.lcc.meta.section.introduction")
    val usage = Text.translatable("knowledge.lcc.meta.section.usage")
    val changelog = Text.translatable("knowledge.lcc.meta.section.changelog")
    val versionChangelog = Text.translatable("knowledge.lcc.meta.section.version_changelog")
    val recipes = Text.translatable("knowledge.lcc.meta.section.recipes")
    val usages = Text.translatable("knowledge.lcc.meta.section.usages")
    val despawning = Text.translatable("knowledge.lcc.meta.section.despawning")
    val repairing = Text.translatable("knowledge.lcc.meta.section.repairing")
    val coloring = Text.translatable("knowledge.lcc.meta.section.coloring")
    val combat = Text.translatable("knowledge.lcc.meta.section.combat")
    val mining = Text.translatable("knowledge.lcc.meta.section.mining")
    val obtaining = Text.translatable("knowledge.lcc.meta.section.obtaining")

    val information = Text.translatable("knowledge.lcc.meta.section.info")
    val image = Text.translatable("knowledge.lcc.meta.section.info.image")
    val stackSize = Text.translatable("knowledge.lcc.meta.section.info.stack_size")
    val durability = Text.translatable("knowledge.lcc.meta.section.info.durability")
    val miningSpeed = Text.translatable("knowledge.lcc.meta.section.info.mining_speed")
    val miningLevel = Text.translatable("knowledge.lcc.meta.section.info.mining_level")
    val enchantability = Text.translatable("knowledge.lcc.meta.section.info.enchantability")
    val attackDamage = Text.translatable("knowledge.lcc.meta.section.info.attack_damage")
    val attackSpeed = Text.translatable("knowledge.lcc.meta.section.info.attack_speed")
    val renewable = Text.translatable("knowledge.lcc.meta.section.info.renewable")
    val rarity = Text.translatable("knowledge.lcc.meta.section.info.rarity")
    val hardness = Text.translatable("knowledge.lcc.meta.section.info.hardness")
    val blastResistance = Text.translatable("knowledge.lcc.meta.section.info.blast_resistance")
    val luminance = Text.translatable("knowledge.lcc.meta.section.info.luminance")
    val friction = Text.translatable("knowledge.lcc.meta.section.info.friction")
    val flammability = Text.translatable("knowledge.lcc.meta.section.info.flammability")
    val randomTicks = Text.translatable("knowledge.lcc.meta.section.info.random_ticks")
    val mapColors = Text.translatable("knowledge.lcc.meta.section.info.map_colors")
    val recommendedTool = Text.translatable("knowledge.lcc.meta.section.info.recommended_tool")
    val requiredTool = Text.translatable("knowledge.lcc.meta.section.info.required_tool")
    val anyTool = Text.translatable("knowledge.lcc.meta.section.info.any_tool")
    val notFlammable = Text.translatable("knowledge.lcc.meta.section.info.not_flammable")

    val introduced = Text.translatable("knowledge.lcc.meta.text.introduced")
    val reintroduced = Text.translatable("knowledge.lcc.meta.text.reintroduced")

    val color = Text.translatable("knowledge.lcc.meta.text.color")
    val colors = Text.translatable("knowledge.lcc.meta.text.colors")
    val armor = Text.translatable("knowledge.lcc.meta.text.armor")
    val colorCode = Text.translatable("knowledge.lcc.meta.text.color_code")
    val colored = Text.translatable("knowledge.lcc.meta.text.colored")

    val mud = Text.translatable("block.aimagg.mud")

    private val Text.key get() = (this.content as TranslatableTextContent).key

    fun injectTranslations(batch: LangBatch) {
        batch[introduction.key] = "Introduction"
        batch[usage.key] = "Usage"
        batch[changelog.key] = "Changelog"
        batch[versionChangelog.key] = "Full Changelog"
        batch[recipes.key] = "Crafting Recipes"
        batch[usages.key] = "Crafting Usages"
        batch[despawning.key] = "Despawning"
        batch[repairing.key] = "Repairing"
        batch[coloring.key] = "Coloring"
        batch["en_gb", coloring.key] = "Colouring"
        batch[combat.key] = "Combat"
        batch[mining.key] = "Mining"
        batch[obtaining.key] = "Obtaining"
        batch[information.key] = "Information"
        batch[image.key] = "Image"
        batch[stackSize.key] = "Stack Size"
        batch[durability.key] = "Durability"
        batch[miningSpeed.key] = "Mining Speed"
        batch[miningLevel.key] = "Mining Level"
        batch[enchantability.key] = "Enchantability"
        batch[attackDamage.key] = "Attack Damage"
        batch[attackSpeed.key] = "Attack Speed"
        batch[renewable.key] = "Renewable"
        batch[rarity.key] = "Rarity"
        batch[hardness.key] = "Hardness"
        batch[blastResistance.key] = "Blast Resistance"
        batch[luminance.key] = "Luminance"
        batch[friction.key] = "Friction"
        batch[flammability.key] = "Flammability"
        batch[randomTicks.key] = "Random Ticks"
        batch[mapColors.key] = "Map Colors"
        batch["en_gb", mapColors.key] = "Map Colours"
        batch[recommendedTool.key] = "Recommended Tool"
        batch[requiredTool.key] = "Required Tool"
        batch[anyTool.key] = "Any"
        batch[notFlammable.key] = "No"
        batch[introduced.key] = "Introduced."
        batch[reintroduced.key] = "Reintroduced."
        batch[color.key] = "color"
        batch["en_gb", color.key] = "colour"
        batch[colors.key] = "colors"
        batch["en_gb", colors.key] = "colours"
        batch[armor.key] = "armor"
        batch["en_gb", armor.key] = "armour"
        batch[colorCode.key] = "Color Code"
        batch["en_gb", colorCode.key] = "Colour Code"
        batch[colored.key] = "colored"
        batch["en_gb", colored.key] = "coloured"
        batch[mud.key] = "Mud"
    }

}