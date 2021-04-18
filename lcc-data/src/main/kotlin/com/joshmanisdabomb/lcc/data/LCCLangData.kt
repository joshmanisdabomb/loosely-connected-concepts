package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.data.generators.LangData
import com.joshmanisdabomb.lcc.directory.LCCBlocks

object LCCLangData {

    operator fun invoke(lang: Map<String, LangData>) {
        lang["en_us"]!!["tooltip.lcc.energy"] = "Energy: %1\$s / %2\$s%3\$s"
        lang["en_us"]!!["tooltip.lcc.oxygen"] = "Oxygen: %1\$s / %2\$s"
        lang["en_us"]!!["tooltip.lcc.contained_armor.consume"] = "Your helmet is preventing you from using this item"

        lang["en_us"]!!["itemGroup.lcc.group"] = "Loosely Connected Concepts"
        lang["en_us"]!!["itemGroup.lcc.group.set.amount"] = "%s item"
        lang["en_us"]!!["itemGroup.lcc.group.set.amount.s"] = "s"
        lang["en_us"]!!["itemGroup.lcc.group.set.classic_cloth"] = "Classic Cloths"

        lang["en_us"]!!["effect.lcc.stun"] = "Stun"
        lang["en_us"]!!["effect.lcc.vulnerable"] = "Vulnerable"
        lang["en_us"]!!["effect.lcc.flammable"] = "Flammable"
        lang["en_us"]!!["effect.lcc.radiation"] = "Radiation"

        lang["en_us"]!!["biome.lcc.wasteland"] = "Wasteland"

        lang["en_us"]!!["container.lcc.spawner_table"] = "Arcane Table"
        lang["en_us"]!!["container.lcc.refiner"] = "Refiner"
        lang["en_us"]!!["container.lcc.composite_processor"] = "Composite Processor"
        lang["en_us"]!!["container.lcc.coal_generator"] = "Furnace Generator"
        lang["en_us"]!!["container.lcc.oil_generator"] = "Combustion Generator"
        lang["en_us"]!!["container.lcc.energy_bank"] = "Energy Bank"
        lang["en_us"]!!["container.lcc.atomic_bomb"] = "Atomic Bomb"
        lang["en_us"]!!["container.lcc.oxygen_extractor"] = "Oxygen Extractor"
        lang["en_us"]!!["container.lcc.kiln"] = "Kiln"
        lang["en_us"]!!["container.lcc.nuclear_generator"] = "Nuclear Generator"

        lang["en_us"]!!["container.lcc.refining.power"] = "Power: %1\$s"
        lang["en_us"]!!["container.lcc.refining.power.recipe"] = "Power: %1\$s\nConsumed: %2\$s/t"
        lang["en_us"]!!["container.lcc.refining.efficiency"] = "Efficiency: %1\$s%%"
        lang["en_us"]!!["container.lcc.refining.efficiency.recipe"] = "Efficiency: %1\$s%%\nMaximum Efficiency: %2\$s%%"
        lang["en_us"]!!["container.lcc.refining.time"] = "%1\$s/%2\$s s"
        lang["en_us"]!!["container.lcc.refining.recipe.asphalt_mixing"] = "Bituminous Mixing"
        lang["en_us"]!!["container.lcc.refining.recipe.pozzolanic_mixing"] = "Pozzolanic Mixing"
        lang["en_us"]!!["container.lcc.refining.recipe.uranium"] = "Centrifugal Separation"
        lang["en_us"]!!["container.lcc.refining.recipe.treating"] = "Thermal Disinfection"
        lang["en_us"]!!["container.lcc.refining.recipe.arc"] = "Electric Arc Smelting"
        lang["en_us"]!!["container.lcc.refining.recipe.dry"] = "Heated Drying"
        lang["en_us"]!!["container.lcc.refining.recipe.ducrete_mixing"] = "Ducrete Mixing"
        lang["en_us"]!!["container.lcc.refining.recipe.pellet_compression"] = "Pellet Compression"
        lang["en_us"]!!["container.lcc.refining.recipe.salt"] = "Salt Purification"

        lang["en_us"]!!["container.lcc.generator.burn"] = "Remaining Time: %1\$s/%2\$s s"
        lang["en_us"]!!["container.lcc.generator.output"] = "Steam Produced: %1\$s/t\nBase Steam from Fuel: %2\$s/t\nSteam Multiplier from Water: %3\$s%%"
        lang["en_us"]!!["container.lcc.nuclear_generator.power"] = "Power: %1\$s"
        lang["en_us"]!!["container.lcc.nuclear_generator.output"] = "Total Steam Produced: %1\$s/t\nBase Steam Produced: %2\$s/t\nMaximum Safe Output: %3\$s/t\nMaximum Fuel Approaches: %4\$s/t\nSteam Multiplier from Water: %5\$s%%"
        lang["en_us"]!!["container.lcc.nuclear_generator.coolant"] = "Coolant Value: %1\$s units\nDepletion Rate: %2\$s units/t"
        lang["en_us"]!!["container.lcc.nuclear_generator.fuel"] = "Fuel Value: %1\$s units\nFuel Value per Item: %2\$s units\nDepletion Rate: %3\$s units/t"
        lang["en_us"]!!["container.lcc.nuclear_generator.chance"] = "Meltdown Chance: %1\$s%%"
        lang["en_us"]!!["container.lcc.nuclear_generator.meltdown"] = "Meltdown in %1\$ss!!!"

        lang["en_us"]!!["container.lcc.battery.power"] = "Power: %1\$s"

        lang["en_us"]!!["container.lcc.oxygen_extractor.power"] = "Power: %1\$s"
        lang["en_us"]!!["container.lcc.oxygen_extractor.oxygen"] = "Oxygen Outflow: %1\$s/t\nHold SHIFT for details"
        lang["en_us"]!!["container.lcc.oxygen_extractor.oxygen.advanced"] = "Total Oxygen Outflow: %1\$s/t\n • From Top: %2\$s/t\n • From North: %3\$s/t\n • From East: %4\$s/t\n • From South: %5\$s/t\n • From West: %6\$s/t\n • Dimension Modifier: %7\$s%%"

        lang["en_us"]!!["gui.lcc.atomic_bomb.detonate"] = "Detonate"
        lang["en_us"]!!["gui.lcc.atomic_bomb.detonate.power"] = "Power: %1\$s"
        lang["en_us"]!!["gui.lcc.nuclear_generator.activate"] = "Power On"

        lang["en_us"]!!["death.attack.lcc.gauntlet_punch"] = "%1\$s was obliterated by %2\$s using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch.item"] = "%1\$s was obliterated by %2\$s using Punch of %3\$s"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch_wall"] = "%1\$s was launched into the wall using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch_wall.player"] = "%1\$s was launched into the wall by %2\$s using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_uppercut"] = "%1\$s was struck by %2\$s using Uppercut"
        lang["en_us"]!!["death.attack.lcc.gauntlet_uppercut.item"] = "%1\$s was struck by %2\$s using Uppercut of %3\$s"

        lang["en_us"]!!["death.attack.lcc.heated"] = "%1\$s was steamed like a vegetable"
        lang["en_us"]!!["death.attack.lcc.heated.player"] = "%1\$s stepped on a generator whilst trying to escape %2\$s"
        lang["en_us"]!!["death.attack.lcc.boiled"] = "%1\$s was boiled alive"
        lang["en_us"]!!["death.attack.lcc.boiled.player"] = "%1\$s ended up boiled alive whilst trying to escape %2\$s"

        lang["en_us"]!!["death.attack.lcc.nuke"] = "%1\$s was nuked"
        lang["en_us"]!!["death.attack.lcc.nuke.player"] = "%1\$s was nuked by %2\$s"
        lang["en_us"]!!["death.attack.lcc.radiation"] = "%1\$s was exposed to ionizing radiation"
        lang["en_us"]!!["death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionizing radiation whilst trying to escape %2\$s"
        lang["en_gb"]!!["death.attack.lcc.radiation"] = "%1\$s was exposed to ionising radiation"
        lang["en_gb"]!!["death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionising radiation whilst trying to escape %2\$s"
        lang["en_us"]!!["death.attack.lcc.hazmat_anoxia"] = "%1\$s couldn't get their helmet off"
        lang["en_us"]!!["death.attack.lcc.hazmat_anoxia.player"] = "%1\$s couldn't get their helmet off whilst trying to escape %2\$s"
        lang["en_us"]!!["death.attack.salt"] = "%1\$s was lightly salted by %2\$s"
        lang["en_us"]!!["death.attack.salt.item"] = "%1\$s was lightly salted by %2\$s using %3\$s"

        lang["en_us"]!!["subtitles.lcc.block.soaking_soul_sand.jump"] = "Soaking Soul Sand squelches"
        lang["en_us"]!!["subtitles.lcc.block.bounce_pad.jump"] = "Bounce Pad protracts"
        lang["en_us"]!!["subtitles.lcc.block.bounce_pad.set"] = "Bounce Pad set"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.ambient"] = "Pocket Zombie Pigman grunts angrily"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.hurt"] = "Pocket Zombie Pigman hurts"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.death"] = "Pocket Zombie Pigman dies"
        lang["en_us"]!!["subtitles.lcc.block.classic_crying_obsidian.set_spawn"] = "Classic Crying Obsidian sets spawn"
        lang["en_us"]!!["subtitles.lcc.entity.atomic_bomb.fuse"] = "Atomic Bomb ticks"
        lang["en_us"]!!["subtitles.lcc.entity.atomic_bomb.defuse"] = "Atomic Bomb defused"
        lang["en_us"]!!["subtitles.lcc.entity.nuclear_explosion.explode"] = "Nuclear explosion"
        lang["en_us"]!!["subtitles.lcc.block.alarm.bell"] = "Alarm rings"
        lang["en_us"]!!["subtitles.lcc.block.alarm.nuclear_siren"] = "Alarm siren blares"

        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".active"] = "Active!"
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".incorrect"] = "Not the correct pattern!"
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".players"] = "All players need to be close to the reactor."
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".y"] = "The reactor must be placed between y=%1\$s and y=%2\$s"

        lang["en_us"]!![LCCBlocks.radar.translationKey.toString() + ".range"] = "Range is %1\$s blocks"

        lang["en_us"]!!["commands.lcc.radiation.add.success.single"] = "Added %s to the radiation sickness of %s"
        lang["en_us"]!!["commands.lcc.radiation.add.success.multiple"] = "Added %s to the radiation sickness of %s players"
        lang["en_us"]!!["commands.lcc.radiation.set.success.single"] = "Set the radiation sickness of %s to %s"
        lang["en_us"]!!["commands.lcc.radiation.set.success.multiple"] = "Set the radiation sickness of %s players to %s"
        lang["en_us"]!!["commands.lcc.radiation.query"] = "%s has a radiation sickness value of %s"
        lang["en_us"]!!["commands.lcc.radiation.failed.living.single"] = "%s must be a living entity to have radiation sickness"
        lang["en_us"]!!["commands.lcc.radiation.failed.living.multiple"] = "No entities given were living and cannot receive radiation sickness"

        lang["en_us"]!!["commands.lcc.nuclearwinter.add.success"] = "Added %s to the nuclear winter level of %s"
        lang["en_us"]!!["commands.lcc.nuclearwinter.set.success"] = "Set the nuclear winter level of %s to %s"
        lang["en_us"]!!["commands.lcc.nuclearwinter.query"] = "%s has a nuclear winter level of %s"
    }

}
