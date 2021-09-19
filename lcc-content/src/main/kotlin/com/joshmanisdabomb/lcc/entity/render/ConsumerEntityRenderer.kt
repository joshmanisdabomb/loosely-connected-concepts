package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.ConsumerEntity
import com.joshmanisdabomb.lcc.entity.model.ConsumerEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.ConsumerEyesFeatureRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer

class ConsumerEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<ConsumerEntity, ConsumerEntityModel<ConsumerEntity>>(ctx, ConsumerEntityModel(ctx.getPart(LCCModelLayers.consumer)), 0.5f) {

    init {
        addFeature(ConsumerEyesFeatureRenderer(this))
    }

    override fun getTexture(entity: ConsumerEntity) = LCC.id("textures/entity/consumer/consumer.png")

}