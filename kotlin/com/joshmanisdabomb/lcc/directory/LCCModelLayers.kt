package com.joshmanisdabomb.lcc.directory

import com.google.common.collect.ImmutableMap
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import com.joshmanisdabomb.lcc.block.entity.render.TimeRiftBlockEntityRenderer
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.EntityModelLayer

object LCCModelLayers : ThingDirectory<EntityModelLayer, () -> TexturedModelData>() {

    val bounce_pad by create(BouncePadBlockEntityRenderer::data) { EntityModelLayer(LCC.id("bounce_pad"), "main") }
    val time_rift by create(TimeRiftBlockEntityRenderer::data) { EntityModelLayer(LCC.id("time_rift"), "main") }

    fun build(builder: ImmutableMap.Builder<EntityModelLayer, TexturedModelData>) {
        all.forEach { (k, v) -> builder.put(v, allProperties[k]!!()) }
    }

}