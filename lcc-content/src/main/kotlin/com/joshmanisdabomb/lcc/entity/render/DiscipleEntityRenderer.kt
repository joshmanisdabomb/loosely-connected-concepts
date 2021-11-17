package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.entity.model.DiscipleEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.DiscipleBrainFeatureRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer

@Environment(EnvType.CLIENT)
class DiscipleEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<DiscipleEntity, DiscipleEntityModel>(ctx, DiscipleEntityModel(ctx.getPart(LCCModelLayers.disciple)), 0.5f) {

    init {
        addFeature(DiscipleBrainFeatureRenderer(this))
    }

    override fun getTexture(entity: DiscipleEntity) = LCC.id("textures/entity/disciple/disciple.png")

}