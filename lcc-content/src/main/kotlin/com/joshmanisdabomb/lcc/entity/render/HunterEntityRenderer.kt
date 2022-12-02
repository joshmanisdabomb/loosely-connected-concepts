package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import net.minecraft.client.model.Dilation
import net.minecraft.client.model.TexturedModelData
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.ZombieEntityRenderer
import net.minecraft.client.render.entity.model.BipedEntityModel
import net.minecraft.entity.mob.ZombieEntity

class HunterEntityRenderer(ctx: EntityRendererFactory.Context) : ZombieEntityRenderer(ctx, LCCModelLayers.hunter, LCCModelLayers.hunter_armor_inner, LCCModelLayers.hunter_armor_outer) {

    override fun getTexture(entity: ZombieEntity) = Companion.texture

    companion object {
        val texture = LCC.id("textures/entity/hunter.png")

        fun data() = TexturedModelData.of(BipedEntityModel.getModelData(Dilation.NONE, 0.0f), 64, 64)
    }

}