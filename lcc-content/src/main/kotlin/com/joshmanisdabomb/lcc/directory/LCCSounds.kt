package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry

object LCCSounds : BasicDirectory<SoundEvent, Unit>(), RegistryDirectory<SoundEvent, Unit, Unit> {

    override val registry by lazy { Registry.SOUND_EVENT }

    override fun regId(name: String) = LCC.id(name)

    val soaking_soul_sand_break by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.break")) }
    val soaking_soul_sand_fall by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.fall")) }
    val soaking_soul_sand_hit by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.hit")) }
    val soaking_soul_sand_place by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.place")) }
    val soaking_soul_sand_step by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.step")) }
    val soaking_soul_sand_jump by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.jump")) }

    val bounce_pad_jump by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.bounce_pad.jump")) }
    val bounce_pad_set by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.bounce_pad.set")) }

    val alarm_bell by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.alarm.bell")) }
    val alarm_nuclear_siren by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.alarm.nuclear_siren")) }

    val pocket_zombie_pigman_ambient by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.ambient")) }
    val pocket_zombie_pigman_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.hurt")) }
    val pocket_zombie_pigman_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.death")) }

    val classic_crying_obsidian_set_spawn by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.classic_crying_obsidian.set_spawn")) }

    val atomic_bomb_fuse by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.atomic_bomb.fuse")) }
    val atomic_bomb_defuse by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.atomic_bomb.defuse")) }
    val nuclear_explosion_explode by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.nuclear_explosion.explode")) }

    val player_hurt_iron by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.player.hurt_iron")) }
    val player_hurt_crystal by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.player.hurt_crystal")) }
    val player_hurt_temporary by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.player.hurt_temporary")) }

    val radiation_detector_click by entry(::initialiser) { SoundEvent(LCC.id("item.lcc.detector.click")) }

    val soaking_soul_sand by lazy { BlockSoundGroup(1.0f, 1.0f, soaking_soul_sand_break, soaking_soul_sand_step, soaking_soul_sand_place, soaking_soul_sand_hit, soaking_soul_sand_fall) }

    //TODO classic block sounds for nostalgic blocks
    //TODO sound update, pass for all blocks

    override fun defaultProperties(name: String) = Unit

}