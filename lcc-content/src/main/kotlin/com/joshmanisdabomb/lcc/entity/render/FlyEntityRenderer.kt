package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.FlyEntity
import com.joshmanisdabomb.lcc.entity.model.FlyEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.FlyHeadFeatureRenderer
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer

@Environment(EnvType.CLIENT)
class FlyEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<FlyEntity, FlyEntityModel>(ctx, FlyEntityModel(ctx.getPart(LCCModelLayers.fly)), 0.0f) {

    init {
        addFeature(FlyHeadFeatureRenderer(this))
    }

    override fun getTexture(entity: FlyEntity) = LCC.id("textures/entity/fly.png")

}