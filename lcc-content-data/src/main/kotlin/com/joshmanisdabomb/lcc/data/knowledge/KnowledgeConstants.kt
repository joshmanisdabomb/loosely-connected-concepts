package com.joshmanisdabomb.lcc.data.knowledge

import com.joshmanisdabomb.lcc.data.batches.LangBatch
import net.minecraft.text.TranslatableText

object KnowledgeConstants {

    val introduction = TranslatableText("knowledge.lcc.meta.section.introduction")
    val usage = TranslatableText("knowledge.lcc.meta.section.usage")
    val changelog = TranslatableText("knowledge.lcc.meta.section.changelog")
    val versionChangelog = TranslatableText("knowledge.lcc.meta.section.version_changelog")
    val information = TranslatableText("knowledge.lcc.meta.section.info")
    val image = TranslatableText("knowledge.lcc.meta.section.info.image")
    val stackSize = TranslatableText("knowledge.lcc.meta.section.info.stack_size")
    val durability = TranslatableText("knowledge.lcc.meta.section.info.durability")
    val miningSpeed = TranslatableText("knowledge.lcc.meta.section.info.mining_speed")
    val miningLevel = TranslatableText("knowledge.lcc.meta.section.info.mining_level")
    val enchantability = TranslatableText("knowledge.lcc.meta.section.info.enchantability")
    val attackDamage = TranslatableText("knowledge.lcc.meta.section.info.attack_damage")
    val attackSpeed = TranslatableText("knowledge.lcc.meta.section.info.attack_speed")
    val renewable = TranslatableText("knowledge.lcc.meta.section.info.renewable")
    val rarity = TranslatableText("knowledge.lcc.meta.section.info.rarity")
    val hardness = TranslatableText("knowledge.lcc.meta.section.info.hardness")
    val blastResistance = TranslatableText("knowledge.lcc.meta.section.info.blast_resistance")
    val luminance = TranslatableText("knowledge.lcc.meta.section.info.luminance")
    val friction = TranslatableText("knowledge.lcc.meta.section.info.friction")
    val flammability = TranslatableText("knowledge.lcc.meta.section.info.flammability")
    val randomTicks = TranslatableText("knowledge.lcc.meta.section.info.random_ticks")
    val mapColors = TranslatableText("knowledge.lcc.meta.section.info.map_colors")
    val recommendedTool = TranslatableText("knowledge.lcc.meta.section.info.recommended_tool")
    val requiredTool = TranslatableText("knowledge.lcc.meta.section.info.required_tool")
    val anyTool = TranslatableText("knowledge.lcc.meta.section.info.any_tool")
    val notFlammable = TranslatableText("knowledge.lcc.meta.section.info.not_flammable")
    val introduced = TranslatableText("knowledge.lcc.meta.text.introduced")
    val reintroduced = TranslatableText("knowledge.lcc.meta.text.reintroduced")

    fun injectTranslations(batch: LangBatch) {
        batch[introduction.key] = "Introduction"
        batch[usage.key] = "Usage"
        batch[changelog.key] = "Changelog"
        batch[versionChangelog.key] = "Full Changelog"
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
        batch[introduced.key] = "Introduced"
        batch[reintroduced.key] = "Reintroduced"
    }

}