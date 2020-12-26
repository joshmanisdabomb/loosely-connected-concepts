package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvent
import net.minecraft.util.registry.Registry

object LCCSounds : RegistryDirectory<SoundEvent, Unit>() {

    override val _registry by lazy { Registry.SOUND_EVENT }

    val soaking_soul_sand_break by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.break")) }
    val soaking_soul_sand_fall by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.fall")) }
    val soaking_soul_sand_hit by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.hit")) }
    val soaking_soul_sand_place by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.place")) }
    val soaking_soul_sand_step by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.step")) }
    val soaking_soul_sand_jump by create { SoundEvent(LCC.id("block.lcc.soaking_soul_sand.jump")) }

    val bounce_pad_jump by create { SoundEvent(LCC.id("block.lcc.bounce_pad.jump")) }
    val bounce_pad_set by create { SoundEvent(LCC.id("block.lcc.bounce_pad.set")) }

    val pocket_zombie_pigman_ambient by create { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.ambient")) }
    val pocket_zombie_pigman_hurt by create { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.hurt")) }
    val pocket_zombie_pigman_death by create { SoundEvent(LCC.id("entity.lcc.pocket_zombie_pigman.death")) }

    val soaking_soul_sand by lazy { BlockSoundGroup(1.0f, 1.0f, soaking_soul_sand_break, soaking_soul_sand_step, soaking_soul_sand_place, soaking_soul_sand_hit, soaking_soul_sand_fall) }

    //TODO classic block sounds for nostalgic blocks

}