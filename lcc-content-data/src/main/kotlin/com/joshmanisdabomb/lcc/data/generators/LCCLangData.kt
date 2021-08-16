package com.joshmanisdabomb.lcc.data.generators

import com.joshmanisdabomb.lcc.data.LCCData
import com.joshmanisdabomb.lcc.directory.LCCBlocks

object LCCLangData {

    val lang get() = LCCData.lang

    fun init() {
        val en_us = lang["en_us"]!!
        en_us["tooltip.lcc.energy"] = "Energy: %1\$s / %2\$s%3\$s"
        en_us["tooltip.lcc.oxygen"] = "Oxygen: %1\$s / %2\$s"
        en_us["tooltip.lcc.contained_armor.consume"] = "Your helmet is preventing you from using this item"

        en_us["itemGroup.lcc.group"] = "Loosely Connected Concepts"
        en_us["itemGroup.lcc.group.set.amount"] = "%s item"
        en_us["itemGroup.lcc.group.set.amount.s"] = "s"
        en_us["itemGroup.lcc.group.set.classic_cloth"] = "Classic Cloths"

        en_us["effect.lcc.stun"] = "Stun"
        en_us["effect.lcc.vulnerable"] = "Vulnerable"
        en_us["effect.lcc.flammable"] = "Flammable"
        en_us["effect.lcc.radiation"] = "Radiation"

        en_us["biome.lcc.wasteland"] = "Wasteland"

        en_us["container.lcc.spawner_table"] = "Arcane Table"
        en_us["container.lcc.refiner"] = "Refiner"
        en_us["container.lcc.composite_processor"] = "Composite Processor"
        en_us["container.lcc.coal_generator"] = "Furnace Generator"
        en_us["container.lcc.oil_generator"] = "Combustion Generator"
        en_us["container.lcc.energy_bank"] = "Energy Bank"
        en_us["container.lcc.atomic_bomb"] = "Atomic Bomb"
        en_us["container.lcc.oxygen_extractor"] = "Oxygen Extractor"
        en_us["container.lcc.kiln"] = "Kiln"
        en_us["container.lcc.nuclear_generator"] = "Nuclear Generator"

        en_us["container.lcc.refining.power"] = "Power: %1\$s"
        en_us["container.lcc.refining.power.recipe"] = "Power: %1\$s\nConsumed: %2\$s/t"
        en_us["container.lcc.refining.efficiency"] = "Efficiency: %1\$s%%"
        en_us["container.lcc.refining.efficiency.recipe"] = "Efficiency: %1\$s%%\nMaximum Efficiency: %2\$s%%"
        en_us["container.lcc.refining.time"] = "%1\$s/%2\$s s"
        en_us["container.lcc.refining.recipe.asphalt_mixing"] = "Bituminous Mixing"
        en_us["container.lcc.refining.recipe.pozzolanic_mixing"] = "Pozzolanic Mixing"
        en_us["container.lcc.refining.recipe.uranium"] = "Centrifugal Separation"
        en_us["container.lcc.refining.recipe.treating"] = "Thermal Disinfection"
        en_us["container.lcc.refining.recipe.arc"] = "Electric Arc Smelting"
        en_us["container.lcc.refining.recipe.dry"] = "Heated Drying"
        en_us["container.lcc.refining.recipe.ducrete_mixing"] = "Ducrete Mixing"
        en_us["container.lcc.refining.recipe.pellet_compression"] = "Pellet Compression"
        en_us["container.lcc.refining.recipe.salt"] = "Salt Purification"
        en_us["container.lcc.refining.recipe.paste_mixing"] = "Reagent Binding"
        en_us["container.lcc.refining.recipe.fractional_distillation"] = "Fractional Distillation"
        en_us["container.lcc.refining.recipe.oil_cracking"] = "Oil Cracking"
        en_us["container.lcc.refining.recipe.polymerization"] = "Polymerization"

        en_us["container.lcc.generator.burn"] = "Remaining Time: %1\$s/%2\$s s"
        en_us["container.lcc.generator.output"] = "Steam Produced: %1\$s/t\nBase Steam from Fuel: %2\$s/t\nSteam Multiplier from Water: %3\$s%%"
        en_us["container.lcc.nuclear_generator.power"] = "Power: %1\$s"
        en_us["container.lcc.nuclear_generator.output"] = "Total Steam Produced: %1\$s/t\nBase Steam Produced: %2\$s/t\nMaximum Safe Output: %3\$s\nMaximum Fuel Approaches: %4\$s/t\nSteam Multiplier from Water: %5\$s%%"
        en_us["container.lcc.nuclear_generator.coolant"] = "Coolant Value: %1\$s units\nDepletion Rate: %2\$s units/t"
        en_us["container.lcc.nuclear_generator.fuel"] = "Fuel Value: %1\$s units\nFuel Value per Item: %2\$s units\nDepletion Rate: %3\$s units/t"
        en_us["container.lcc.nuclear_generator.chance"] = "Meltdown Chance: %1\$s%%/t"
        en_us["container.lcc.nuclear_generator.waste"] = "Progress Towards Next Waste: %1\$s%%\nGeneration Rate: %2\$s%%/t\nMaximum Safe Output: %3\$s\nSafe Output Change: %4\$s/t"
        en_us["container.lcc.nuclear_generator.meltdown"] = "Meltdown in %1\$ss!!!"

        en_us["container.lcc.battery.power"] = "Power: %1\$s"

        en_us["container.lcc.oxygen_extractor.power"] = "Power: %1\$s"
        en_us["container.lcc.oxygen_extractor.oxygen"] = "Oxygen Outflow: %1\$s/t\nHold SHIFT for details"
        en_us["container.lcc.oxygen_extractor.oxygen.advanced"] = "Total Oxygen Outflow: %1\$s/t\n • From Top: %2\$s/t\n • From North: %3\$s/t\n • From East: %4\$s/t\n • From South: %5\$s/t\n • From West: %6\$s/t\n • Dimension Modifier: %7\$s%%"

        en_us["gui.lcc.atomic_bomb.detonate"] = "Detonate"
        en_us["gui.lcc.atomic_bomb.detonate.power"] = "Power: %1\$s"
        en_us["gui.lcc.nuclear_generator.activate"] = "Power On"

        en_us["death.attack.lcc.gauntlet_punch"] = "%1\$s was obliterated by %2\$s using Punch"
        en_us["death.attack.lcc.gauntlet_punch.item"] = "%1\$s was obliterated by %2\$s using Punch of %3\$s"
        en_us["death.attack.lcc.gauntlet_punch_wall"] = "%1\$s was launched into the wall using Punch"
        en_us["death.attack.lcc.gauntlet_punch_wall.player"] = "%1\$s was launched into the wall by %2\$s using Punch"
        en_us["death.attack.lcc.gauntlet_uppercut"] = "%1\$s was struck by %2\$s using Uppercut"
        en_us["death.attack.lcc.gauntlet_uppercut.item"] = "%1\$s was struck by %2\$s using Uppercut of %3\$s"

        en_us["death.attack.lcc.heated"] = "%1\$s was steamed like a vegetable"
        en_us["death.attack.lcc.heated.player"] = "%1\$s stepped on a generator whilst trying to escape %2\$s"
        en_us["death.attack.lcc.boiled"] = "%1\$s was boiled alive"
        en_us["death.attack.lcc.boiled.player"] = "%1\$s ended up boiled alive whilst trying to escape %2\$s"

        en_us["death.attack.lcc.nuke"] = "%1\$s was nuked"
        en_us["death.attack.lcc.nuke.player"] = "%1\$s was nuked by %2\$s"
        en_us["death.attack.lcc.radiation"] = "%1\$s was exposed to ionizing radiation"
        en_us["death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionizing radiation whilst trying to escape %2\$s"
        lang["en_gb"]!!["death.attack.lcc.radiation"] = "%1\$s was exposed to ionising radiation"
        lang["en_gb"]!!["death.attack.lcc.radiation.player"] = "%1\$s was exposed to ionising radiation whilst trying to escape %2\$s"
        en_us["death.attack.lcc.hazmat_anoxia"] = "%1\$s couldn't get their helmet off"
        en_us["death.attack.lcc.hazmat_anoxia.player"] = "%1\$s couldn't get their helmet off whilst trying to escape %2\$s"
        en_us["death.attack.lcc.salt"] = "%1\$s was lightly salted by %2\$s"
        en_us["death.attack.lcc.salt.item"] = "%1\$s was lightly salted by %2\$s using %3\$s"
        en_us["death.attack.lcc.spikes"] = "%1\$s was skewered"
        en_us["death.attack.lcc.spikes.player"] = "%1\$s was skewered like a kebab whilst trying to escape %2\$s"

        en_us["subtitles.lcc.block.soaking_soul_sand.jump"] = "Soaking Soul Sand squelches"
        en_us["subtitles.lcc.block.bounce_pad.jump"] = "Bounce Pad protracts"
        en_us["subtitles.lcc.block.bounce_pad.set"] = "Bounce Pad set"
        en_us["subtitles.lcc.entity.pocket_zombie_pigman.ambient"] = "Pocket Zombie Pigman grunts angrily"
        en_us["subtitles.lcc.entity.pocket_zombie_pigman.hurt"] = "Pocket Zombie Pigman hurts"
        en_us["subtitles.lcc.entity.pocket_zombie_pigman.death"] = "Pocket Zombie Pigman dies"
        en_us["subtitles.lcc.block.classic_crying_obsidian.set_spawn"] = "Classic Crying Obsidian sets spawn"
        en_us["subtitles.lcc.entity.atomic_bomb.fuse"] = "Atomic Bomb ticks"
        en_us["subtitles.lcc.entity.atomic_bomb.defuse"] = "Atomic Bomb defused"
        en_us["subtitles.lcc.entity.nuclear_explosion.explode"] = "Nuclear explosion"
        en_us["subtitles.lcc.block.alarm.bell"] = "Alarm rings"
        en_us["subtitles.lcc.block.alarm.nuclear_siren"] = "Alarm siren blares"
        en_us["subtitles.lcc.item.detector.click"] = "Radiation Detector clicks"
        en_us["subtitles.lcc.entity.salt.throw"] = "Salt thrown"
        en_us["subtitles.lcc.block.spikes.hurt"] = "Spikes damage"
        en_us["subtitles.lcc.block.improvised_explosive.beep"] = "Improvised Explosive beeps"
        en_us["subtitles.lcc.block.improvised_explosive.constant"] = "Improvised Explosive beeps rapidly"
        en_us["subtitles.lcc.block.improvised_explosive.triggered"] = "Improvised Explosive triggered"
        en_us["subtitles.lcc.block.improvised_explosive.defuse"] = "Improvised Explosive defused"
        en_us["subtitles.lcc.entity.consumer.ambient"] = "Consumer snarls"
        en_us["subtitles.lcc.entity.consumer.hurt"] = "Consumer hurts"
        en_us["subtitles.lcc.entity.consumer.death"] = "Consumer dies"
        en_us["subtitles.lcc.entity.consumer.attack"] = "Consumer chomps"
        en_us["subtitles.lcc.entity.consumer.cqc"] = "Consumer snarls angrily"
        en_us["subtitles.lcc.entity.consumer.tongue.shoot"] = "Consumer extends tongue"
        en_us["subtitles.lcc.entity.consumer.tongue.loop"] = "Consumer licks"
        en_us["subtitles.lcc.entity.consumer.tongue.attach"] = "Consumer tongues something"

        en_us[LCCBlocks.nether_reactor.translationKey.toString() + ".active"] = "Active!"
        en_us[LCCBlocks.nether_reactor.translationKey.toString() + ".incorrect"] = "Not the correct pattern!"
        en_us[LCCBlocks.nether_reactor.translationKey.toString() + ".players"] = "All players need to be close to the reactor."
        en_us[LCCBlocks.nether_reactor.translationKey.toString() + ".y"] = "The reactor must be placed between y=%1\$s and y=%2\$s"

        en_us[LCCBlocks.radar.translationKey.toString() + ".range"] = "Range is %1\$s blocks"

        en_us[LCCBlocks.sapphire_altar.translationKey.toString() + ".malformed"] = "The bomb board has been tampered with."

        en_us["commands.lcc.radiation.add.success.single"] = "Added %s to the radiation sickness of %s"
        en_us["commands.lcc.radiation.add.success.multiple"] = "Added %s to the radiation sickness of %s players"
        en_us["commands.lcc.radiation.set.success.single"] = "Set the radiation sickness of %s to %s"
        en_us["commands.lcc.radiation.set.success.multiple"] = "Set the radiation sickness of %s players to %s"
        en_us["commands.lcc.radiation.query"] = "%s has a radiation sickness value of %s"
        en_us["commands.lcc.radiation.failed.living.single"] = "%s must be a living entity to have radiation sickness"
        en_us["commands.lcc.radiation.failed.living.multiple"] = "No entities given were living and cannot receive radiation sickness"

        en_us["commands.lcc.nuclearwinter.add.success"] = "Added %s to the nuclear winter level of %s"
        en_us["commands.lcc.nuclearwinter.set.success"] = "Set the nuclear winter level of %s to %s"
        en_us["commands.lcc.nuclearwinter.query"] = "%s has a nuclear winter level of %s"
    }

}
