package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.util.registry.Registry

object LCCBlockEntities : RegistryDirectory<BlockEntityType<*>, Unit>() {

    override val _registry = Registry.BLOCK_ENTITY_TYPE

    val bounce_pad by create { BlockEntityType.Builder.create(::BouncePadBlockEntity, LCCBlocks.bounce_pad).build(null) }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(bounce_pad, ::BouncePadBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(BouncePadBlockEntityRenderer)
    }

}
