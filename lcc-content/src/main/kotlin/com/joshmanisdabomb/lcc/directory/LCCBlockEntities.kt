package com.joshmanisdabomb.lcc.directory

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.BatteryBlock
import com.joshmanisdabomb.lcc.block.FiredGeneratorBlock
import com.joshmanisdabomb.lcc.block.RefiningBlock
import com.joshmanisdabomb.lcc.block.entity.*
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.NuclearFiredGeneratorBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.rendereregistry.v1.BlockEntityRendererRegistry
import net.fabricmc.fabric.api.event.client.ClientSpriteRegistryCallback
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.texture.SpriteAtlasTexture
import net.minecraft.util.registry.Registry

object LCCBlockEntities : AdvancedDirectory<BlockEntityType.Builder<out BlockEntity>, BlockEntityType<out BlockEntity>, Unit, Unit>(), RegistryDirectory<BlockEntityType<*>, Unit, Unit> {

    override val registry = Registry.BLOCK_ENTITY_TYPE

    override fun regId(name: String) = LCC.id(name)

    val bounce_pad by entry(::beInitialiser) { BlockEntityType.Builder.create(::BouncePadBlockEntity, LCCBlocks.bounce_pad) }
    val spawner_table by entry(::beInitialiser) { BlockEntityType.Builder.create(::DungeonTableBlockEntity, LCCBlocks.spawner_table) }
    val time_rift by entry(::beInitialiser) { BlockEntityType.Builder.create(::TimeRiftBlockEntity, LCCBlocks.time_rift) }
    val classic_chest by entry(::beInitialiser) { BlockEntityType.Builder.create(::ClassicChestBlockEntity, LCCBlocks.classic_chest) }
    val nether_reactor by entry(::beInitialiser) { BlockEntityType.Builder.create(::NetherReactorBlockEntity, LCCBlocks.nether_reactor) }
    val classic_crying_obsidian by entry(::beInitialiser) { BlockEntityType.Builder.create(::ClassicCryingObsidianBlockEntity, LCCBlocks.classic_crying_obsidian) }
    val refining by entry(::beInitialiser) { BlockEntityType.Builder.create(::RefiningBlockEntity, *LCCBlocks.all.values.filterIsInstance<RefiningBlock>().toTypedArray()) }
    val generator by entry(::beInitialiser) { BlockEntityType.Builder.create(::FiredGeneratorBlockEntity, *LCCBlocks.all.values.filterIsInstance<FiredGeneratorBlock>().toTypedArray()) }
    val battery by entry(::beInitialiser) { BlockEntityType.Builder.create(::BatteryBlockEntity, *LCCBlocks.all.values.filterIsInstance<BatteryBlock>().toTypedArray()) }
    val atomic_bomb by entry(::beInitialiser) { BlockEntityType.Builder.create(::AtomicBombBlockEntity, LCCBlocks.atomic_bomb) }
    val oxygen_extractor by entry(::beInitialiser) { BlockEntityType.Builder.create(::OxygenExtractorBlockEntity, LCCBlocks.oxygen_extractor) }
    val kiln by entry(::beInitialiser) { BlockEntityType.Builder.create(::KilnBlockEntity, LCCBlocks.kiln) }
    val nuclear_generator by entry(::beInitialiser) { BlockEntityType.Builder.create(::NuclearFiredGeneratorBlockEntity, LCCBlocks.nuclear_generator, LCCBlocks.failing_nuclear_generator) }

    fun <E : BlockEntity> beInitialiser(input: BlockEntityType.Builder<E>, context: DirectoryContext<Unit>, parameters: Unit): BlockEntityType<E> {
        return initialiser(input.build(null), context, parameters)
    }

    override fun defaultProperties(name: String) = Unit
    override fun defaultContext() = Unit

    @Environment(EnvType.CLIENT)
    fun initRenderers() {
        BlockEntityRendererRegistry.INSTANCE.register(bounce_pad, ::BouncePadBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(BouncePadBlockEntityRenderer)
        BlockEntityRendererRegistry.INSTANCE.register(time_rift, ::TimeRiftBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(TimeRiftBlockEntityRenderer)
        BlockEntityRendererRegistry.INSTANCE.register(nuclear_generator, ::NuclearFiredGeneratorBlockEntityRenderer)
        ClientSpriteRegistryCallback.event(SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE).register(NuclearFiredGeneratorBlockEntityRenderer)
    }

}
