package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.BatteryBlock
import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.entity.*
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

    override fun id(path: String) = LCC.id(path)

    val bounce_pad by create { BlockEntityType.Builder.create(::BouncePadBlockEntity, LCCBlocks.bounce_pad).build(null) }
    val spawner_table by create { BlockEntityType.Builder.create(::DungeonTableBlockEntity, LCCBlocks.spawner_table).build(null) }
    val time_rift by create { BlockEntityType.Builder.create(::TimeRiftBlockEntity, LCCBlocks.time_rift).build(null) }
    val classic_chest by create { BlockEntityType.Builder.create(::ClassicChestBlockEntity, LCCBlocks.classic_chest).build(null) }
    val nether_reactor by create { BlockEntityType.Builder.create(::NetherReactorBlockEntity, LCCBlocks.nether_reactor).build(null) }
    val classic_crying_obsidian by create { BlockEntityType.Builder.create(::ClassicCryingObsidianBlockEntity, LCCBlocks.classic_crying_obsidian).build(null) }
    val refining by create { BlockEntityType.Builder.create(::RefiningBlockEntity, *LCCBlocks.all.values.filterIsInstance<RefiningBlock>().toTypedArray()).build(null) }
    val generator by create { BlockEntityType.Builder.create(::FiredGeneratorBlockEntity, *LCCBlocks.all.values.filterIsInstance<FiredGeneratorBlock>().toTypedArray()).build(null) }
    val battery by create { BlockEntityType.Builder.create(::BatteryBlockEntity, *LCCBlocks.all.values.filterIsInstance<BatteryBlock>().toTypedArray()).build(null) }

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(bounce_pad, ::BouncePadBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(BouncePadBlockEntityRenderer)
        BlockEntityRendererRegistry.INSTANCE.register(time_rift, ::TimeRiftBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(TimeRiftBlockEntityRenderer)
    }

}
