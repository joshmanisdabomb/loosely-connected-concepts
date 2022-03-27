package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.WoodlouseEntity
import com.joshmanisdabomb.lcc.entity.model.WoodlouseEntityModel
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.EntityModel

class WoodlouseEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<WoodlouseEntity, EntityModel<WoodlouseEntity>>(ctx, WoodlouseEntityModel(ctx.getPart(LCCModelLayers.woodlouse)), 0.45f) {

    override fun getTexture(entity: WoodlouseEntity) = LCC.id("textures/entity/woodlouse.png")

    override fun getLyingAngle(entity: WoodlouseEntity) = 180.0f

}