package com.joshmanisdabomb.lcc.entity.render

import com.joshmanisdabomb.lcc.LCC
import com.joshmanisdabomb.lcc.directory.LCCModelLayers
import com.joshmanisdabomb.lcc.entity.RotwitchEntity
import com.joshmanisdabomb.lcc.entity.model.RotwitchEntityModel
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.minecraft.client.render.entity.EntityRendererFactory
import net.minecraft.client.render.entity.MobEntityRenderer

@Environment(EnvType.CLIENT)
class RotwitchEntityRenderer(ctx: EntityRendererFactory.Context) : MobEntityRenderer<RotwitchEntity, RotwitchEntityModel>(ctx, RotwitchEntityModel(ctx.getPart(LCCModelLayers.rotwitch)), 0.8f) {

    override fun getTexture(entity: RotwitchEntity) = LCC.id("textures/entity/rotwitch.png")

}