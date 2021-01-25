package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.PocketZombiePigmanEntity
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.BipedEntityRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.util.math.Dilation

class PocketZombiePigmanEntityRenderer(ctx: EntityRendererFactory.Context) : BipedEntityRenderer<PocketZombiePigmanEntity, BipedEntityModel<PocketZombiePigmanEntity>>(ctx, BipedEntityModel(ctx.getPart(LCCModelLayers.pocket_zombie_pigman), RenderLayer::getEntityCutoutNoCull), 0.5f) {

    override fun getTexture(entity: PocketZombiePigmanEntity) = LCC.id("textures/entity/pocket_zombie_pigman.png")

    companion object {
        fun data() = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64)
    }

}