package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.facade.boat.LCCBoatType
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.entity.vehicle.BoatEntity
import net.minecraft.item.Items

@EnvironmentInterface(itf = ClientSpriteRegistryCallback::class, value = EnvType.CLIENT)
object LCCBoatTypes : BasicDirectory<LCCBoatType, Unit>(), ClientSpriteRegistryCallback {

    val rubber by entry(::initialiser) { LCCBoatType({ LCCItems.rubber_boat }, LCC.id("${name}_boat"), LCC.id("textures/entity/boat/$name.png"), LCCBlocks.rubber_planks, Items.STICK, BoatEntity.Type.OAK) }
    val deadwood by entry(::initialiser) { LCCBoatType({ LCCItems.deadwood_boat }, LCC.id("${name}_boat"), LCC.id("textures/entity/boat/$name.png"), LCCBlocks.deadwood_planks, Items.STICK, BoatEntity.Type.OAK) }
    val ash by entry(::initialiser) { LCCBoatType({ LCCItems.ash_boat }, LCC.id("${name}_boat"), LCC.id("textures/entity/boat/$name.png"), LCCBlocks.ash_planks, Items.STICK, BoatEntity.Type.OAK) }

    fun initialiser(input: LCCBoatType, context: DirectoryContext<Unit>, parameters: Unit) = input.apply { registerEntity() }

    override fun defaultProperties(name: String) = Unit

    @Environment(EnvType.CLIENT)
    override fun registerSprites(atlasTexture: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
        registry.register(rubber.texture)
    }

}