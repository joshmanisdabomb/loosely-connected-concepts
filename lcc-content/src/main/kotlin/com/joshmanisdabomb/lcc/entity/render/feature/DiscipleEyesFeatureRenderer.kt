package com.joshmanisdabomb.lcc.entity.render.feature

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.entity.model.DiscipleEntityModel
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.feature.EyesFeatureRenderer
import net.minecraft.client.render.entity.feature.FeatureRendererContext

class DiscipleEyesFeatureRenderer(renderer: FeatureRendererContext<DiscipleEntity, DiscipleEntityModel>) : EyesFeatureRenderer<DiscipleEntity, DiscipleEntityModel>(renderer) {

    override fun getEyesTexture() = eyes

    companion object {
        val eyes = RenderLayer.getEyes(LCC.id("textures/entity/disciple/eyes.png"))
    }

}