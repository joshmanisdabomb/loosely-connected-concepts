package com.joshmanisdabomb.lcc.particle

import com.joshmanisdabomb.lcc.LCC
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.client.texture.SpriteAtlasTexture

interface LCCParticle {

    fun registerSprites(atlas: SpriteAtlasTexture, registry: ClientSpriteRegistryCallback.Registry, key: String) {
        registry.register(LCC.id(key))
    }

}
