package com.joshmanisdabomb.lcc.data.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.LCCData
import com.joshmanisdabomb.lcc.data.json.sound.SoundProperties
import com.joshmanisdabomb.lcc.directory.BasicDirectory
import com.joshmanisdabomb.lcc.directory.LCCSounds

object LCCSoundData: BasicDirectory<SoundProperties, Unit>() {

    val soaking_soul_sand_break by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.break", Array(4) {
        SoundProperties.SoundEntry("${LCC.modid}:dig/soaking_soul_sand${it+1}", volume = 0.8f)
    }, "subtitles.block.generic.break") }
    val soaking_soul_sand_fall by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.fall", Array(5) {
        SoundProperties.SoundEntry("${LCC.modid}:step/soaking_soul_sand${it+1}")
    }, null) }
    val soaking_soul_sand_hit by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.hit", Array(5) {
        SoundProperties.SoundEntry("${LCC.modid}:step/soaking_soul_sand${it+1}")
    }, "subtitles.block.generic.hit") }
    val soaking_soul_sand_place by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.place", Array(4) {
        SoundProperties.SoundEntry("${LCC.modid}:dig/soaking_soul_sand${it+1}", volume = 0.8f)
    }, "subtitles.block.generic.place") }
    val soaking_soul_sand_step by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.step", Array(5) {
        SoundProperties.SoundEntry("${LCC.modid}:step/soaking_soul_sand${it+1}")
    }, "subtitles.block.generic.footsteps") }
    val soaking_soul_sand_jump by entry(::initialiser) { SoundProperties("block", LCC.modid, "soaking_soul_sand.jump", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:block/soaking_soul_sand/jump")
    }) }

    val bounce_pad_jump by entry(::initialiser) { SoundProperties("block", LCC.modid, "bounce_pad.jump", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:block/bounce_pad/jump")
    }) }
    val bounce_pad_set by entry(::initialiser) { SoundProperties("block", LCC.modid, "bounce_pad.set", Array(1) {
        SoundProperties.SoundEntry("minecraft:random/click")
    }) }

    val pocket_zombie_pigman_ambient by entry(::initialiser) { SoundProperties("entity", LCC.modid, "pocket_zombie_pigman.ambient", Array(4) {
        SoundProperties.SoundEntry("minecraft:mob/zombified_piglin/zpigangry${it+1}")
    }) }
    val pocket_zombie_pigman_hurt by entry(::initialiser) { SoundProperties("entity", LCC.modid, "pocket_zombie_pigman.hurt", Array(2) {
        SoundProperties.SoundEntry("minecraft:mob/zombified_piglin/zpighurt${it+1}")
    }) }
    val pocket_zombie_pigman_death by entry(::initialiser) { SoundProperties("entity", LCC.modid, "pocket_zombie_pigman.death", Array(1) {
        SoundProperties.SoundEntry("minecraft:mob/zombified_piglin/zpigdeath")
    }) }

    val classic_crying_obsidian_set_spawn by entry(::initialiser) { SoundProperties("block", LCC.modid, "classic_crying_obsidian.set_spawn", Array(3) {
        SoundProperties.SoundEntry("minecraft:block/respawn_anchor/set_spawn${it+1}") //TODO custom
    }) }

    val atomic_bomb_fuse by entry(::initialiser) { SoundProperties("entity", LCC.modid, "atomic_bomb.fuse", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/atomicbomb/fuse")
    }) }
    val atomic_bomb_defuse by entry(::initialiser) { SoundProperties("entity", LCC.modid, "atomic_bomb.defuse", Array(5) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/atomicbomb/cut${it+1}")
    }) }
    val nuclear_explosion_explode by entry(::initialiser) { SoundProperties("entity", LCC.modid, "nuclear_explosion.explode", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/nuclearexplosion/explode", stream = true)
    }) }

    val player_hurt_temporary by entry(::initialiser) { SoundProperties("entity", LCC.modid, "player.hurt_temporary", Array(3) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/player/temporary${it+1}")
    }, "subtitles.entity.player.hurt") }
    val player_hurt_iron by entry(::initialiser) { SoundProperties("entity", LCC.modid, "player.hurt_iron", Array(3) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/player/iron${it+1}")
    }, "subtitles.entity.player.hurt") }
    val player_hurt_crystal by entry(::initialiser) { SoundProperties("entity", LCC.modid, "player.hurt_crystal", Array(3) {
        SoundProperties.SoundEntry("${LCC.modid}:entity/player/crystal${it+1}")
    }, "subtitles.entity.player.hurt") }

    val alarm_bell by entry(::initialiser) { SoundProperties("block", LCC.modid, "alarm.bell", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:block/alarm/bell", stream = true)
    }) }
    val alarm_nuclear_siren by entry(::initialiser) { SoundProperties("block", LCC.modid, "alarm.nuclear_siren", Array(1) {
        SoundProperties.SoundEntry("${LCC.modid}:block/alarm/nuclear_siren", stream = true)
    }) }

    val radiation_detector_click by entry(::initialiser) { SoundProperties("item", LCC.modid, "detector.click", Array(7) {
        SoundProperties.SoundEntry("${LCC.modid}:item/detector/click${it+1}")
    }) }

    fun initialiser(input: SoundProperties, context: DirectoryContext<Unit>, parameters: Unit) = input.also { LCCData.sounds.sounds.add(it) }

    override fun afterInitAll(initialised: List<DirectoryEntry<out SoundProperties, out SoundProperties>>, filter: (context: DirectoryContext<Unit>) -> Boolean) {
        val a = LCCSoundData.all.keys.minus(LCCSounds.all.keys)
        if (a.isNotEmpty()) println("LCCSoundData contains information for sound definitions not present in LCCSounds: $a")
        val b = LCCSounds.all.keys.minus(LCCSoundData.all.keys)
        if (b.isNotEmpty()) println("LCCSounds contains sound definitions with no information in LCCSoundData. $b")
    }

    override fun defaultProperties(name: String) = Unit

}
