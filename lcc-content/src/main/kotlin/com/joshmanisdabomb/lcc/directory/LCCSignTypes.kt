package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.adaptation.sign.LCCSignType
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.api.EnvironmentInterface
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.texture.SpriteAtlasTexture

@EnvironmentInterface(itf = ClientSpriteRegistryCallback::class, value = EnvType.CLIENT)
object LCCSignTypes : BasicDirectory<LCCSignType, Unit>(), ClientSpriteRegistryCallback {

    val rubber by entry(::initialiser) { LCCSignType(LCC.entity("signs/$name")) }

    fun initialiser(input: LCCSignType, context: DirectoryContext<Unit>, parameters: Unit) = input

    override fun defaultProperties(name: String) = Unit

    @Environment(EnvType.CLIENT)
    override fun registerSprites(atlasTexture: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry) {
        registry.register(rubber.texture)
    }

}