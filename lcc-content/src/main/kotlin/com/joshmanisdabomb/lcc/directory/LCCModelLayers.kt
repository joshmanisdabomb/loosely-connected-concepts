package com.joshmanisdabomb.lcc.directory

import com.google.common.collect.ImmutableMap
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.render.AlarmBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.NuclearFiredGeneratorBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import com.joshmanisdabomb.lcc.entity.model.*
import com.joshmanisdabomb.lcc.entity.render.PocketZombiePigmanEntityRenderer
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.EntityModelLayer
import net.minecraft.client.render.entity.model.VillagerResemblingModel

object LCCModelLayers : BasicDirectory<EntityModelLayer, () -> TexturedModelData>() {

    val pocket_zombie_pigman by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(PocketZombiePigmanEntityRenderer::data)
    val wasp by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(WaspEntityModel.Companion::data)
    val consumer by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(ConsumerEntityModel.Companion::data)
    val consumer_tongue by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(ConsumerTongueEntityModel.Companion::data)
    val disciple by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(DiscipleEntityModel.Companion::data)
    val psycho_pig by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(PsychoPigEntityModel.Companion::data)
    val rotwitch by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(RotwitchEntityModel.Companion::data)
    val fly by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(FlyEntityModel.Companion::data)
    val woodlouse by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(WoodlouseEntityModel.Companion::data)
    val traveller by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties { TexturedModelData.of(VillagerResemblingModel.getModelData(), 64, 64) }
    val traveller_bindle by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(TravellerEntityModel.Companion::data)

    val bounce_pad by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(BouncePadBlockEntityRenderer::data)
    val time_rift by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(TimeRiftBlockEntityRenderer::data)
    val nuclear_generator by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(NuclearFiredGeneratorBlockEntityRenderer::data)
    val alarm by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(AlarmBlockEntityRenderer::data)

    private fun <L : EntityModelLayer> initialiser(input: L, context: DirectoryContext<() -> TexturedModelData>, parameters: Unit) = input

    fun build(builder: ImmutableMap.Builder<EntityModelLayer, TexturedModelData>) {
        entries.forEach { (k, v) -> builder.put(v.entry, v.properties()) }
    }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String): () -> TexturedModelData = error("No default properties available for this directory.")

}