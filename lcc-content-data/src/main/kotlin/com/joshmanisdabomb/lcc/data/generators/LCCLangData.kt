package com.joshmanisdabomb.lcc.data.generators

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.data.batches.LangBatch.Companion.defaultLocale
import com.joshmanisdabomb.lcc.directory.LCCBlocks

object LCCLangData {

    val lang get() = LCCData.lang

    fun init() {
        lang[defaultLocale, "tooltip.lcc.energy"] = "Energy: %1\$s / %2\$s%3\$s"
        lang[defaultLocale, "tooltip.lcc.oxygen"] = "Oxygen: %1\$s / %2\$s"
        lang[defaultLocale, "tooltip.lcc.contained_armor.consume"] = "Your helmet is preventing you from using this item"

        lang[defaultLocale, "itemGroup.lcc.group"] = "Loosely Connected Concepts"
        lang[defaultLocale, "itemGroup.lcc.group.set.amount"] = "%s item"
        lang[defaultLocale, "itemGroup.lcc.group.set.amount.s"] = "s"
        lang[defaultLocale, "itemGroup.lcc.group.set.classic_cloth"] = "Classic Cloths"

        lang[defaultLocale, "effect.lcc.stun"] = "Stun"
        lang[defaultLocale, "effect.lcc.vulnerable"] = "Vulnerable"
        lang[defaultLocale, "effect.lcc.flammable"] = "Flammable"
        lang[defaultLocale, "effect.lcc.radiation"] = "Radiation"

        lang[defaultLocale, "biome.lcc.wasteland"] = "Wasteland"

        lang[defaultLocale, "container.lcc.spawner_table"] = "Arcane Table"
        lang[defaultLocale, "container.lcc.refiner"] = "Refiner"
        lang[defaultLocale, "container.lcc.composite_processor"] = "Composite Processor"
        lang[defaultLocale, "container.lcc.coal_generator"] = "Furnace Generator"
        lang[defaultLocale, "container.lcc.oil_generator"] = "Combustion Generator"
        lang[defaultLocale, "container.lcc.energy_bank"] = "Energy Bank"
        lang[defaultLocale, "container.lcc.atomic_bomb"] = "Atomic Bomb"
        lang[defaultLocale, "container.lcc.oxygen_extractor"] = "Oxygen Extractor"
        lang[defaultLocale, "container.lcc.kiln"] = "Kiln"
        lang[defaultLocale, "container.lcc.nuclear_generator"] = "Nuclear Generator"

        lang[defaultLocale, "container.lcc.refining.power"] = "Power: %1\$s"
        lang[defaultLocale, "container.lcc.refining.power.recipe"] = "Power: %1\$s\nConsumed: %2\$s/t"
        lang[defaultLocale, "container.lcc.refining.efficiency"] = "Efficiency: %1\$s%%"
        lang[defaultLocale, "container.lcc.refining.efficiency.recipe"] = "Efficiency: %1\$s%%\nMaximum Efficiency: %2\$s%%"
        lang[defaultLocale, "container.lcc.refining.time"] = "%1\$s/%2\$s s"
        lang[defaultLocale, "container.lcc.refining.recipe.asphalt_mixing"] = "Bituminous Mixing"
        lang[defaultLocale, "container.lcc.refining.recipe.pozzolanic_mixing"] = "Pozzolanic Mixing"
        lang[defaultLocale, "container.lcc.refining.recipe.uranium"] = "Centrifugal Separation"
        lang[defaultLocale, "container.lcc.refining.recipe.treating"] = "Thermal Disinfection"
        lang[defaultLocale, "container.lcc.refining.recipe.arc"] = "Electric Arc Smelting"
        lang[defaultLocale, "container.lcc.refining.recipe.dry"] = "Heated Drying"
        lang[defaultLocale, "container.lcc.refining.recipe.ducrete_mixing"] = "Ducrete Mixing"
        lang[defaultLocale, "container.lcc.refining.recipe.pellet_compression"] = "Pellet Compression"
        lang[defaultLocale, "container.lcc.refining.recipe.salt"] = "Salt Purification"
        lang[defaultLocale, "container.lcc.refining.recipe.paste_mixing"] = "Reagent Binding"
        lang[defaultLocale, "container.lcc.refining.recipe.fractional_distillation"] = "Fractional Distillation"
        lang[defaultLocale, "container.lcc.refining.recipe.oil_cracking"] = "Oil Cracking"
        lang[defaultLocale, "container.lcc.refining.recipe.polymerization"] = "Polymerization"

        lang[defaultLocale, "container.lcc.generator.burn"] = "Remaining Time: %1\$s/%2\$s s"
        lang[defaultLocale, "container.lcc.generator.output"] = "Steam Produced: %1\$s/t\nBase Steam from Fuel: %2\$s/t\nSteam Multiplier from Water: %3\$s%%"
        lang[defaultLocale, "container.lcc.nuclear_generator.power"] = "Power: %1\$s"
        lang[defaultLocale, "container.lcc.nuclear_generator.output"] = "Total Steam Produced: %1\$s/t\nBase Steam Produced: %2\$s/t\nMaximum Safe Output: %3\$s\nMaximum Fuel Approaches: %4\$s/t\nSteam Multiplier from Water: %5\$s%%"
        lang[defaultLocale, "container.lcc.nuclear_generator.coolant"] = "Coolant Value: %1\$s units\nDepletion Rate: %2\$s units/t"
        lang[defaultLocale, "container.lcc.nuclear_generator.fuel"] = "Fuel Value: %1\$s units\nFuel Value per Item: %2\$s units\nDepletion Rate: %3\$s units/t"
        lang[defaultLocale, "container.lcc.nuclear_generator.chance"] = "Meltdown Chance: %1\$s%%/t"
        lang[defaultLocale, "container.lcc.nuclear_generator.waste"] = "Progress Towards Next Waste: %1\$s%%\nGeneration Rate: %2\$s%%/t\nMaximum Safe Output: %3\$s\nSafe Output Change: %4\$s/t"
        lang[defaultLocale, "container.lcc.nuclear_generator.meltdown"] = "Meltdown in %1\$ss!!!"

        lang[defaultLocale, "container.lcc.battery.power"] = "Power: %1\$s"

        lang[defaultLocale, "container.lcc.oxygen_extractor.power"] = "Power: %1\$s"
        lang[defaultLocale, "container.lcc.oxygen_extractor.oxygen"] = "Oxygen Outflow: %1\$s/t\nHold SHIFT for details"
        lang[defaultLocale, "container.lcc.oxygen_extractor.oxygen.advanced"] = "Total Oxygen Outflow: %1\$s/t\n • From Top: %2\$s/t\n • From North: %3\$s/t\n • From East: %4\$s/t\n • From South: %5\$s/t\n • From West: %6\$s/t\n • Dimension Modifier: %7\$s%%"

        lang[defaultLocale, "gui.lcc.atomic_bomb.detonate"] = "Detonate"
        lang[defaultLocale, "gui.lcc.atomic_bomb.detonate.power"] = "Power: %1\$s"
        lang[defaultLocale, "gui.lcc.nuclear_generator.activate"] = "Power On"

        lang[defaultLocale, "death.attack.lcc.gauntlet_punch"] = "%1\$s was obliterated by %2\$s using Punch"
        lang[defaultLocale, "death.attack.lcc.gauntlet_punch.item"] = "%1\$s was obliterated by %2\$s using Punch of %3\$s"
        lang[defaultLocale, "death.attack.lcc.gauntlet_punch_wall"] = "%1\$s was launched into the wall using Punch"
        lang[defaultLocale, "death.attack.lcc.gauntlet_punch_wall.player"] = "%1\$s was launched into the wall by %2\$s using Punch"
        lang[defaultLocale, "death.attack.lcc.gauntlet_uppercut"] = "%1\$s was struck by %2\$s using Uppercut"
        lang[defaultLocale, "death.attack.lcc.gauntlet_uppercut.item"] = "%1\$s was struck by %2\$s using Uppercut of %3\$s"

        lang[defaultLocale, "death.attack.lcc.heated"] = "%1\$s was steamed like a vegetable"
        lang[defaultLocale, "death.attack.lcc.heated.player"] = "%1\$s stepped on a generator whilst trying to escape %2\$s"
        lang[defaultLocale, "death.attack.lcc.boiled"] = "%1\$s was boiled alive"
        lang[defaultLocale, "death.attack.lcc.boiled.player"] = "%1\$s ended up boiled alive whilst trying to escape %2\$s"

        lang[defaultLocale, "death.attack.lcc.nuke"] = "%1\$s was nuked"
        lang[defaultLocale, "death.attack.lcc.nuke.player"] = "%1\$s was nuked by %2\$s"
        lang[defaultLocale, "death.attack.lcc.radiation"] = "%1\$s was exposed to ionizing radiation"
        lang[defaultLocale, "death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionizing radiation whilst trying to escape %2\$s"
        lang["en_gb", "death.attack.lcc.radiation"] = "%1\$s was exposed to ionising radiation"
        lang["en_gb", "death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionising radiation whilst trying to escape %2\$s"
        lang[defaultLocale, "death.attack.lcc.hazmat_anoxia"] = "%1\$s couldn't get their helmet off"
        lang[defaultLocale, "death.attack.lcc.hazmat_anoxia.player"] = "%1\$s couldn't get their helmet off whilst trying to escape %2\$s"
        lang[defaultLocale, "death.attack.lcc.salt"] = "%1\$s was lightly salted by %2\$s"
        lang[defaultLocale, "death.attack.lcc.salt.item"] = "%1\$s was lightly salted by %2\$s using %3\$s"
        lang[defaultLocale, "death.attack.lcc.spikes"] = "%1\$s was skewered"
        lang[defaultLocale, "death.attack.lcc.spikes.player"] = "%1\$s was skewered like a kebab whilst trying to escape %2\$s"

        lang[defaultLocale, "subtitles.lcc.block.soaking_soul_sand.jump"] = "Soaking Soul Sand squelches"
        lang[defaultLocale, "subtitles.lcc.block.bounce_pad.jump"] = "Bounce Pad protracts"
        lang[defaultLocale, "subtitles.lcc.block.bounce_pad.set"] = "Bounce Pad set"
        lang[defaultLocale, "subtitles.lcc.entity.pocket_zombie_pigman.ambient"] = "Pocket Zombie Pigman grunts angrily"
        lang[defaultLocale, "subtitles.lcc.entity.pocket_zombie_pigman.hurt"] = "Pocket Zombie Pigman hurts"
        lang[defaultLocale, "subtitles.lcc.entity.pocket_zombie_pigman.death"] = "Pocket Zombie Pigman dies"
        lang[defaultLocale, "subtitles.lcc.block.classic_crying_obsidian.set_spawn"] = "Classic Crying Obsidian sets spawn"
        lang[defaultLocale, "subtitles.lcc.entity.atomic_bomb.fuse"] = "Atomic Bomb ticks"
        lang[defaultLocale, "subtitles.lcc.entity.atomic_bomb.defuse"] = "Atomic Bomb defused"
        lang[defaultLocale, "subtitles.lcc.entity.nuclear_explosion.explode"] = "Nuclear explosion"
        lang[defaultLocale, "subtitles.lcc.block.alarm.bell"] = "Alarm rings"
        lang[defaultLocale, "subtitles.lcc.block.alarm.nuclear_siren"] = "Alarm siren blares"
        lang[defaultLocale, "subtitles.lcc.item.detector.click"] = "Radiation Detector clicks"
        lang[defaultLocale, "subtitles.lcc.entity.salt.throw"] = "Salt thrown"
        lang[defaultLocale, "subtitles.lcc.block.spikes.hurt"] = "Spikes damage"
        lang[defaultLocale, "subtitles.lcc.block.improvised_explosive.beep"] = "Improvised Explosive beeps"
        lang[defaultLocale, "subtitles.lcc.block.improvised_explosive.constant"] = "Improvised Explosive beeps rapidly"
        lang[defaultLocale, "subtitles.lcc.block.improvised_explosive.triggered"] = "Improvised Explosive triggered"
        lang[defaultLocale, "subtitles.lcc.block.improvised_explosive.defuse"] = "Improvised Explosive defused"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.ambient"] = "Consumer snarls"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.hurt"] = "Consumer hurts"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.death"] = "Consumer dies"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.attack"] = "Consumer chomps"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.cqc"] = "Consumer snarls angrily"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.tongue.shoot"] = "Consumer extends tongue"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.tongue.loop"] = "Consumer licks"
        lang[defaultLocale, "subtitles.lcc.entity.consumer.tongue.attach"] = "Consumer tongues something"

        lang[defaultLocale, LCCBlocks.nether_reactor.translationKey.toString() + ".active"] = "Active!"
        lang[defaultLocale, LCCBlocks.nether_reactor.translationKey.toString() + ".incorrect"] = "Not the correct pattern!"
        lang[defaultLocale, LCCBlocks.nether_reactor.translationKey.toString() + ".players"] = "All players need to be close to the reactor."
        lang[defaultLocale, LCCBlocks.nether_reactor.translationKey.toString() + ".y"] = "The reactor must be placed between y=%1\$s and y=%2\$s"

        lang[defaultLocale, LCCBlocks.radar.translationKey.toString() + ".range"] = "Range is %1\$s blocks"

        lang[defaultLocale, LCCBlocks.sapphire_altar.translationKey.toString() + ".minesweeper.malformed"] = "The sapphire altar has been tampered with."

        lang[defaultLocale, LCCBlocks.wasteland_obelisk.translationKey.toString() + ".cooldown"] = "The obelisks asks you to be patient."

        lang[defaultLocale, "commands.lcc.radiation.add.success.single"] = "Added %s to the radiation sickness of %s"
        lang[defaultLocale, "commands.lcc.radiation.add.success.multiple"] = "Added %s to the radiation sickness of %s players"
        lang[defaultLocale, "commands.lcc.radiation.set.success.single"] = "Set the radiation sickness of %s to %s"
        lang[defaultLocale, "commands.lcc.radiation.set.success.multiple"] = "Set the radiation sickness of %s players to %s"
        lang[defaultLocale, "commands.lcc.radiation.query"] = "%s has a radiation sickness value of %s"
        lang[defaultLocale, "commands.lcc.radiation.failed.living.single"] = "%s must be a living entity to have radiation sickness"
        lang[defaultLocale, "commands.lcc.radiation.failed.living.multiple"] = "No entities given were living and cannot receive radiation sickness"

        lang[defaultLocale, "commands.lcc.nuclearwinter.add.success"] = "Added %s to the nuclear winter level of %s"
        lang[defaultLocale, "commands.lcc.nuclearwinter.set.success"] = "Set the nuclear winter level of %s to %s"
        lang[defaultLocale, "commands.lcc.nuclearwinter.query"] = "%s has a nuclear winter level of %s"
    }

}
