package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.TravellerEntity
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.feature.VillagerHeldItemFeatureRenderer
import net.minecraft.client.render.entity.model.VillagerResemblingModel
import net.minecraft.client.util.math.MatrixStack

class TravellerEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<TravellerEntity, VillagerResemblingModel<TravellerEntity>>(ctx, VillagerResemblingModel<TravellerEntity>(ctx.getPart(LCCModelLayers.traveller)), 0.5f) {

    init {
        addFeature(VillagerHeldItemFeatureRenderer(this, ctx.heldItemRenderer))
    }

    override fun getTexture(entity: TravellerEntity) = LCC.id("textures/entity/traveller.png")

    override fun scale(entity: TravellerEntity, matrixStack: MatrixStack, f: Float) {
        matrixStack.scale(0.9375f, 0.9375f, 0.9375f)
    }

}