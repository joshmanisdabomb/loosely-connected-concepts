package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.WaspEntity
import com.joshmanisdabomb.lcc.entity.model.WaspEntityModel
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer

class WaspEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<WaspEntity, WaspEntityModel<WaspEntity>>(ctx, WaspEntityModel(ctx.getPart(LCCModelLayers.wasp)), 0.6f) {

    override fun getTexture(entity: WaspEntity) = LCC.id("textures/entity/wasp/wasp.png")

}