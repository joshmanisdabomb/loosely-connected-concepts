package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
import com.joshmanisdabomb.lcc.entity.model.PsychoPigEntityModel
import com.joshmanisdabomb.lcc.entity.render.feature.PsychoPigHeldItemFeatureRenderer
import com.joshmanisdabomb.lcc.extensions.transform
import net.minecraft.client.model.Dilation
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.VertexConsumerProvider
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.render.entity.model.EntityModel
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.client.render.entity.model.PigEntityModel
import net.minecraft.client.util.math.MatrixStack
import net.minecraft.util.Identifier

class PsychoPigEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<PsychoPigEntity, EntityModel<PsychoPigEntity>>(ctx, PsychoPigEntityModel(ctx.getPart(LCCModelLayers.psycho_pig)), 0.7f) {

    val angryModel = this.model
    val pigModel = PigEntityModel<PsychoPigEntity>(ctx.getPart(EntityModelLayers.PIG))

    init {
        addFeature(PsychoPigHeldItemFeatureRenderer(this))
    }

    override fun getTexture(entity: PsychoPigEntity): Identifier {
        if (entity.isAggro) return textureAngry
        return entity.eyeLeft.transform(textureLeft, textureRight)
    }

    override fun render(entity: PsychoPigEntity, yaw: Float, tickDelta: Float, matrices: MatrixStack, vertexConsumers: VertexConsumerProvider, light: Int) {
        this.model = entity.isAggro.transform(angryModel, pigModel)
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light)
    }

    companion object {
        val textureAngry = LCC.id("textures/entity/psycho_pig/angry.png")
        val textureLeft = LCC.id("textures/entity/psycho_pig/left.png")
        val textureRight = LCC.id("textures/entity/psycho_pig/right.png")

        fun data() = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64)
    }

}