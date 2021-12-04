package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.entity.PocketZombiePigmanEntity
import com.joshmanisdabomb.lcc.entity.PsychoPigEntity
import net.minecraft.client.model.Dilation
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.RenderLayer
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.client.render.entity.model.EntityModelLayers
import net.minecraft.client.render.entity.model.PigEntityModel
import net.minecraft.util.Identifier

class PsychoPigEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<PsychoPigEntity, PigEntityModel<PsychoPigEntity>>(ctx, PigEntityModel(ctx.getPart(EntityModelLayers.PIG)), 0.7f) {

    override fun getTexture(entity: PsychoPigEntity) = Identifier("textures/entity/pig/pig.png")

    companion object {
        fun data() = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64)
    }

}