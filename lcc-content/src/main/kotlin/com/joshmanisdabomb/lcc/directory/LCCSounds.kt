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
    val salt_throw by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.salt.throw")) }

    val spikes_hurt by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.spikes.hurt")) }
    val improvised_explosive_triggered by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.improvised_explosive.triggered")) }
    val improvised_explosive_beep by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.improvised_explosive.beep")) }
    val improvised_explosive_constant by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.improvised_explosive.constant")) }
    val improvised_explosive_defuse by entry(::initialiser) { SoundEvent(LCC.id("block.lcc.improvised_explosive.defuse")) }

    val consumer_ambient by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.ambient")) }
    val consumer_cqc by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.cqc")) }
    val consumer_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.hurt")) }
    val consumer_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.death")) }
    val consumer_attack by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.attack")) }
    val consumer_tongue_shoot by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.tongue.shoot")) }
    val consumer_tongue_loop by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.tongue.loop")) }
    val consumer_tongue_attach by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.consumer.tongue.attach")) }

    val woodlouse_ambient by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.woodlouse.ambient")) }
    val woodlouse_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.woodlouse.hurt")) }
    val woodlouse_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.woodlouse.death")) }
    val woodlouse_step by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.woodlouse.step")) }

    val wasp_loop by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.loop")) }
    val wasp_aggressive by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.aggressive")) }
    val wasp_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.hurt")) }
    val wasp_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.death")) }
    val wasp_step by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.step")) }
    val wasp_sting by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.wasp.sting")) }

    val psycho_pig_ambient by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.psycho_pig.ambient")) }
    val psycho_pig_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.psycho_pig.hurt")) }
    val psycho_pig_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.psycho_pig.death")) }
    val psycho_pig_reveal by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.psycho_pig.reveal")) }

    val disciple_ambient by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.ambient")) }
    val disciple_hurt by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.hurt")) }
    val disciple_death by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.death")) }
    val disciple_jump by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.jump")) }
    val disciple_fire by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.fire")) }
    val disciple_dust by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.dust")) }
    val disciple_explosion by entry(::initialiser) { SoundEvent(LCC.id("entity.lcc.disciple.explosion")) }

    val soaking_soul_sand by lazy { BlockSoundGroup(1.0f, 1.0f, soaking_soul_sand_break, soaking_soul_sand_step, soaking_soul_sand_place, soaking_soul_sand_hit, soaking_soul_sand_fall) }

    //TODO classic block sounds for nostalgic blocks
    //TODO sound update, pass for all blocks

    override fun defaultProperties(name: String) = Unit

}