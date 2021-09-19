package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.ConsumerEntity
import com.joshmanisdabomb.lcc.entity.model.ConsumerEntityModel
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext

class ConsumerEyesFeatureRenderer(renderer: FeatureRendererContext<ConsumerEntity, ConsumerEntityModel<ConsumerEntity>>) : EyesFeatureRenderer<ConsumerEntity, ConsumerEntityModel<ConsumerEntity>>(renderer) {

    override fun getEyesTexture() = eyes

    companion object {
        val eyes = RenderLayer.getEyes(LCC.id("textures/entity/consumer/eyes.png"))
    }

}