package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.DiscipleEntity
import com.joshmanisdabomb.lcc.entity.model.DiscipleEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.DiscipleBrainFeatureRenderer
import com.joshmanisdabomb.lcc.entity.render.feature.DiscipleEyesFeatureRenderer
import com.joshmanisdabomb.lcc.extensions.exp
import com.joshmanisdabomb.lcc.extensions.sqrt
import com.joshmanisdabomb.lcc.extensions.square
import net.minecraft.client.MinecraftClient
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.entity.LivingEntity
import net.minecraft.util.math.MathHelper
import net.minecraft.util.math.Vec3f

class DiscipleEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<DiscipleEntity, DiscipleEntityModel>(ctx, DiscipleEntityModel(ctx.getPart(LCCModelLayers.disciple)), 0.5f) {

    init {
        addFeature(DiscipleEyesFeatureRenderer(this))
        addFeature(DiscipleBrainFeatureRenderer(this))
    }

    override fun getTexture(entity: DiscipleEntity) = LCC.id("textures/entity/disciple/disciple.png")

}