package com.joshmanisdabomb.lcc.directory

import com.google.common.collect.ImmutableMap
import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.block.entity.render.BouncePadBlockEntityRenderer
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.model.EntityModelLayer

object LCCModelLayers : ThingDirectory<EntityModelLayer, () -> TexturedModelData>() {

    val bounce_pad by create(BouncePadBlockEntityRenderer::data) { EntityModelLayer(LCC.id("bounce_pad"), "main") }

    fun build(builder: ImmutableMap.Builder<EntityModelLayer, TexturedModelData>) {
        all.forEach { (k, v) -> builder.put(v, allProperties[k]!!()) }
    }

}