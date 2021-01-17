package com.joshmanisdabomb.lcc.data

import com.joshmanisdabomb.lcc.data.generators.LangData
import com.joshmanisdabomb.lcc.directory.LCCBlocks

object LCCLangData {

    operator fun invoke(lang: Map<String, LangData>) {
        lang["en_us"]!!["itemGroup.lcc.group"] = "Loosely Connected Concepts"
        lang["en_us"]!!["itemGroup.lcc.group.set.amount"] = "%s item"
        lang["en_us"]!!["itemGroup.lcc.group.set.amount.s"] = "s"
        lang["en_us"]!!["itemGroup.lcc.group.set.classic_cloth"] = "Classic Cloths"

        lang["en_us"]!!["effect.lcc.stun"] = "Stun"
        lang["en_us"]!!["effect.lcc.vulnerable"] = "Vulnerable"
        lang["en_us"]!!["effect.lcc.flammable"] = "Flammable"

        lang["en_us"]!!["biome.lcc.wasteland"] = "Wasteland"

        lang["en_us"]!!["container.lcc.spawner_table"] = "Arcane Table"
        lang["en_us"]!!["container.lcc.refiner"] = "Refiner"
        lang["en_us"]!!["container.lcc.composite_processor"] = "Composite Processor"
        lang["en_us"]!!["container.lcc.coal_generator"] = "Furnace Generator"
        lang["en_us"]!!["container.lcc.oil_generator"] = "Combustion Generator"

        lang["en_us"]!!["container.lcc.refining.power"] = "Power: %1\$s"
        lang["en_us"]!!["container.lcc.refining.power.recipe"] = "Power: %1\$s\nConsumed: %2\$s/t"
        lang["en_us"]!!["container.lcc.refining.efficiency"] = "Efficiency: %1\$s%%"
        lang["en_us"]!!["container.lcc.refining.efficiency.recipe"] = "Efficiency: %1\$s%%\nMaximum Efficiency: %2\$s%%"
        lang["en_us"]!!["container.lcc.refining.time"] = "%1\$s/%2\$s s"
        lang["en_us"]!!["container.lcc.refining.recipe.asphalt_mixing"] = "Bituminous Mixing"
        lang["en_us"]!!["container.lcc.refining.recipe.pozzolanic_mixing"] = "Pozzolanic Mixing"
        lang["en_us"]!!["container.lcc.refining.recipe.uranium"] = "Centrifugal Separation"
        lang["en_us"]!!["container.lcc.refining.recipe.treating"] = "Thermal Disinfection"

        lang["en_us"]!!["container.lcc.generator.burn"] = "Remaining Time: %1\$s/%2\$s s"
        lang["en_us"]!!["container.lcc.generator.output"] = "Steam Produced: %1\$s/t\nBase Steam from Fuel: %2\$s/t\nSteam Multiplier from Water: %3\$s%%"

        lang["en_us"]!!["death.attack.lcc.gauntlet_punch"] = "%1\$s was obliterated by %2\$s using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch.item"] = "%1\$s was obliterated by %2\$s using Punch of %3\$s"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch_wall"] = "%1\$s was launched into the wall using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_punch_wall.player"] = "%1\$s was launched into the wall by %2\$s using Punch"
        lang["en_us"]!!["death.attack.lcc.gauntlet_uppercut"] = "%1\$s was struck by %2\$s using Uppercut"
        lang["en_us"]!!["death.attack.lcc.gauntlet_uppercut.item"] = "%1\$s was struck by %2\$s using Uppercut of %3\$s"

        lang["en_us"]!!["death.attack.lcc.heated"] = "%1\$s was steamed like a vegetable"
        lang["en_us"]!!["death.attack.lcc.boiled"] = "%1\$s was boiled alive"

        lang["en_us"]!!["subtitles.lcc.block.soaking_soul_sand.jump"] = "Soaking Soul Sand squelches"
        lang["en_us"]!!["subtitles.lcc.block.bounce_pad.jump"] = "Bounce Pad protracts"
        lang["en_us"]!!["subtitles.lcc.block.bounce_pad.set"] = "Bounce Pad set"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.ambient"] = "Pocket Zombie Pigman grunts angrily"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.hurt"] = "Pocket Zombie Pigman hurts"
        lang["en_us"]!!["subtitles.lcc.entity.pocket_zombie_pigman.death"] = "Pocket Zombie Pigman dies"
        lang["en_us"]!!["subtitles.lcc.block.classic_crying_obsidian.set_spawn"] = "Classic Crying Obsidian sets spawn"

        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".active"] = "Active!"
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".incorrect"] = "Not the correct pattern!"
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".players"] = "All players need to be close to the reactor."
        lang["en_us"]!![LCCBlocks.nether_reactor.translationKey.toString() + ".y"] = "The reactor must be placed between y=4 and y=221"
    }

}
