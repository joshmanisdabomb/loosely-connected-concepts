package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.WaspEntity
import com.joshmanisdabomb.lcc.entity.model.WaspEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.WaspEyesFeatureRenderer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack

class WaspEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<WaspEntity, WaspEntityModel<WaspEntity>>(ctx, WaspEntityModel(ctx.getPart(LCCModelLayers.wasp)), 0.6f) {

    init {
        addFeature(WaspEyesFeatureRenderer(this));
    }

    override fun setupTransforms(entity: WaspEntity, matrices: MatrixStack, animationProgress: Float, bodyYaw: Float, tickDelta: Float) {
        if (entity.hasAngerTime()) {
            matrices.translate(entity.world.random.nextDouble().minus(0.5).div(10), entity.world.random.nextDouble().minus(0.5).div(10), entity.world.random.nextDouble().minus(0.5).div(10))
            entity.bodyYaw += (Math.cos(entity.age.toDouble() * 32.5) * 3.141592653589793 * 0.4000000059604645).toFloat()
        }
        super.setupTransforms(entity, matrices, animationProgress, bodyYaw, tickDelta)
    }

    override fun getLyingAngle(entity: WaspEntity) = 180.0f

    override fun getTexture(entity: WaspEntity) = LCC.id("textures/entity/wasp/wasp.png")

}