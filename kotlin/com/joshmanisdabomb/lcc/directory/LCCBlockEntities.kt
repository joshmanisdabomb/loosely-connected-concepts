package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.block.entity.BouncePadBlockEntity
import com.joshmanisdabomb.lcc.block.entity.ClassicChestBlockEntity
import com.joshmanisdabomb.lcc.block.entity.DungeonTableBlockEntity
import com.joshmanisdabomb.lcc.block.entity.TimeRiftBlockEntity
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
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
    val spawner_table by create { BlockEntityType.Builder.create(::DungeonTableBlockEntity, LCCBlocks.spawner_table).build(null) }
    val time_rift by create { BlockEntityType.Builder.create(::TimeRiftBlockEntity, LCCBlocks.time_rift).build(null) }
    val classic_chest by create { BlockEntityType.Builder.create(::ClassicChestBlockEntity, LCCBlocks.classic_chest).build(null) }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(bounce_pad, ::BouncePadBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(BouncePadBlockEntityRenderer)
        BlockEntityRendererRegistry.INSTANCE.register(time_rift, ::TimeRiftBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(TimeRiftBlockEntityRenderer)
    }

}
