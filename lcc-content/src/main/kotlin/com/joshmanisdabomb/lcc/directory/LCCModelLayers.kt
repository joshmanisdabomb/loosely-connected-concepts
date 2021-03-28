package com.joshmanisdabomb.lcc.directory

import com.google.common.collect.ImmutableMap
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.NuclearFiredGeneratorBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import com.joshmanisdabomb.lcc.entity.render.PocketZombiePigmanEntityRenderer
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.EntityModelLayer

object LCCModelLayers : BasicDirectory<EntityModelLayer, () -> TexturedModelData>() {

    val pocket_zombie_pigman by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(PocketZombiePigmanEntityRenderer::data)

    val bounce_pad by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(BouncePadBlockEntityRenderer::data)
    val time_rift by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(TimeRiftBlockEntityRenderer::data)
    val nuclear_generator by entry(::initialiser) { EntityModelLayer(id, "main") }
        .setProperties(NuclearFiredGeneratorBlockEntityRenderer::data)

    private fun <L : EntityModelLayer> initialiser(input: L, context: DirectoryContext<() -> TexturedModelData>, parameters: Unit) = input

    fun build(builder: ImmutableMap.Builder<EntityModelLayer, TexturedModelData>) {
        entries.forEach { (k, v) -> builder.put(v.entry, v.properties()) }
    }

    override fun id(name: String) = LCC.id(name)

    override fun defaultProperties(name: String): () -> TexturedModelData = error("No default properties available for this directory.")

}